package com.kaicube.snomed.srqs.service;

import com.google.common.collect.Lists;
import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.ConceptConstants;
import com.kaicube.snomed.srqs.domain.Relationship;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import com.kaicube.snomed.srqs.service.dto.RelationshipResult;
import com.kaicube.snomed.srqs.service.exception.*;
import com.kaicube.snomed.srqs.service.exception.InternalError;
import org.antlr.v4.runtime.RecognitionException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.join.ToChildBlockJoinQuery;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReleaseReader {

	private ExpressionConstraintToLuceneConverter elToLucene;
	private final IndexSearcher indexSearcher;
	private final QueryParser luceneQueryParser;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final Map<ExpressionConstraintToLuceneConverter.InternalFunction, Pattern> internalFunctionPatternMap = new TreeMap<>();
	static {
		for (ExpressionConstraintToLuceneConverter.InternalFunction internalFunction : ExpressionConstraintToLuceneConverter.InternalFunction.values()) {
			internalFunctionPatternMap.put(internalFunction, Pattern.compile(".*(" + internalFunction + "\\(([^\\)]+)\\)).*"));
		}
	}

	public ReleaseReader(ReleaseStore releaseStore) throws IOException {
		elToLucene = new ExpressionConstraintToLuceneConverter();
		indexSearcher = new IndexSearcher(DirectoryReader.open(releaseStore.getDirectory()));
		final Analyzer analyzer = releaseStore.createAnalyzer();
		luceneQueryParser = new QueryParser(Version.LUCENE_40, Concept.ID, analyzer);
		luceneQueryParser.setAllowLeadingWildcard(true);
	}

	public long getConceptCount() throws IOException {
		return indexSearcher.collectionStatistics(Concept.ID).docCount();
	}

	public ConceptResult retrieveConcept(String conceptId) throws ServiceException {
		final Set<ConceptResult> results = expressionConstraintQuery(conceptId);
		if (!results.isEmpty()) {
			return results.iterator().next();
		} else {
			throw new ConceptNotFoundException(conceptId);
		}
	}

	public Set<ConceptResult> retrieveConceptAncestors(String conceptId) throws ServiceException {
		return expressionConstraintQuery(">" + conceptId);
	}

	public Set<ConceptResult> retrieveConceptDescendants(String conceptId) throws ServiceException {
		return expressionConstraintQuery("<" + conceptId);
	}

	public Set<ConceptResult> retrieveReferenceSets() throws ServiceException {
		// TODO: harden this
		return expressionConstraintQuery("<" + ConceptConstants.REFSET_CONCEPT);
	}

	public Set<ConceptResult> expressionConstraintQuery(String ecQuery) throws ServiceException {
		Map<String, ConceptResult> concepts = new HashMap<>();

		if (ecQuery != null && !ecQuery.isEmpty()) {
			String luceneQuery;
			try {
				luceneQuery = elToLucene.parse(ecQuery);
			} catch (RecognitionException e) {
				throw new InvalidECLSyntaxException(ecQuery, e);
			}
			try {
				for (ExpressionConstraintToLuceneConverter.InternalFunction internalFunction : internalFunctionPatternMap.keySet()) {
					while (luceneQuery.contains(internalFunction.name())) {
						luceneQuery = processInternalFunction(luceneQuery, internalFunction);
					}
				}
			} catch (IOException e) {
				throw new InternalError("Error preparing internal search query.", e);
			}
			try {
				final Query query = luceneQueryParser.parse(luceneQuery);

				// Fetch Concepts
				final TopDocs topDocs = indexSearcher.search(query, Integer.MAX_VALUE);
				for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
					final ConceptResult conceptResult = getConceptResult(getDocument(scoreDoc));
					concepts.put(conceptResult.getId(), conceptResult);
				}
				logger.info("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, luceneQuery, topDocs.totalHits);

				// Fetch Join Relationships
				final ToChildBlockJoinQuery joinQuery = new ToChildBlockJoinQuery(query, new CachingWrapperFilter(new QueryWrapperFilter(new TermQuery(new Term("type", "concept")))), false);
				final TopDocs relTopDocs = indexSearcher.search(joinQuery, Integer.MAX_VALUE);
				for (ScoreDoc scoreDoc : relTopDocs.scoreDocs) {
					final RelationshipResult relationshipResult = getRelationshipResult(getDocument(scoreDoc));
					concepts.get(relationshipResult.getSourceId()).addRelationship(relationshipResult);
				}
			} catch (ParseException e) {
				throw new InternalError("Error parsing internal search query.", e);
			} catch (IOException e) {
				throw new InternalError("Error performing search.", e);
			}
		}
		return new HashSet<>(concepts.values());
	}

	private String processInternalFunction(String luceneQuery, ExpressionConstraintToLuceneConverter.InternalFunction internalFunction) throws IOException, NotFoundException {
		final Matcher matcher = internalFunctionPatternMap.get(internalFunction).matcher(luceneQuery);
		if (!matcher.matches() || matcher.groupCount() != 2) {
			final String message = "Failed to extract the id from the function " + internalFunction + " in internal query '" + luceneQuery + "'";
			logger.error(message);
			throw new IllegalStateException(message);
		}
		final String conceptId = matcher.group(2);
		List<String> conceptRelatives;
		if (internalFunction.isAncestorType()) {
			conceptRelatives = Lists.newArrayList(getConceptDocument(conceptId).getValues(Concept.ANCESTOR));
		} else {
			final TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(Concept.ANCESTOR, conceptId)), Integer.MAX_VALUE);
			conceptRelatives = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				conceptRelatives.add(getDocument(scoreDoc).get(Concept.ID));
			}
		}
		if (internalFunction.isIncludeSelf()) {
			conceptRelatives.add(conceptId);
		}

		String newLuceneQuery = luceneQuery.replace(matcher.group(1), buildOptionsList(conceptRelatives, !internalFunction.isAttributeType()));
		logger.info("Processed statement of internal query. Before:'{}', After:'{}'", luceneQuery, newLuceneQuery);
		return newLuceneQuery;
	}

	private String buildOptionsList(List<String> conceptRelatives, boolean includeIdFieldName) {
		StringBuilder relativesIdBuilder = new StringBuilder();
		if (!conceptRelatives.isEmpty()) {
			relativesIdBuilder.append("(");
			boolean first = true;
			for (String conceptRelative : conceptRelatives) {
				if (first) {
					first = false;
				} else {
					relativesIdBuilder.append(" OR ");
				}
				if (includeIdFieldName) {
					relativesIdBuilder.append(Concept.ID).append(":");
				}
				relativesIdBuilder.append(conceptRelative);
			}
			relativesIdBuilder.append(")");
		}
		return relativesIdBuilder.toString();
	}

	private Document getConceptDocument(String conceptId) throws IOException, NotFoundException {
		final TopDocs docs = indexSearcher.search(new TermQuery(new Term(Concept.ID, conceptId)), Integer.MAX_VALUE);
		if (docs.totalHits < 1) {
			throw new ConceptNotFoundException(conceptId);
		}
		return indexSearcher.doc(docs.scoreDocs[0].doc);
	}

	private Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}

	private ConceptResult getConceptResult(Document document) {
		return new ConceptResult(
				document.get(Concept.ID),
				document.get(Concept.EFFECTIVE_TIME),
				document.get(Concept.ACTIVE),
				document.get(Concept.MODULE_ID),
				document.get(Concept.DEFINITION_STATUS_ID),
				document.get(Concept.FSN));
	}

	private RelationshipResult getRelationshipResult(Document document) {
		return new RelationshipResult(
				document.get(Relationship.ID),
				document.get(Relationship.EFFECTIVE_TIME),
				document.get(Relationship.ACTIVE),
				document.get(Relationship.MODULE_ID),
				document.get(Relationship.SOURCE_ID),
				document.get(Relationship.DESTINATION_ID),
				document.get(Relationship.RELATIONSHIP_GROUP),
				document.get(Relationship.TYPE_ID),
				document.get(Relationship.CHARACTERISTIC_TYPE_ID),
				document.get(Relationship.MODIFIER_ID));
	}

}
