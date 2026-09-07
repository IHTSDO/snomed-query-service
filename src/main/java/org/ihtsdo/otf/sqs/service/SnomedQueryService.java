package org.ihtsdo.otf.sqs.service;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FloatPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.index.NumericDocValues;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.MultiTerms;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.ihtsdo.otf.sqs.domain.ConceptConstants;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.domain.DescriptionFieldNames;
import org.ihtsdo.otf.sqs.domain.RelationshipFieldNames;
import org.ihtsdo.otf.sqs.service.dto.*;
import org.ihtsdo.otf.sqs.service.exception.InternalError;
import org.ihtsdo.otf.sqs.service.exception.*;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snomed.langauges.ecl.ECLException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.ihtsdo.otf.sqs.domain.ConceptFieldNames.*;
import static org.ihtsdo.otf.sqs.service.ExpressionConstraintToLuceneConverter.*;

public class SnomedQueryService {

	public static final int DEFAULT_LIMIT = 1000;
	public static final Pattern SCTID_PATTERN = Pattern.compile("\\d{6,18}");
	/** Opening text of a converted {@code !=} clause. */
	private static final String NOT_IN_CLAUSE_START = "(* NOT";
	/**
	 * Prefix of the token that replaces a {@code (* NOT ...)} clause. The
	 * excluded ids follow, separated by {@link #NOT_IN_SEPARATOR}, so the token
	 * carries everything {@code CustomizedQueryParser} needs to build the
	 * complement - no state is held between building the text and parsing it.
	 */
	private static final String NOT_IN_TOKEN = "__notin__";
	/** Safe inside a query token: the classic parser gives no meaning to it. */
	private static final char NOT_IN_SEPARATOR = '_';
	/** Replaces an {@code attr != *} clause, which no document can satisfy. */
	private static final String MATCH_NOTHING_TOKEN = "__matchnothing__";
	/** Restores the pre-existing range-chain rendering. */
	static final String RANGE_FORM_PROPERTY = "sqs.notin.rangeform";
	private final ExpressionConstraintToLuceneConverter eclToLucene;
	private final IndexSearcher indexSearcher;
	private final Analyzer analyzer;
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final Map<String, RefsetMembershipResult> refsetResultMap = new ConcurrentHashMap<>();

	private static final Set<String> CONCEPT_FIELD_SET = Collections.singleton(ConceptFieldNames.ID);
	protected static final Map<InternalFunction, Pattern> internalFunctionPatternMap = new TreeMap<>();
	static {
		for (InternalFunction internalFunction : InternalFunction.values()) {
			internalFunctionPatternMap.put(internalFunction, Pattern.compile(".*(" + internalFunction + "\\(([^\\)]+)\\)).*"));
		}
	}

	public SnomedQueryService(@Autowired  ReleaseStore releaseStore) throws IOException {
		eclToLucene = new ExpressionConstraintToLuceneConverter();
		indexSearcher = new IndexSearcher(DirectoryReader.open(releaseStore.getDirectory()));
		analyzer = releaseStore.createAnalyzer();
		BooleanQuery.setMaxClauseCount(4 * 100 * 1000);
	}

	public long getConceptCount() throws IOException {
		return indexSearcher.collectionStatistics(ConceptFieldNames.ID).docCount();
	}

	public ConceptResult retrieveConceptByDescriptionId(String descriptionId) throws ServiceException {
		try {
			TopDocs conceptDocs = indexSearcher.search(new TermQuery(new Term(ConceptFieldNames.DESCRIPTION_IDS, descriptionId)), 1);
			if (conceptDocs.totalHits.value > 0) {
				Document conceptDoc = getDocument(conceptDocs.scoreDocs[0]);
				return getConceptResult(conceptDoc);
			}
			return null;
		} catch (IOException e) {
			throw new NotFoundException("Could not find concept by description id.");
		}
	}

