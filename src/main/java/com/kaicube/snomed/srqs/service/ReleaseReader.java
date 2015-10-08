package com.kaicube.snomed.srqs.service;

import com.google.common.collect.Lists;
import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.ConceptConstants;
import com.kaicube.snomed.srqs.exceptions.ConceptNotFoundException;
import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
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
	public static final Pattern ANCESTOR_OF_PATTERN = Pattern.compile(".*(" + ExpressionConstraintToLuceneConverter.InternalFunction.ANCESTOR_OF + "\\(([^\\)]+)\\)).*");
	public static final Pattern ANCESTOR_OR_SELF_OF_PATTERN = Pattern.compile(".*(" + ExpressionConstraintToLuceneConverter.InternalFunction.ANCESTOR_OR_SELF_OF + "\\(([^\\)]+)\\)).*");
	public static final Pattern ATTRIBUTE_DESCENDANT_OF_PATTERN = Pattern.compile(".*(" + ExpressionConstraintToLuceneConverter.InternalFunction.ATTRIBUTE_DESCENDANT_OF + "\\(([^\\)]+)\\)).*");
	public static final Pattern ATTRIBUTE_DESCENDANT_OR_SELF_OF_PATTERN = Pattern.compile(".*(" + ExpressionConstraintToLuceneConverter.InternalFunction.ATTRIBUTE_DESCENDANT_OR_SELF_OF + "\\(([^\\)]+)\\)).*");

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

	public ConceptResult retrieveConcept(String conceptId) throws IOException, ParseException, NotFoundException {
		final Set<ConceptResult> results = expressionConstraintQuery(conceptId);
		if (!results.isEmpty()) {
			return results.iterator().next();
		} else {
			throw new ConceptNotFoundException(conceptId);
		}
	}

	public Set<ConceptResult> retrieveConceptAncestors(String conceptId) throws ParseException, IOException, NotFoundException {
		return expressionConstraintQuery(">" + conceptId);
	}

	public Set<ConceptResult> retrieveConceptDescendants(String conceptId) throws ParseException, IOException, NotFoundException {
		return expressionConstraintQuery("<" + conceptId);
	}

	public Set<ConceptResult> retrieveReferenceSets() throws ParseException, NotFoundException, IOException {
		// TODO: harden this
		return expressionConstraintQuery("<" + ConceptConstants.REFSET_CONCEPT);
	}

	public Set<ConceptResult> expressionConstraintQuery(String ecQuery) throws ParseException, IOException, NotFoundException {
		Set<ConceptResult> concepts = new HashSet<>();

		if (ecQuery != null && !ecQuery.isEmpty()) {
			String luceneQuery = elToLucene.parse(ecQuery);
			while (luceneQuery.contains(ExpressionConstraintToLuceneConverter.InternalFunction.ANCESTOR_OF)) {
				luceneQuery = processAncestorOf(luceneQuery);
			}
			while (luceneQuery.contains(ExpressionConstraintToLuceneConverter.InternalFunction.ANCESTOR_OR_SELF_OF)) {
				luceneQuery = processAncestorOrSelfOf(luceneQuery);
			}
			while (luceneQuery.contains(ExpressionConstraintToLuceneConverter.InternalFunction.ATTRIBUTE_DESCENDANT_OF)) {
				luceneQuery = processAttributeDescendantOf(luceneQuery);
			}
			while (luceneQuery.contains(ExpressionConstraintToLuceneConverter.InternalFunction.ATTRIBUTE_DESCENDANT_OR_SELF_OF)) {
				luceneQuery = processAttributeDescendantOrSelfOf(luceneQuery);
			}
			final TopDocs topDocs = indexSearcher.search(luceneQueryParser.parse(luceneQuery), Integer.MAX_VALUE);
			logger.info("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, luceneQuery, topDocs.totalHits);
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				concepts.add(getConceptResult(getDocument(scoreDoc)));
			}
		}
		return concepts;
	}

	private String processAncestorOf(String luceneQuery) throws IOException, NotFoundException {
		return processStatement(luceneQuery, ANCESTOR_OF_PATTERN, true, false);
	}

	private String processAncestorOrSelfOf(String luceneQuery) throws IOException, NotFoundException {
		return processStatement(luceneQuery, ANCESTOR_OR_SELF_OF_PATTERN, true, true);
	}

	private String processAttributeDescendantOf(String luceneQuery) throws IOException, NotFoundException {
		return processStatement(luceneQuery, ATTRIBUTE_DESCENDANT_OF_PATTERN, false, false);
	}

	private String processAttributeDescendantOrSelfOf(String luceneQuery) throws IOException, NotFoundException {
		return processStatement(luceneQuery, ATTRIBUTE_DESCENDANT_OR_SELF_OF_PATTERN, false, true);
	}

	private String processStatement(String luceneQuery, Pattern pattern, boolean ancestorNotAttribute, boolean includeSelf) throws IOException, NotFoundException {
		final Matcher matcher = pattern.matcher(luceneQuery);
		if (!matcher.matches() || matcher.groupCount() != 2) {
			final String message = "Failed to extract the " + (ancestorNotAttribute ? "ancestor" : "descendant" ) + " id from the internal query syntax '" + luceneQuery + "'";
			logger.error(message);
			throw new IllegalStateException(message);
		}
		final String conceptId = matcher.group(2);
		List<String> conceptRelatives = null;
		if (ancestorNotAttribute) {
			conceptRelatives = Lists.newArrayList(getConceptDocument(conceptId).getValues(Concept.ANCESTOR));
		} else {
			final TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(Concept.ANCESTOR, conceptId)), Integer.MAX_VALUE);
			conceptRelatives = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				conceptRelatives.add(getDocument(scoreDoc).get(Concept.ID));
			}
		}
		if (includeSelf) {
			conceptRelatives.add(conceptId);
		}

		String newLuceneQuery = luceneQuery.replace(matcher.group(1), buildOptionsList(conceptRelatives, ancestorNotAttribute));
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
		return new ConceptResult(document.get(Concept.ID), document.get(Concept.FSN));
	}

}
