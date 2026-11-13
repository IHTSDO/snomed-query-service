package org.ihtsdo.otf.sqs.service;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryVisitor;
import org.apache.lucene.util.automaton.ByteRunAutomaton;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.sqs.domain.ConceptConstants;
import org.ihtsdo.otf.sqs.service.dto.ConceptIdResults;
import org.ihtsdo.otf.sqs.service.dto.ConceptResult;
import org.ihtsdo.otf.sqs.service.dto.ConceptResults;
import org.ihtsdo.otf.sqs.service.exception.NotFoundException;
import org.ihtsdo.otf.sqs.service.exception.ServiceException;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class IntegrationTest {

	private SnomedQueryService snomedQueryService;

	@Before
	public void setup() throws Exception {
		final ReleaseStore releaseStore = new TestReleaseImportManager(true).buildTestTaxonomy(LoadingProfile.light.withoutInactiveConcepts());
		snomedQueryService = new SnomedQueryService(releaseStore);
	}

	@Test
	public void adhocTest() throws ServiceException {
		//<< 404684003 |Clinical finding (finding)|: 363698007 = *
		//<< 363787002 |Observable entity (observable entity)|: 704318007 = *"
		//* MINUS (<<91723000  OR <<723264001) :  272741003=*
		//* MINUS <<91723000 : 272741003=*
		// * MINUS (<<91723000  OR <<723264001) :  272741003=*
		// <<71388002: 363703001 = *
		// <<71388002: [0..1] 363703001 = <<363675004
		// <<404684003: [0..1] 3311487004 != #10
		// *:260686004 != < 129264002
		// <<71388002: [0..1] 363703001 != <<363675004
		ConceptIdResults results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:260686004 != << 129264002", 0, -1);
		assertNotNull(results);
		results.conceptIds().forEach(System.out::println);
	}

	/**
	 * Several {@code !=} clauses in one query must give the intersection of
	 * each clause alone.
	 *
	 * <p>The two fields are chosen so the expected intersection is NOT empty.
	 * An assertion against an empty oracle would be satisfied by any regression
	 * that silently empties the result.
	 */
	@Test
	public void testTwoNotEqualClausesOnDifferentFields() throws ServiceException {
		assertIntersection("*:260686004 != 360314001 AND 405813007 != 129264002",
				"*:260686004 != 360314001", "*:405813007 != 129264002");
	}

	/**
	 * Of several {@code !=} clauses, the regex that used to locate them
	 * rewrote only the last; the others reached the parser as
	 * {@code attr:(* NOT X)}, which means "has a value and none of them is X"
	 * rather than "has a value other than X". 8367003 has two methods,
	 * 360314001 and 72651009, so
	 * it satisfies the first clause through 72651009 - and the old rendering
	 * dropped it because it also has 360314001.
	 */
	@Test
	public void testEveryNotEqualClauseIsRewrittenNotJustTheLast() throws ServiceException {
		assertEquals(Set.of(8367003L), assertIntersection(
				"*:260686004 != 360314001 AND 260686004 != 129264002",
				"*:260686004 != 360314001", "*:260686004 != 129264002"));
	}

	/**
	 * A bracketed clause after a {@code !=} clause. The regex that used to
	 * locate the clause ran to the last closing bracket of the query, so it
	 * harvested 405813007 and the descendants of 442083009 as excluded values
	 * of 260686004, and the procedure site clause disappeared: 8367003, which
	 * has no procedure site, was returned.
	 */
	@Test
	public void testClauseAfterNotEqualIsNotSwallowed() throws ServiceException {
		assertEquals(Set.of(128927009L), assertIntersection(
				"*:260686004 != 363675004 AND 405813007 = << 442083009",
				"*:260686004 != 363675004", "*:405813007 = << 442083009"));
	}

	/** Two clauses whose excluded sets are identical, so their text is too. */
	@Test
	public void testTwoNotEqualClausesWithTheSameValueSet() throws ServiceException {
		List<Long> both = snomedQueryService.eclQueryReturnConceptIdentifiers(
				"*:260686004 != 129264002 AND 363698007 != 129264002", 0, -1).conceptIds();
		assertNotNull(both);

		List<Long> first = snomedQueryService.eclQueryReturnConceptIdentifiers(
				"*:260686004 != 129264002", 0, -1).conceptIds();
		List<Long> second = snomedQueryService.eclQueryReturnConceptIdentifiers(
				"*:363698007 != 129264002", 0, -1).conceptIds();

		Set<Long> expected = new HashSet<>(first);
		expected.retainAll(new HashSet<>(second));
		assertEquals(expected, new HashSet<>(both));
	}

	/**
	 * {@code != *} excludes every value, so nothing can lie outside it. It is
	 * answered with a query that matches nothing; the range chain could not
	 * render it and the query failed to parse.
	 */
	@Test
	public void testNotEqualToWildcardMatchesNothing() throws ServiceException {
		assertEquals(List.of("MatchNoDocsQuery", "TermQuery:type"), leafQueries("*:260686004 != *"));
		assertEquals(List.of(), snomedQueryService.eclQueryReturnConceptIdentifiers(
				"*:260686004 != *", 0, -1).conceptIds());
	}

	/**
	 * Every {@code !=} clause is answered with a term set over its own field,
	 * including each clause of a query that carries several, and none falls
	 * back to the range chain or reaches the index as a literal token.
	 */
	@Test
	public void testEveryNotEqualClauseIsAnsweredWithATermSet() throws ServiceException {
		assertEquals(List.of("TermInSetQuery:260686004", "TermQuery:type"),
				leafQueries("*:260686004 != 129264002"));
		assertEquals(List.of("TermInSetQuery:260686004", "TermInSetQuery:405813007", "TermQuery:type"),
				leafQueries("*:260686004 != 129264002 AND 405813007 != 129264002"));
	}

	/** The escape hatch must restore the range chain, and give the same hits in the same order. */
	@Test
	public void testRangeFormPropertyRestoresTheOldRendering() throws ServiceException {
		String ecl = "*:260686004 != 442083009";
		List<Long> termSetForm = snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
		System.setProperty(SnomedQueryService.RANGE_FORM_PROPERTY, "true");
		try {
			assertEquals(List.of("TermQuery:type", "TermRangeQuery:260686004", "TermRangeQuery:260686004"),
					leafQueries(ecl));
			List<Long> rangeForm = snomedQueryService.eclQueryReturnConceptIdentifiers(ecl, 0, -1).conceptIds();
			assertEquals("both renderings must return the same concepts in the same order", rangeForm, termSetForm);
		} finally {
			System.clearProperty(SnomedQueryService.RANGE_FORM_PROPERTY);
		}
	}

	/**
	 * Proper descendants of any of the given ids. A given id is itself
	 * returned only when it descends from another given id.
	 */
	@Test
	public void testConceptsWithAnyAncestor() throws ServiceException {
		assertEquals(List.of(), snomedQueryService.conceptsWithAnyAncestor(List.of()));
		assertEquals(List.of(360314001L), snomedQueryService.conceptsWithAnyAncestor(List.of(129264002L)));

		long model = Long.parseLong(ConceptConstants.MODEL_CONCEPT);
		Set<Long> expected = new HashSet<>(snomedQueryService.eclQueryReturnConceptIdentifiers("<" + model, 0, -1).conceptIds());
		expected.addAll(snomedQueryService.eclQueryReturnConceptIdentifiers("<129264002", 0, -1).conceptIds());
		List<Long> actual = snomedQueryService.conceptsWithAnyAncestor(List.of(model, 129264002L));
		assertEquals(expected, new HashSet<>(actual));
		assertEquals("no concept may be returned twice", expected.size(), actual.size());
		assertTrue("129264002 descends from the model concept, so it is returned", actual.contains(129264002L));
		assertFalse(actual.contains(model));
	}

	/**
	 * Asserts {@code combined} returns exactly the concepts every one of
	 * {@code clauses} returns alone, and that this is not the empty set.
	 */
	private Set<Long> assertIntersection(String combined, String... clauses) throws ServiceException {
		Set<Long> expected = null;
		for (String clause : clauses) {
			List<Long> ids = snomedQueryService.eclQueryReturnConceptIdentifiers(clause, 0, -1).conceptIds();
			if (expected == null) {
				expected = new HashSet<>(ids);
			} else {
				expected.retainAll(ids);
			}
		}
		assertFalse("the oracle intersection must not be empty, or this proves nothing", expected.isEmpty());
		assertEquals(expected, new HashSet<>(
				snomedQueryService.eclQueryReturnConceptIdentifiers(combined, 0, -1).conceptIds()));
		return expected;
	}

	/** The leaf queries {@code ecl} parses to, as {@code QueryClass:field}, sorted. */
	private List<String> leafQueries(String ecl) throws ServiceException {
		List<String> leaves = new ArrayList<>();
		snomedQueryService.getECLQuery(ecl).visit(new QueryVisitor() {
			@Override
			public void consumeTerms(Query query, Term... terms) {
				for (Term term : terms) {
					assertFalse("a token reached the index as a term: " + term, term.text().contains("__"));
				}
				add(query, terms[0].field());
			}

			@Override
			public void consumeTermsMatching(Query query, String field, Supplier<ByteRunAutomaton> automaton) {
				add(query, field);
			}

			@Override
			public void visitLeaf(Query query) {
				leaves.add(query.getClass().getSimpleName());
			}

			@Override
			public QueryVisitor getSubVisitor(BooleanClause.Occur occur, Query parent) {
				return this;
			}

			private void add(Query query, String field) {
				leaves.add(query.getClass().getSimpleName() + ":" + field);
			}
		});
		Collections.sort(leaves);
		return leaves;
	}

	@Test
	public void testWordSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "action", 0, 10).items();
		assertEquals(2, conceptResults.size());
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testWordWildcardSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "val*", 0, 10).items();
		assertEquals(3, conceptResults.size());
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("363675004")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testExpressionConstraintQuery_wildcardFocusConcept() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*").items();
		assertEquals(18, conceptResults.size());
	}

	@Test
	public void testExpressionConstraintQuery_pagination() throws Exception {
		final List<ConceptResult> fullItems = snomedQueryService.search("*").items();

		ConceptResults results = snomedQueryService.search("*", null, 0, 5);
		assertEquals(0, results.offset());
		assertEquals(18, results.total());
		assertEquals(5, results.limit());
		assertEquals(5, results.items().size());
		assertTrue(results.items().containsAll(fullItems.subList(0, 5)));

		results = snomedQueryService.search("*", null, 0, 10);
		assertEquals(0, results.offset());
		assertEquals(18, results.total());
		assertEquals(10, results.limit());
		assertEquals(10, results.items().size());
		assertTrue(results.items().containsAll(fullItems.subList(0, 10)));

		results = snomedQueryService.search("*", null, 10, 10);
		assertEquals(10, results.offset());
		assertEquals(18, results.total());
		assertEquals(10, results.limit());
		assertEquals(8, results.items().size());
		assertTrue(results.items().containsAll(fullItems.subList(10, 16)));
	}

	@Test
	public void testECLReturnId() throws ServiceException {
		ConceptIdResults idResults = snomedQueryService.eclQueryReturnConceptIdentifiers("442083009", 0, 100);
		assertEquals(0, idResults.offset());
		assertEquals(100, idResults.limit());
		assertEquals(1, idResults.total());
		List<Long> conceptIds = idResults.conceptIds();
		assertEquals(1, conceptIds.size());
		assertEquals(442083009L, conceptIds.get(0).longValue());
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionOnlyRootOverlap() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search(">442083009 AND >8367003").items();
		assertResultSet(conceptResults, 138875005);
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionSomeOverlap() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search(">>128927009 AND >8367003").items();
		assertResultSet(conceptResults, 138875005, 71388002, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfConjunction() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<71388002 AND <128927009").items();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfDisjunction() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<123037004 OR <71388002").items();
		assertResultSet(conceptResults, 442083009, 362961001, 128927009, 8367003, 72651009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfExclusion() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<71388002 MINUS <<362961001").items();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeName() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=*").items();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValue() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=129264002").items();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=<<129264002").items();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=<129264002").items();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=>360314001").items();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=>>360314001").items();
		assertResultSet(conceptResults, 8367003, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_conjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=* AND 405813007=*").items();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_disjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=* OR 405813007=*").items();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery() throws Exception {
		final ConceptResults conceptResults = snomedQueryService.search("< 404684003 |clinical finding|: 116676008 |associated morphology| = ((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)");
		assertNotNull(conceptResults);
		assertEquals(0, conceptResults.total());
	}

	@Test(expected = NotFoundException.class)
	public void testRetrieveConceptNotFound() throws Exception {
		snomedQueryService.retrieveConcept("121111004");
	}

	@Test
	public void testRetrieveConcept() throws Exception {
		final ConceptResult conceptResult = snomedQueryService.retrieveConcept("123037004");
		assertEquals("Body structure (body structure)", conceptResult.getFsn());
		assertEquals("20150731", conceptResult.getEffectiveTime());
		assertTrue(conceptResult.isActive());
		assertEquals("900000000000207008", conceptResult.getModuleId());
		assertEquals("900000000000074008", conceptResult.getDefinitionStatusId());
	}

	@Test
	public void testRetrieveConceptAncestors() throws Exception {
		final List<ConceptResult> ancestors = snomedQueryService.retrieveConceptAncestors("128927009").items();
		assertEquals(2, ancestors.size());
		assertTrue(ancestors.contains(new ConceptResult("138875005")));
		assertTrue(ancestors.contains(new ConceptResult("71388002")));
	}

	@Test
	public void testRetrieveConceptDescendants() throws Exception {
		final List<ConceptResult> descendants = snomedQueryService.retrieveConceptDescendants("71388002").items();
		assertEquals(3, descendants.size());
		assertTrue(descendants.contains(new ConceptResult("362961001")));
		assertTrue(descendants.contains(new ConceptResult("128927009")));
		assertTrue(descendants.contains(new ConceptResult("8367003")));
	}

	@Test
	public void testEclQueryWhenConceptDescendantIsEmpty() throws Exception {
		final ConceptResults result = snomedQueryService.search("<< 105590001 |Substance (substance)|: 726542003 = < 726711005 |Disposition (disposition)|");
		assertEquals(0,result.items().size());
	}
	
	@Test
	public void testAttribute() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:260686004 = *", 0, -1).conceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(128927009L));
		assertTrue(results.contains(8367003L));
		
	}
	
	
	@Test
	public void testAttributeWithCardinality() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("< 373873005 |Pharmaceutical / biologic product| : [1..3]  127489000 |Has active ingredient|  = <  105590001 |Substance|", 0 ,-1);
		assertEquals(0,result.conceptIds().size());
	}
	
	@Test
	public void testAttributeCardinalityWithSpecificDomain() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[1..*] 260686004 = *", 0, -1).conceptIds();
		assertEquals(1,results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
	}
	
	@Test
	public void testAttributeCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:[1..2] 260686004 = *", 0, -1).conceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
		assertTrue(results.contains(Long.parseLong("8367003")));
	}
	
	@Test
	public void testAttributeCardinalityWithoutMatch() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[2..*] 260686004 = *", 0, -1).conceptIds();
		assertEquals(0,results.size());
	}
	
	
	@Test
	public void testAttributeGroupCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..*] { [0..1] 260686004 = *}", 0, -1).conceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithMatchedResults() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..*] { [1..*] 260686004 = *}", 0, -1).conceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
		assertTrue(results.contains(Long.parseLong("8367003")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatch() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..3] { [3..*] 260686004 = *}", 0, -1);
		assertEquals(0,result.conceptIds().size());
	}
	
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatchWithDefaultGroupCardinality() throws Exception {
		final ConceptResults result = snomedQueryService.search("*: [2..*] { 260686004 = *}");
		assertEquals(0,result.items().size());
	}
	
	@Test
	public void testDefaultAttributeGroupCardinality() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { [1..*] 260686004 = *}", 0, -1).conceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
		assertTrue(results.contains(Long.parseLong("8367003")));
	}

	@Test
	public void testDefaultAttributeGroupCardinalities() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { 260686004 = *}", 0, -1).conceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(Long.parseLong("128927009")));
		assertTrue(results.contains(Long.parseLong("8367003")));
	}

	@Test
	public void testConcreteValue() throws ServiceException {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: 3311487004 = #100", 0, -1).conceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(Long.parseLong("404684003")));

		// search with #100.0 should returns the same result as above
		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: 3311487004 = #100.0", 0, -1).conceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(Long.parseLong("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 >= #100", 0, -1).conceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(Long.parseLong("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 > #100", 0, -1).conceptIds();
		assertEquals(0, results.size());

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 > #20", 0, -1).conceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(Long.parseLong("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 != #100", 0, -1).conceptIds();
		assertEquals(0, results.size());

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 != #10", 0, -1).conceptIds();
		assertEquals(1, results.size());
	}

	@Test
	public void testQueryWithNotEqualToOperator() throws Exception {
		// Two concepts have attribute 260686004
		List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004 = *").items();
		assertEquals(2, conceptResults.size());
		List<String> conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));

		// concept 8367003 has two values one (360314001) is a child of 129264002
		// and the other 72651009 | Nail structure (body structure) is not child of 129264002.
		conceptResults = snomedQueryService.search("*:260686004 = < 129264002").items();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));

		// Concept 128927009 has attribute value of 129264002
		conceptResults = snomedQueryService.search("*:260686004 != < 129264002").items();
		assertEquals(2, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));

		// only one should be in the results
		conceptResults = snomedQueryService.search("*:260686004 != << 129264002").items();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 != (<< 129264002 OR << 405813007)").items();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));
	}

	@Test
	public void testQueryWithMinusOperator() throws Exception {
		List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004 = *").items();
		assertEquals(2, conceptResults.size());
		List<String> conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 = < 129264002").items();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 = (* MINUS < 129264002)").items();
		assertEquals(2, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));
	}

	private void assertResultSet(List<ConceptResult> conceptResults, int... conceptIds) {
		Set<ConceptResult> notFound = new HashSet<>();
		Set<ConceptResult> remaining = new HashSet<>(conceptResults);
		for (int conceptId : conceptIds) {
			final ConceptResult testConcept = new ConceptResult(conceptId + "");
			if (conceptResults.contains(testConcept)) {
				remaining.remove(testConcept);
			} else {
				notFound.add(testConcept);
			}
		}
		if (!notFound.isEmpty() || !remaining.isEmpty()) {
			Assert.fail("Results set did not match expected concept ids." + (notFound.isEmpty() ? "" : "\nConcepts not found are: " + notFound) + (!remaining.isEmpty() ? "\nConcept found but not expected found are: " + remaining : ""));
		}
	}

}