	public ConceptResult retrieveConcept(String conceptId) throws ServiceException {
		final List<ConceptResult> results = search(conceptId).items();
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
		logger.trace("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, limitStringLength(query.toString(), 200), conceptResults.total());
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

	Query getECLQuery(String ecQuery) throws InvalidECLSyntaxException, NotFoundException, InternalError {
		if (ecQuery == null || ecQuery.isEmpty()) {
			return null;
		}
		String luceneQueryString = preprocessECLQuery(ecQuery);
		try {
			return getQueryParser().parse(luceneQueryString);
		} catch ( ParseException e) {
			throw new InternalError("Error parsing internal search query.", e);
		}
	}

	public ConceptIdResults eclQueryReturnConceptIdentifiers(String ecQuery, int offset, int limit) throws ServiceException {
		if (ecQuery != null && !ecQuery.isEmpty()) {
			String luceneQuery = preprocessECLQuery(ecQuery);
			try {
				Query query = getQueryParser().parse(luceneQuery);
				final ConceptIdResults conceptIdResults = getConceptIdResults(query, offset, limit);
				logger.trace("ec:'{}', lucene:'{}', totalHits:{}", ecQuery, limitStringLength(luceneQuery, 200), conceptIdResults.total());
				return conceptIdResults;
			} catch (ParseException e) {
				throw new InternalError("Error parsing internal search query.", e);
			}
		}
		return new ConceptIdResults(new ArrayList<>(), offset, 0, limit);
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
			luceneQuery = eclToLucene.parse(ecQuery);
			logger.trace("ec:'{}', unprocessed-lucene:'{}'", ecQuery, luceneQuery);
		} catch (ECLException e) {
			throw new InvalidECLSyntaxException(ecQuery, e);
		}
		try {
			for (InternalFunction internalFunction : internalFunctionPatternMap.keySet()) {
				while (luceneQuery.contains(internalFunction.name())) {
					luceneQuery = processInternalFunction(luceneQuery, internalFunction);
				}
			}
		} catch (IOException e) {
			throw new InternalError("Error preparing internal search query.", e);
		}
		// process NOT equal TO
		if (luceneQuery.contains("* NOT")) {
			return processQueryWithNotEqualTo(luceneQuery);
		}
		return luceneQuery;
	}

	private ConceptIdResults getConceptIdResults(Query query, int offset, int limit) throws InternalError {
		try {
			if (offset < 0) offset = 0;
			final int fetchLimit = limit == -1 ? Integer.MAX_VALUE : limit + offset;
			final TopDocs topDocs = indexSearcher.search(query, fetchLimit);
			final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int total = (int) topDocs.totalHits.value;
			return new ConceptIdResults(readConceptIds(scoreDocs, offset), offset, total, limit);
		} catch (IOException e) {
			throw new InternalError("Error performing search.", e);
		}
	}

	/**
	 * Every concept with at least one of {@code ancestorIds} among its
	 * ancestors.
	 *
	 * <p>Exists so a caller does not have to ask one concept at a time.
	 * MRCM's lateralizable-domain check was building an ECL string per
	 * candidate concept - {@code ">" + conceptId} - each a distinct string so
	 * none of it could be cached and every one paid a fresh ECL parse. The
	 * ancestor relation is already indexed per concept, so the same question
	 * is one term-set query over that field.
	 *
	 * <p>{@code TermInSetQuery} rather than a boolean OR of term queries: the
	 * set is prefix-compressed and holds no automata, so a large ancestor set
	 * stays cheap.
	 *
	 * <p>Proper ancestors only. The ANCESTOR field is written from the
	 * inferred and stated ancestor ids, which never include the concept
	 * itself, so a given id is returned only when it descends from another
	 * given id. A caller wanting ECL {@code <<} semantics must union the input
	 * back in.
	 */
	public List<Long> conceptsWithAnyAncestor(Collection<Long> ancestorIds) throws ServiceException {
		if (ancestorIds == null || ancestorIds.isEmpty()) {
			return List.of();
		}
		List<BytesRef> terms = new ArrayList<>(ancestorIds.size());
		for (Long id : ancestorIds) {
			terms.add(new BytesRef(Long.toString(id)));
		}
		return getConceptIdResults(new TermInSetQuery(ConceptFieldNames.ANCESTOR, terms), 0, -1).conceptIds();
	}

	/**
	 * Reads the concept id of every hit from doc values.
	 *
	 * <p>This used to fetch a STORED field per hit, which decompresses a
	 * stored-fields block to read one number. Measured over the 435 domain
	 * and cardinality expressions release-mrcm-validator runs on the SNOMED
	 * CT-AU edition, 2,905,758 hits in total: 63.6 s reading the stored id,
	 * 13.5 s reading doc values, with identical results in identical order.
	 *
	 * <p>Hit order is preserved exactly. Doc values must be read in
	 * non-decreasing docid order per segment, so hits are bucketed by segment
	 * and sorted for the read, then scattered back to their original positions -
	 * callers that page with offset/limit, or report the first N failures, see
	 * the same sequence as before. That is what keeps findings identical rather
	 * than merely equivalent as a set.
	 */
	private List<Long> readConceptIds(ScoreDoc[] scoreDocs, int offset) throws IOException {
		final int count = Math.max(0, scoreDocs.length - offset);
		final LongArrayList conceptIds = new LongArrayList(count);
		if (count == 0) {
			return conceptIds;
		}
		conceptIds.size(count);

		final List<LeafReaderContext> leaves = indexSearcher.getIndexReader().leaves();

		// Doc values must be read in non-decreasing docid order per segment, but
		// hits arrive in score order, so a sort is unavoidable. It is done in
		// fixed-size chunks against ONE reusable primitive array: a whole-result
		// sort would allocate proportionally to the hit count, and the biggest
		// expressions here return over 125,000 hits on eight threads at once,
		// inside a phase that already peaks near the heap ceiling. Bounding the
		// scratch is the same lesson as batching the axiom conversion.
		final int chunk = Math.min(count, 8192);
		final long[] scratch = new long[chunk];

		for (int start = 0; start < count; start += chunk) {
			final int size = Math.min(chunk, count - start);

			// docid in the high half, output slot in the low half, so sorting
			// the packed value sorts by docid and carries the slot with it
			for (int i = 0; i < size; i++) {
				scratch[i] = (((long) scoreDocs[offset + start + i].doc) << 32) | i;
			}
			Arrays.sort(scratch, 0, size);

			int leafIndex = 0;
			NumericDocValues docValues = null;
			int leafBase = 0;
			int leafMax = -1;

			for (int i = 0; i < size; i++) {
				final int docId = (int) (scratch[i] >>> 32);
				final int slot = start + (int) (scratch[i] & 0xFFFFFFFFL);
				if (docId >= leafMax) {
					while (leafIndex < leaves.size()) {
						final LeafReaderContext leaf = leaves.get(leafIndex++);
						leafBase = leaf.docBase;
						leafMax = leafBase + leaf.reader().maxDoc();
						if (docId < leafMax) {
							docValues = leaf.reader().getNumericDocValues(ConceptFieldNames.ID_DOC_VALUES);
							break;
						}
					}
				}
				if (docValues == null || !docValues.advanceExact(docId - leafBase)) {
					// An index written before the doc-values field existed.
					// Fall back per hit so an old index still reads correctly,
					// only slowly.
					conceptIds.set(slot, Long.parseLong(getConceptId(new ScoreDoc(docId, 0f))));
					continue;
				}
				conceptIds.set(slot, docValues.longValue());
			}
		}
		return conceptIds;
	}

	private ConceptResults getConceptResults(Query query, int offset, int limit) throws ServiceException {
		try {
			if (offset < 0) offset = 0;
			final int fetchLimit = limit == -1 ? Integer.MAX_VALUE : limit + offset;

			final TopDocs topDocs = indexSearcher.search(query, fetchLimit, new Sort(SortField.FIELD_SCORE,
					new SortedNumericSortField(ConceptFieldNames.FSN_LENGTH, SortField.Type.INT)));

			final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			int total = (int) topDocs.totalHits.value;
			List<ConceptResult> concepts = new ArrayList<>();
			for (int a = offset; a < scoreDocs.length; a++) {
				ScoreDoc scoreDoc = scoreDocs[a];
				final ConceptResult conceptResult = getConceptResult(getDocument(scoreDoc));
				concepts.add(conceptResult);
			}

			return new ConceptResults(concepts, offset, total, limit);
		} catch (IOException e) {
			throw new InternalError("Error performing search.", e);
		}
	}

	private String processInternalFunction(String luceneQuery, InternalFunction internalFunction) throws IOException, NotFoundException {
		String newLuceneQuery;
		final Matcher matcher = internalFunctionPatternMap.get(internalFunction).matcher(luceneQuery);
		if (!matcher.matches() || matcher.groupCount() != 2) {
			final String message = "Failed to extract the id from the function " + internalFunction + " in internal query '" + luceneQuery + "'";
			logger.error(message);
			throw new IllegalStateException(message);
		}
		List<String> conceptRelatives = getConceptRelatives(internalFunction, matcher.group(2));
		newLuceneQuery = luceneQuery.replace(matcher.group(1), buildOptionsList(conceptRelatives, !internalFunction.isAttributeType()));
		logger.debug("Processed statement of internal query. Before:'{}', After:'{}'", limitStringLength(luceneQuery, 200), limitStringLength(newLuceneQuery, 200));
		return newLuceneQuery;
	}
	
	private List<String> getConceptRelatives(InternalFunction internalFunction, String conceptId) throws IOException, NotFoundException {
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
			logger.warn("{} internalFunction returned empty result therefore the default value 0 is used.", internalFunction.name());
			conceptRelatives.add("0");
		}
		return conceptRelatives;
	}

