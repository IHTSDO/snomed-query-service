package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
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
		ConceptIdResults results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<71388002: [0..1] 363703001 != <<363675004", 0, -1);
		assertNotNull(results);
		results.getConceptIds().forEach(System.out::println);
	}

	@Test
	public void testWordSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "action", 0, 10).getItems();
		assertEquals(2, conceptResults.size());
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testWordWildcardSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "val*", 0, 10).getItems();
		assertEquals(3, conceptResults.size());
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("363675004")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testExpressionConstraintQuery_wildcardFocusConcept() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*").getItems();
		assertEquals(18, conceptResults.size());
	}

	@Test
	public void testExpressionConstraintQuery_pagination() throws Exception {
		final List<ConceptResult> fullItems = snomedQueryService.search("*").getItems();

		ConceptResults results = snomedQueryService.search("*", null, 0, 5);
		assertEquals(0, results.getOffset());
		assertEquals(18, results.getTotal());
		assertEquals(5, results.getLimit());
		assertEquals(5, results.getItems().size());
		assertTrue(results.getItems().containsAll(fullItems.subList(0, 5)));

		results = snomedQueryService.search("*", null, 0, 10);
		assertEquals(0, results.getOffset());
		assertEquals(18, results.getTotal());
		assertEquals(10, results.getLimit());
		assertEquals(10, results.getItems().size());
		assertTrue(results.getItems().containsAll(fullItems.subList(0, 10)));

		results = snomedQueryService.search("*", null, 10, 10);
		assertEquals(10, results.getOffset());
		assertEquals(18, results.getTotal());
		assertEquals(10, results.getLimit());
		assertEquals(8, results.getItems().size());
		assertTrue(results.getItems().containsAll(fullItems.subList(10, 16)));
	}

	@Test
	public void testECLReturnId() throws ServiceException {
		ConceptIdResults idResults = snomedQueryService.eclQueryReturnConceptIdentifiers("442083009", 0, 100);
		assertEquals(0, idResults.getOffset());
		assertEquals(100, idResults.getLimit());
		assertEquals(1, idResults.getTotal());
		List<Long> conceptIds = idResults.getConceptIds();
		assertEquals(1, conceptIds.size());
		assertEquals(new Long(442083009L), conceptIds.get(0));
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionOnlyRootOverlap() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search(">442083009 AND >8367003").getItems();
		assertResultSet(conceptResults, 138875005);
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionSomeOverlap() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search(">>128927009 AND >8367003").getItems();
		assertResultSet(conceptResults, 138875005, 71388002, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfConjunction() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<71388002 AND <128927009").getItems();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfDisjunction() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<123037004 OR <71388002").getItems();
		assertResultSet(conceptResults, 442083009, 362961001, 128927009, 8367003, 72651009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfExclusion() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("<71388002 MINUS <<362961001").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeName() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=*").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValue() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=129264002").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=<<129264002").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=<129264002").getItems();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=>360314001").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=>>360314001").getItems();
		assertResultSet(conceptResults, 8367003, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_conjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=* AND 405813007=*").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_disjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004=* OR 405813007=*").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery() throws Exception {
		final ConceptResults conceptResults = snomedQueryService.search("< 404684003 |clinical finding|: 116676008 |associated morphology| = ((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)");
		assertNotNull(conceptResults);
		assertEquals(0, conceptResults.getTotal());
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
		final List<ConceptResult> ancestors = snomedQueryService.retrieveConceptAncestors("128927009").getItems();
		assertEquals(2, ancestors.size());
		assertTrue(ancestors.contains(new ConceptResult("138875005")));
		assertTrue(ancestors.contains(new ConceptResult("71388002")));
	}

	@Test
	public void testRetrieveConceptDescendants() throws Exception {
		final List<ConceptResult> descendants = snomedQueryService.retrieveConceptDescendants("71388002").getItems();
		assertEquals(3, descendants.size());
		assertTrue(descendants.contains(new ConceptResult("362961001")));
		assertTrue(descendants.contains(new ConceptResult("128927009")));
		assertTrue(descendants.contains(new ConceptResult("8367003")));
	}

	@Test
	public void testEclQueryWhenConceptDescendantIsEmpty() throws Exception {
		final ConceptResults result = snomedQueryService.search("<< 105590001 |Substance (substance)|: 726542003 = < 726711005 |Disposition (disposition)|");
		assertEquals(0,result.getItems().size());
	}
	
	@Test
	public void testAttribute() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:260686004 = *", 0, -1).getConceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(128927009L));
		assertTrue(results.contains(8367003L));
		
	}
	
	
	@Test
	public void testAttributeWithCardinality() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("< 373873005 |Pharmaceutical / biologic product| : [1..3]  127489000 |Has active ingredient|  = <  105590001 |Substance|", 0 ,-1);
		assertEquals(0,result.getConceptIds().size());
	}
	
	@Test
	public void testAttributeCardinalityWithSpecificDomain() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[1..*] 260686004 = *", 0, -1).getConceptIds();
		assertEquals(1,results.size());
		assertTrue(results.contains(new Long("128927009")));
	}
	
	@Test
	public void testAttributeCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:[1..2] 260686004 = *", 0, -1).getConceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(new Long("128927009")));
		assertTrue(results.contains(new Long("8367003")));
	}
	
	@Test
	public void testAttributeCardinalityWithoutMatch() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[2..*] 260686004 = *", 0, -1).getConceptIds();
		assertEquals(0,results.size());
	}
	
	
	@Test
	public void testAttributeGroupCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..*] { [0..1] 260686004 = *}", 0, -1).getConceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(new Long("128927009")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithMatchedResults() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..*] { [1..*] 260686004 = *}", 0, -1).getConceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(new Long("128927009")));
		assertTrue(results.contains(new Long("8367003")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatch() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..3] { [3..*] 260686004 = *}", 0, -1);
		assertEquals(0,result.getConceptIds().size());
	}
	
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatchWithDefaultGroupCardinality() throws Exception {
		final ConceptResults result = snomedQueryService.search("*: [2..*] { 260686004 = *}");
		assertEquals(0,result.getItems().size());
	}
	
	@Test
	public void testDefaultAttributeGroupCardinality() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { [1..*] 260686004 = *}", 0, -1).getConceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(new Long("128927009")));
		assertTrue(results.contains(new Long("8367003")));
	}

	@Test
	public void testDefaultAttributeGroupCardinalities() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { 260686004 = *}", 0, -1).getConceptIds();
		assertEquals(2,results.size());
		assertTrue(results.contains(new Long("128927009")));
		assertTrue(results.contains(new Long("8367003")));
	}

	@Test
	public void testConcreteValue() throws ServiceException {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: 3311487004 = #100", 0, -1).getConceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(new Long("404684003")));

		// search with #100.0 should returns the same result as above
		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: 3311487004 = #100.0", 0, -1).getConceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(new Long("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 >= #100", 0, -1).getConceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(new Long("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 > #100", 0, -1).getConceptIds();
		assertEquals(0, results.size());

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 > #20", 0, -1).getConceptIds();
		assertEquals(1, results.size());
		assertTrue(results.contains(new Long("404684003")));

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 != #100", 0, -1).getConceptIds();
		assertEquals(0, results.size());

		results = snomedQueryService.eclQueryReturnConceptIdentifiers("<<404684003: [0..1] 3311487004 != #10", 0, -1).getConceptIds();
		assertEquals(1, results.size());
	}

	@Test
	public void testQueryWithMinusOperator() throws Exception {
		List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004 = *").getItems();
		assertEquals(2, conceptResults.size());
		List<String> conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 = < 129264002").getItems();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 = (* MINUS < 129264002)").getItems();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
	}


	@Test
	public void testQueryWithNotEqualToOperator() throws Exception {
		List<ConceptResult> conceptResults = snomedQueryService.search("*:260686004 = *").getItems();
		assertEquals(2, conceptResults.size());
		List<String> conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
		assertTrue(conceptIds.contains("8367003"));

		conceptResults = snomedQueryService.search("*:260686004 = < 129264002").getItems();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("8367003"));
		conceptResults = snomedQueryService.search("*:260686004 != < 129264002").getItems();
		assertEquals(1, conceptResults.size());
		conceptIds = conceptResults.stream().map(ConceptResult::getId).collect(Collectors.toList());
		assertTrue(conceptIds.contains("128927009"));
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
