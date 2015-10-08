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
	public static final Pattern ANCESTOR_OF_PATTERN = Pattern.compile(".*(ancestorOf\\(([^\\)]+)\\)).*");
	public static final Pattern ANCESTOR_OR_SELF_OF_PATTERN = Pattern.compile(".*(ancestorOrSelfOf\\(([^\\)]+)\\)).*");

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

	public Set<ConceptResult> expressionConstraintQuery(String ecQuery) throws ParseException, IOException, NotFoundException {
		Set<ConceptResult> concepts = new HashSet<>();

		if (ecQuery != null && !ecQuery.isEmpty()) {
			String luceneQuery = elToLucene.parse(ecQuery);
			while (luceneQuery.contains("ancestorOf")) {
				luceneQuery = processAncestorOf(luceneQuery);
			}
			while (luceneQuery.contains("ancestorOrSelfOf")) {
				luceneQuery = processAncestorOrSelfOf(luceneQuery);
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
		return processAncestorStatement(luceneQuery, ANCESTOR_OF_PATTERN, false);
	}

	private String processAncestorOrSelfOf(String luceneQuery) throws IOException, NotFoundException {
		return processAncestorStatement(luceneQuery, ANCESTOR_OR_SELF_OF_PATTERN, true);
	}

	private String processAncestorStatement(String luceneQuery, Pattern pattern, boolean includeSelf) throws IOException, NotFoundException {
		logger.info("Processing internal query '{}'" + luceneQuery);
		final Matcher matcher = pattern.matcher(luceneQuery);
		if (!matcher.matches()) {
			final String message = "Failed to extract the ancestor id from the internal query syntax '" + luceneQuery + "'";
			logger.error(message);
			throw new IllegalStateException(message);
		}
		final String conceptId = matcher.group(2);
		final Document conceptDocument = getConceptDocument(conceptId);
		final List<String> ancestors = Lists.newArrayList(conceptDocument.getValues(Concept.ANCESTOR));
		if (includeSelf) {
			ancestors.add(conceptId);
		}

		StringBuilder ancestorIdBuilder = new StringBuilder();
		if (!ancestors.isEmpty()) {
			ancestorIdBuilder.append("(");
			boolean first = true;
			for (String ancestor : ancestors) {
				if (first) {
					first = false;
				} else {
					ancestorIdBuilder.append(" OR ");
				}
				ancestorIdBuilder.append(Concept.ID).append(":").append(ancestor);
			}
			ancestorIdBuilder.append(")");
		}
		String newLuceneQuery = luceneQuery.replace(matcher.group(1), ancestorIdBuilder.toString());
		logger.info("Processed statement of internal query. Before:'{}', After:'{}'", luceneQuery, newLuceneQuery);
		return newLuceneQuery;
	}

	private Document getConceptDocument(String conceptId) throws IOException, NotFoundException {
		final TopDocs docs = indexSearcher.search(new TermQuery(new Term(Concept.ID, conceptId)), Integer.MAX_VALUE);
		if (docs.totalHits < 1) {
			throw new ConceptNotFoundException(conceptId);
		}
		return indexSearcher.doc(docs.scoreDocs[0].doc);
	}

	public Set<ConceptResult> retrieveConceptAncestors(String conceptId) throws ParseException, IOException, NotFoundException {
		return expressionConstraintQuery(">" + conceptId);
	}

	public Set<ConceptResult> retrieveConceptDescendants(String conceptId) throws ParseException, IOException, NotFoundException {
		return expressionConstraintQuery("<" + conceptId);
	}

	private Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}

	private ConceptResult getConceptResult(Document document) {
		return new ConceptResult(document.get(Concept.ID), document.get(Concept.FSN));
	}

	public Set<ConceptResult> retrieveReferenceSets() throws ParseException, NotFoundException, IOException {
		// TODO: harden this
		return expressionConstraintQuery("<" + ConceptConstants.REFSET_CONCEPT);
	}
}