	private String processQueryWithNotEqualTo(String luceneQuery) {
		// specific logic for range query for ECL with != e.g *:272741003 != << 442083009
		// * AND 260686004: (* NOT ((360314001 OR 129264002) OR (405813007)))
		//
		// "value NOT in S" is the COMPLEMENT of S: a document matches when it
		// carries some value outside S. This used to be rendered as one {a TO b}
		// range per gap between consecutive members of S, and the classic parser
		// builds a state machine for every one of those ranges.
		//
		// Each clause is replaced by a token naming the excluded ids, which
		// CustomizedQueryParser turns into term sets over the values the field
		// actually holds (see notInQuery). The ids are already in the text, so
		// the token carries everything the parser needs and nothing is held on
		// the side.
		//
		// Each clause is located by scanning for its balanced closing bracket
		// rather than by regex. The pattern that used to find them,
		// .*(\(\* NOT.*\)).*, is greedy at both ends. The leading .* backtracks
		// to the LAST "(* NOT", so of several != clauses only the last was
		// rewritten and the others reached the parser as "attr:(* NOT X)",
		// which means "has a value and none of them is X" rather than "has a
		// value other than X".
		// The trailing .*\) runs to the last ")" of the whole query, so any
		// bracketed clause after the != clause was swallowed into it: in
		// "a != X AND b = << Y" the ids of b and of Y's descendants were
		// harvested as excluded values of a, and the b clause disappeared.
		// Rewriting right to left keeps the earlier offsets valid.
		StringBuilder rewritten = new StringBuilder(luceneQuery);
		for (int start = lastNotInClause(rewritten, rewritten.length());
				start >= 0;
				start = lastNotInClause(rewritten, start)) {
			int end = closingBracket(rewritten, start);
			if (end < 0) {
				// Unbalanced, so the extent of the clause is unknown. Leaving it
				// alone keeps the pre-existing behaviour for a query that would
				// not have parsed anyway.
				break;
			}
			String clause = rewritten.substring(start, end + 1);

			List<String> conceptRelatives = new ArrayList<>();
			if (clause.contains("(0)")) {
				conceptRelatives.add("0");
			} else {
				Matcher sctIdMatcher = SCTID_PATTERN.matcher(clause);
				while (sctIdMatcher.find()) {
					conceptRelatives.add(sctIdMatcher.group());
				}
			}
			Collections.sort(conceptRelatives);

			String replacement;
			if (conceptRelatives.isEmpty()) {
				// "attr != *": every value is excluded, so no value lies outside
				// the set and nothing matches. Every other value form names at
				// least one id, or the "(0)" placeholder for an empty expansion,
				// so this is the only way to get here.
				replacement = MATCH_NOTHING_TOKEN;
			} else if (Boolean.getBoolean(RANGE_FORM_PROPERTY)) {
				// Escape hatch, and the mechanism the equivalence probe uses to
				// run both forms against one index in one process.
				replacement = "(" + buildRangeList(conceptRelatives) + ")";
			} else {
				replacement = NOT_IN_TOKEN + String.join(String.valueOf(NOT_IN_SEPARATOR), conceptRelatives);
			}
			rewritten.replace(start, end + 1, replacement);
		}
		return rewritten.toString();
	}

