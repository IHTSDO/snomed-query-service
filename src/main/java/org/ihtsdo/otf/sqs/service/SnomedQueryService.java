package org.ihtsdo.otf.sqs.service;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.antlr.v4.runtime.RecognitionException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.ihtsdo.otf.sqs.domain.ConceptConstants;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.dto.ConceptIdResults;
import org.ihtsdo.otf.sqs.service.dto.ConceptResult;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.dto.RefsetMembershipResult;
import org.ihtsdo.otf.sqs.service.exception.InternalError;
import org.ihtsdo.otf.sqs.service.exception.*;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SnomedQueryService {

	public static final int DEFAULT_LIMIT = 1000;
	private final ReleaseStore releaseStore;
	private ExpressionConstraintToLuceneConverter elToLucene;
	private final IndexSearcher indexSearcher;
	private final Analyzer analyzer;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, RefsetMembershipResult> refsetResultMap = new ConcurrentHashMap<>();

	private static final Set<String> CONCEPT_FIELD_SET = Collections.singleton(ConceptFieldNames.ID);
	public static final Map<ExpressionConstraintToLuceneConverter.InternalFunction, Pattern> internalFunctionPatternMap = new TreeMap<>();
	static {
		for (ExpressionConstraintToLuceneConverter.InternalFunction internalFunction : ExpressionConstraintToLuceneConverter.InternalFunction.values()) {
			internalFunctionPatternMap.put(internalFunction, Pattern.compile(".*(" + internalFunction + "\\(([^\\)]+)\\)).*"));
		}
	}

	public SnomedQueryService(ReleaseStore releaseStore) throws IOException {
		elToLucene = new ExpressionConstraintToLuceneConverter();
		this.releaseStore = releaseStore;
		indexSearcher = new IndexSearcher(DirectoryReader.open(this.releaseStore.getDirectory()));
		analyzer = releaseStore.createAnalyzer();
		BooleanQuery.setMaxClauseCount(4 * 100 * 1000);
	}

	public long getConceptCount() throws IOException {
		return indexSearcher.collectionStatistics(ConceptFieldNames.ID).docCount();
	}

	public ConceptResult retrieveConcept(String conceptId) throws ServiceException {
		final List<ConceptResult> results = search(conceptId).getItems();
		if (!results.isEmpty()) {
			return results.get(0);
		} else {
			throw new ConceptNotFoundException(conceptId);
		}
	}

	public ConceptResults listAll(int offset, int limit) throws ServiceException {
		return getConceptResults(new TermQuery(new Term("type", "concept")), offset, limit);
	}

	public ConceptResults search(String ecQuery, String term, int offset, int limit) throws ServiceException {
		BooleanQuery termLuceneQuery = getTermQuery(term);
		Query eclLuceneQuery = getECLQuery(ecQuery);

		if (termLuceneQuery == null && eclLuceneQuery == null) {
			return listAll(offset, limit);
		}

		BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();

		if (termLuceneQuery != null) {
			queryBuilder.add(termLuceneQuery, BooleanClause.Occur.MUST);
		}
		if (eclLuceneQuery != null) {
			queryBuilder.add(eclLuceneQuery, BooleanClause.Occur.MUST);
		}

		BooleanQuery query = queryBuilder.build();
		final ConceptResults conceptResults = getConceptResults(query, offset, limit);
		logger.info("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, limitStringLength(query.toString(), 100), conceptResults.getTotal());
		return conceptResults;
	}

	private BooleanQuery getTermQuery(String term) {
		if (term == null || term.trim().isEmpty()) {
			return null;
		}

		BooleanQuery.Builder queryBuilder = new BooleanQuery.Builder();
		for (String prefix : term.toLowerCase().trim().split(" ")) {
			prefix = prefix.trim();
			if (!prefix.isEmpty()) {
				queryBuilder.add(new WildcardQuery(new Term(ConceptFieldNames.FSN, prefix + "*")), BooleanClause.Occur.SHOULD);
			}
		}
		return queryBuilder.build();
	}

	private Query getECLQuery(String ecQuery) throws InvalidECLSyntaxException, NotFoundException, InternalError {
		if (ecQuery == null || ecQuery.isEmpty()) {
			return null;
		}
		String luceneQueryString = preprocessECLQuery(ecQuery);
		try {
			return getQueryParser().parse(luceneQueryString);
		} catch (ParseException e) {
			throw new InternalError("Error parsing internal search query.", e);
		}
	}

	public ConceptIdResults eclQueryReturnConceptIdentifiers(String ecQuery, int offset, int limit) throws ServiceException {
		if (ecQuery != null && !ecQuery.isEmpty()) {
			String luceneQuery = preprocessECLQuery(ecQuery);
			try {
				final Query query = getQueryParser().parse(luceneQuery);
				final ConceptIdResults conceptIdResults = getConceptIdResults(query, offset, limit);

				logger.info("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, limitStringLength(luceneQuery, 100), conceptIdResults.getTotal());
				return conceptIdResults;
			} catch (ParseException e) {
				throw new InternalError("Error parsing internal search query.", e);
			}
		}
		return new ConceptIdResults(new ArrayList<Long>(), offset, 0, limit);
	}

	public ConceptResults retrieveConceptAncestors(String conceptId) throws ServiceException {
		return retrieveConceptAncestors(conceptId, 0, DEFAULT_LIMIT);
	}

	public ConceptResults retrieveConceptAncestors(String conceptId, int offset, int limit) throws ServiceException {
		return search(">" + conceptId, null, offset, limit);
	}

	public ConceptResults retrieveConceptDescendants(String conceptId) throws ServiceException {
		return retrieveConceptDescendants(conceptId, 0, DEFAULT_LIMIT);
	}

	public ConceptResults retrieveConceptDescendants(String conceptId, int offset, int limit) throws ServiceException {
		return search("<" + conceptId, null, offset, limit);
	}

	public ConceptResults retrieveReferenceSets(int offset, int limit) throws ServiceException {
		// TODO: harden this
		return search("<" + ConceptConstants.REFSET_CONCEPT, null, offset, limit);
	}

	ConceptResults search(String ecQuery) throws ServiceException {
		return search(ecQuery, null, 0, DEFAULT_LIMIT);
	}

	private String preprocessECLQuery(String ecQuery) throws InvalidECLSyntaxException, NotFoundException, InternalError {
		String luceneQuery;
		try {
			luceneQuery = elToLucene.parse(ecQuery);
			logger.info("ec:'{}', unprocessed-lucene:'{}'", ecQuery, luceneQuery);
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
		return luceneQuery;
	}

	private ConceptIdResults getConceptIdResults(Query query, int offset, int limit) throws InternalError {
		try {
			if (offset < 0) offset = 0;
			final int fetchLimit = limit == -1 ? Integer.MAX_VALUE : limit + offset;
			final TopDocs topDocs = indexSearcher.search(query, fetchLimit);
			final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int total = (int) topDocs.totalHits;
			List<Long> conceptIds = new LongArrayList();
			for (int a = offset; a < scoreDocs.length; a++) {
				String conceptId = getConceptId(scoreDocs[a]);
				conceptIds.add(Long.parseLong(conceptId));
			}
			return new ConceptIdResults(conceptIds, offset, total, limit);
		} catch (IOException e) {
			throw new InternalError("Error performing search.", e);
		}
	}

	private ConceptResults getConceptResults(Query query, int offset, int limit) throws ServiceException {
		try {
			if (offset < 0) offset = 0;
			final int fetchLimit = limit == -1 ? Integer.MAX_VALUE : limit + offset;

			final TopDocs topDocs = indexSearcher.search(query, fetchLimit, new Sort(SortField.FIELD_SCORE,
					new SortedNumericSortField(ConceptFieldNames.FSN_LENGTH, SortField.Type.INT)));

			final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int total = (int) topDocs.totalHits;
			List<ConceptResult> concepts = new ArrayList<>();
			Map<String, ConceptResult> conceptsMap = new HashMap<>();
			for (int a = offset; a < scoreDocs.length; a++) {
				ScoreDoc scoreDoc = scoreDocs[a];
				final ConceptResult conceptResult = getConceptResult(getDocument(scoreDoc));
				conceptsMap.put(conceptResult.getId(), conceptResult);
				concepts.add(conceptResult);
			}

			return new ConceptResults(concepts, offset, total, limit);
		} catch (IOException e) {
			throw new InternalError("Error performing search.", e);
		}
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
			conceptRelatives = Lists.newArrayList(getConceptDocument(conceptId).getValues(ConceptFieldNames.ANCESTOR));
		} else {
			final TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(ConceptFieldNames.ANCESTOR, conceptId)), Integer.MAX_VALUE);
			conceptRelatives = new ArrayList<>();
			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
				conceptRelatives.add(getConceptId(scoreDoc));
			}
		}
		if (internalFunction.isIncludeSelf()) {
			conceptRelatives.add(conceptId);
		}
		if (conceptRelatives.isEmpty()) {
			logger.warn(internalFunction.name() + " internalFunction returned empty result therefore the default value 0 is used.");
			conceptRelatives.add("0");
		}
		String newLuceneQuery = luceneQuery.replace(matcher.group(1), buildOptionsList(conceptRelatives, !internalFunction.isAttributeType()));
		logger.info("Processed statement of internal query. Before:'{}', After:'{}'", limitStringLength(luceneQuery, 100), limitStringLength(newLuceneQuery, 100));
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
					relativesIdBuilder.append(ConceptFieldNames.ID).append(":");
				}
				relativesIdBuilder.append(conceptRelative);
			}
			relativesIdBuilder.append(")");
		} else {
			relativesIdBuilder.append("0");
		}
		return relativesIdBuilder.toString();
	}

	private Document getConceptDocument(String conceptId) throws IOException, NotFoundException {
		final TopDocs docs = indexSearcher.search(new TermQuery(new Term(ConceptFieldNames.ID, conceptId)), Integer.MAX_VALUE);
		if (docs.totalHits < 1) {
			throw new ConceptNotFoundException(conceptId);
		}
		return indexSearcher.doc(docs.scoreDocs[0].doc);
	}

	private Document getDocument(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc);
	}

	private String getConceptId(ScoreDoc scoreDoc) throws IOException {
		return indexSearcher.doc(scoreDoc.doc, CONCEPT_FIELD_SET).getValues(ConceptFieldNames.ID)[0];
	}

	private ConceptResult getConceptResult(Document document) throws IOException, NotFoundException {
		final String[] memberOfRefsetIds = document.getValues(ConceptFieldNames.MEMBER_OF);
		List<RefsetMembershipResult> memberOfRefsets = new ArrayList<>();
		for (String memberOfRefsetId : memberOfRefsetIds) {
			memberOfRefsets.add(getRefsetMembershipResult(memberOfRefsetId));
		}

		return new ConceptResult(
				document.get(ConceptFieldNames.ID),
				document.get(ConceptFieldNames.EFFECTIVE_TIME),
				document.get(ConceptFieldNames.ACTIVE),
				document.get(ConceptFieldNames.MODULE_ID),
				document.get(ConceptFieldNames.DEFINITION_STATUS_ID),
				document.get(ConceptFieldNames.FSN),
				memberOfRefsets);
	}

	private RefsetMembershipResult getRefsetMembershipResult(String memberOfRefsetId) throws IOException, NotFoundException {
		final RefsetMembershipResult refsetMembershipResult = refsetResultMap.get(memberOfRefsetId);
		if (refsetMembershipResult != null) {
			return refsetMembershipResult;
		}
		final Document conceptDocument = getConceptDocument(memberOfRefsetId);
		final RefsetMembershipResult membershipResult = new RefsetMembershipResult(memberOfRefsetId, conceptDocument.get(ConceptFieldNames.FSN));
		refsetResultMap.put(memberOfRefsetId, membershipResult);
		return membershipResult;
	}

	private QueryParser getQueryParser() {
		QueryParser parser = new QueryParser(ConceptFieldNames.ID, analyzer);
		parser.setAllowLeadingWildcard(true);
		return parser;
	}
	
	private String limitStringLength(String string, int limit) {
		return string.length() > limit ? string.substring(0, limit) : string;
	}

}