	/** The last {@code (* NOT} beginning before {@code before}, or -1. */
	private static int lastNotInClause(CharSequence text, int before) {
		return text.toString().lastIndexOf(NOT_IN_CLAUSE_START, before - 1);
	}

	/**
	 * The index of the bracket closing the one at {@code open}, or -1 if the
	 * brackets do not balance.
	 */
	private static int closingBracket(CharSequence text, int open) {
		int depth = 0;
		for (int i = open; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '(') {
				depth++;
			} else if (c == ')') {
				depth--;
				if (depth == 0) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Documents holding a value of {@code field} that is not in {@code excluded},
	 * scored exactly as the range chain scored them.
	 *
	 * <p>The range chain is one constant-scoring {@code {a TO b}} clause per gap
	 * between consecutive excluded ids, so a document scored one point for each
	 * gap it holds a value in, and hits came back in that order. A single term
	 * set would score every match the same and return hits in index order
	 * instead, which changes which concepts a caller reporting the first N
	 * results names. So the values outside the set are grouped by gap, one
	 * {@link TermInSetQuery} per non-empty gap: the same score per document,
	 * at most as many clauses as the chain had, and no state machines.
	 *
	 * <p>Only values the field actually holds are listed. A value that is not
	 * in the index cannot match, so the documents selected are the same.
	 *
	 * @param field    the attribute the clause constrains, as given to the parser
	 * @param excluded the set whose complement is wanted
	 */
	private Query notInQuery(String field, List<BytesRef> excluded) throws IOException {
		Terms terms = MultiTerms.getTerms(indexSearcher.getIndexReader(), field);
		if (terms == null) {
			// No document carries any value for this attribute, so none can
			// carry one outside the set.
			return new MatchNoDocsQuery("no values for " + field);
		}
		BooleanQuery.Builder gaps = new BooleanQuery.Builder();
		Query onlyGap = null;
		int gapCount = 0;
		List<BytesRef> gap = new ArrayList<>();
		// excluded.get(bound) is the first excluded id not below the current
		// term, so bound numbers the gap the term falls in
		int bound = 0;
		TermsEnum it = terms.iterator();
		for (BytesRef term = it.next(); term != null; term = it.next()) {
			int previousBound = bound;
			while (bound < excluded.size() && excluded.get(bound).compareTo(term) < 0) {
				bound++;
			}
			if (bound != previousBound && !gap.isEmpty()) {
				onlyGap = new TermInSetQuery(field, gap);
				gaps.add(onlyGap, BooleanClause.Occur.SHOULD);
				gapCount++;
				gap = new ArrayList<>();
			}
			if (bound < excluded.size() && excluded.get(bound).bytesEquals(term)) {
				continue;
			}
			// Copied: TermsEnum reuses the BytesRef it returns.
			gap.add(BytesRef.deepCopyOf(term));
		}
		if (!gap.isEmpty()) {
			onlyGap = new TermInSetQuery(field, gap);
			gaps.add(onlyGap, BooleanClause.Occur.SHOULD);
			gapCount++;
		}
		if (gapCount == 0) {
			// Every value present is in the set, so nothing lies outside it.
			return new MatchNoDocsQuery("every value of " + field + " is excluded");
		}
		return gapCount == 1 ? onlyGap : gaps.build();
	}

	private String buildRangeList(List<String> conceptRelatives) {
		StringBuilder builder = new StringBuilder();
		builder.append("{* TO ");
		String previous = null;
		for (String conceptRelative : conceptRelatives) {
			if (previous != null) {
				builder.append(" OR {");
				builder.append(previous);
				builder.append(" TO ");
			}
			builder.append(conceptRelative);
			builder.append("}");
			previous = conceptRelative;
		}
		if (!conceptRelatives.isEmpty()) {
			builder.append(" OR {");
			builder.append(previous);
			builder.append(" TO * }");
		}
		return builder.toString();
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
		if (docs.totalHits.value < 1) {
			throw new ConceptNotFoundException(conceptId);
		}
		return indexSearcher.doc(docs.scoreDocs[0].doc);
	}

	private Document getDescriptionDocument(String descriptionId) throws IOException, NotFoundException {
		final TopDocs docs = indexSearcher.search(new TermQuery(new Term(DescriptionFieldNames.ID, descriptionId)), 1);
		if (docs.totalHits.value < 1) {
			throw new NotFoundException("Description not found with id " + descriptionId);
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
		final String[] parents = document.getValues(ConceptConstants.isA);
		return new ConceptResult(
				document.get(ConceptFieldNames.ID),
				document.get(ConceptFieldNames.EFFECTIVE_TIME),
				document.get(ConceptFieldNames.ACTIVE),
				document.get(ConceptFieldNames.MODULE_ID),
				document.get(ConceptFieldNames.DEFINITION_STATUS_ID),
				document.get(ConceptFieldNames.FSN),
				memberOfRefsets,
				Set.of(parents));
	}

	private RelationshipResult getRelationshipResult(Document document) {
		return new RelationshipResult(
				document.get(RelationshipFieldNames.ID),
				document.get(RelationshipFieldNames.EFFECTIVE_TIME),
				document.get(RelationshipFieldNames.ACTIVE),
				document.get(RelationshipFieldNames.MODULE_ID),
				document.get(RelationshipFieldNames.SOURCE_ID),
				document.get(RelationshipFieldNames.DESTINATION_ID),
				document.get(RelationshipFieldNames.RELATIONSHIP_GROUP),
				document.get(RelationshipFieldNames.TYPE_ID),
				document.get(RelationshipFieldNames.CHARACTERISTIC_TYPE_ID),
				document.get(RelationshipFieldNames.MODIFIER_ID));
	}

	private DescriptionResult getDescriptionResult(Document document) {
		return new DescriptionResult(
				document.get(DescriptionFieldNames.ID),
				document.get(DescriptionFieldNames.CONCEPT_ID),
				document.get(DescriptionFieldNames.TERM));
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
		QueryParser parser = new CustomizedQueryParser(ConceptFieldNames.ID, analyzer);
		parser.setAllowLeadingWildcard(true);
		return parser;
	}

	// Implement a customized query parser to create the RangeQuery for numeric fields
	// The alternative option is to use StandardQueryParser and set the dynamic field name in the PointsConfigMap
	//
	// An inner class, not static, so the complement can be read from this
	// service's index at the moment the clause is parsed.
	private class CustomizedQueryParser extends QueryParser {

		public CustomizedQueryParser(String f, Analyzer a) {
			super(f, a);
		}

		/**
		 * Resolves the tokens {@code processQueryWithNotEqualTo} writes: a
		 * {@code __notin__} token becomes the term sets of {@code notInQuery},
		 * and the match-nothing token a query matching no
		 * document.
		 *
		 * <p>Overridden rather than analysed: the token is not a term and must
		 * not be passed through the analyzer. The field comes from the parser,
		 * and the excluded ids from the token, so a query may carry as many of
		 * these clauses as it likes and each is resolved against its own field.
		 */
		@Override
		protected Query getFieldQuery(String field, String queryText, boolean quoted) throws ParseException {
			if (queryText.equals(MATCH_NOTHING_TOKEN)) {
				return new MatchNoDocsQuery("every value of " + field + " is excluded");
			}
			if (queryText.startsWith(NOT_IN_TOKEN)) {
				List<BytesRef> excluded = new ArrayList<>();
				for (String id : queryText.substring(NOT_IN_TOKEN.length()).split(String.valueOf(NOT_IN_SEPARATOR))) {
					excluded.add(new BytesRef(id));
				}
				// Term order, which is the order the gaps are walked in.
				Collections.sort(excluded);
				try {
					return notInQuery(field, excluded);
				} catch (IOException e) {
					throw new ParseException("Could not enumerate the terms of field " + field + ": " + e);
				}
			}
			return super.getFieldQuery(field, queryText, quoted);
		}

		@Override
		protected Query getRangeQuery(String field, String part1, String part2, boolean startInclusive, boolean endInclusive) throws ParseException {
			if (field.contains("_value") || field.contains(TOTAL_GROUPS)
					|| field.contains(CARDINALITY) || field.contains(GROUP_CARDINALITY)) {
				float min = 0f;
				float max = Integer.MAX_VALUE;
				if (part1 != null) {
					min = Float.parseFloat(part1);
					if (!startInclusive) {
						min += 0.00001f;
					}
				}
				if (part2 != null) {
					max = Float.parseFloat(part2);
					if (!endInclusive) {
						max -= 0.00001f;
					}
				}
				return FloatPoint.newRangeQuery(field, min, max);
			} else {
				return super.getRangeQuery(field, part1, part2, startInclusive, endInclusive);
			}
		}
	}

	private String limitStringLength(String string, int limit) {
		return string.length() > limit ? string.substring(0, limit) : string;
	}

}
