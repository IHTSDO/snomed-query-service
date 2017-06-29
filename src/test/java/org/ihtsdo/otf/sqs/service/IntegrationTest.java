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

import java.io.IOException;
import java.security.cert.CollectionCertStoreParameters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		snomedQueryService.eclQueryReturnConceptIdentifiers("* MINUS (<<91723000  OR <<723264001) :  272741003=*", 0, -1);
	}

	@Test
	public void testWordSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "action", 0, 10).getItems();
		Assert.assertEquals(2, conceptResults.size());
		Assert.assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		Assert.assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testWordWildcardSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = snomedQueryService.search(null, "val*", 0, 10).getItems();
		Assert.assertEquals(3, conceptResults.size());
		Assert.assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("363675004")));
		Assert.assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("360314001")));
		Assert.assertTrue(conceptResults.contains(snomedQueryService.retrieveConcept("129264002")));
	}

	@Test
	public void testExpressionConstraintQuery_wildcardFocusConcept() throws Exception {
		final List<ConceptResult> conceptResults = snomedQueryService.search("*").getItems();
		Assert.assertEquals(16, conceptResults.size());
	}

	@Test
	public void testExpressionConstraintQuery_pagination() throws Exception {
		final List<ConceptResult> fullItems = snomedQueryService.search("*").getItems();

		ConceptResults results = snomedQueryService.search("*", null, 0, 5);
		Assert.assertEquals(0, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(5, results.getLimit());
		Assert.assertEquals(5, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(0, 5)));

		results = snomedQueryService.search("*", null, 0, 10);
		Assert.assertEquals(0, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(10, results.getLimit());
		Assert.assertEquals(10, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(0, 10)));

		results = snomedQueryService.search("*", null, 10, 10);
		Assert.assertEquals(10, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(10, results.getLimit());
		Assert.assertEquals(6, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(10, 16)));
	}

	@Test
	public void testECLReturnId() throws ServiceException {
		ConceptIdResults idResults = snomedQueryService.eclQueryReturnConceptIdentifiers("442083009", 0, 100);
		Assert.assertEquals(0, idResults.getOffset());
		Assert.assertEquals(100, idResults.getLimit());
		Assert.assertEquals(1, idResults.getTotal());
		List<Long> conceptIds = idResults.getConceptIds();
		Assert.assertEquals(1, conceptIds.size());
		Assert.assertEquals(new Long(442083009L), conceptIds.get(0));
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
		snomedQueryService.search("< 404684003 |clinical finding|: 116676008 |associated morphology| = ((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)");
	}

	@Test(expected = NotFoundException.class)
	public void testRetrieveConceptNotFound() throws Exception {
		snomedQueryService.retrieveConcept("121111004");
	}

	@Test
	public void testRetrieveConcept() throws Exception {
		final ConceptResult conceptResult = snomedQueryService.retrieveConcept("123037004");
		Assert.assertEquals("Body structure (body structure)", conceptResult.getFsn());
		Assert.assertEquals("20150731", conceptResult.getEffectiveTime());
		Assert.assertEquals(true, conceptResult.isActive());
		Assert.assertEquals("900000000000207008", conceptResult.getModuleId());
		Assert.assertEquals("900000000000074008", conceptResult.getDefinitionStatusId());
	}

	@Test
	public void testRetrieveConceptAncestors() throws Exception {
		final List<ConceptResult> ancestors = snomedQueryService.retrieveConceptAncestors("128927009").getItems();
		Assert.assertEquals(2, ancestors.size());
		Assert.assertTrue(ancestors.contains(new ConceptResult("138875005")));
		Assert.assertTrue(ancestors.contains(new ConceptResult("71388002")));
	}

	@Test
	public void testRetrieveConceptDescendants() throws Exception {
		final List<ConceptResult> descendants = snomedQueryService.retrieveConceptDescendants("71388002").getItems();
		Assert.assertEquals(3, descendants.size());
		Assert.assertTrue(descendants.contains(new ConceptResult("362961001")));
		Assert.assertTrue(descendants.contains(new ConceptResult("128927009")));
		Assert.assertTrue(descendants.contains(new ConceptResult("8367003")));
	}

	@Test
	public void testEclQueryWhenConceptDescendantIsEmpty() throws Exception {
		final ConceptResults result = snomedQueryService.search("<< 105590001 |Substance (substance)|: 726542003 = < 726711005 |Disposition (disposition)|");
		Assert.assertEquals(0,result.getItems().size());
	}
	
	@Test
	public void testAttribute() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:260686004 = *", 0, -1).getConceptIds();
		Assert.assertEquals(2,results.size());
		Assert.assertTrue(results.contains(new Long(128927009)));
		Assert.assertTrue(results.contains(new Long(8367003)));
		
	}
	
	
	@Test
	public void testAttributeWithCardinality() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("<  373873005 |Pharmaceutical / biologic product| : [1..3]  127489000 |Has active ingredient|  = <  105590001 |Substance|", 0 ,-1);
		Assert.assertEquals(0,result.getConceptIds().size());
	}
	
	@Test
	public void testAttributeCardinalityWithSpecificDomain() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[2..*] <<260686004 = *", 0, -1).getConceptIds();
		Assert.assertEquals(1,results.size());
		Assert.assertTrue(results.contains(new Long("128927009")));
	}
	
	@Test
	public void testAttributeCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*:[1..2] <<260686004 = *", 0, -1).getConceptIds();
		Assert.assertEquals(2,results.size());
		Assert.assertTrue(results.contains(new Long("128927009")));
		Assert.assertTrue(results.contains(new Long("8367003")));
	}
	
	@Test
	public void testAttributeCardinalityWithoutMatch() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("128927009:[0..1] 260686004 = *", 0, -1).getConceptIds();
		Assert.assertEquals(0,results.size());
	}
	
	
	@Test
	public void testAttributeGroupCardinality() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [0..1] { [0..1] 260686004 = *}", 0, -1).getConceptIds();
		Assert.assertEquals(1,results.size());
		Assert.assertTrue(results.contains(new Long("8367003")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithMatchedResults() throws Exception {
		final List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..*] { [1..*] 260686004 = *}", 0, -1).getConceptIds();
		Assert.assertEquals(2,results.size());
		Assert.assertTrue(results.contains(new Long("128927009")));
		Assert.assertTrue(results.contains(new Long("8367003")));
	}
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatch() throws Exception {
		final ConceptIdResults result = snomedQueryService.eclQueryReturnConceptIdentifiers("*: [1..3] { [3..*] 260686004 = *}", 0, -1);
		Assert.assertEquals(0,result.getConceptIds().size());
	}
	
	
	@Test
	public void testAttributeGroupCardinalityWithoutMatchWithDefaultGroupCardinality() throws Exception {
		final ConceptResults result = snomedQueryService.search("*: [2..*] { 260686004 = *}");
		Assert.assertEquals(0,result.getItems().size());
	}
	
	@Test
	public void testDefaultAttributeGroupCardinality() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { [1..*] 260686004 = *}", 0, -1).getConceptIds();
		Assert.assertEquals(2,results.size());
		Assert.assertTrue(results.contains(new Long("128927009")));
		Assert.assertTrue(results.contains(new Long("8367003")));
	}

	@Test
	public void testDefaultAttributeGroupCardinalities() throws Exception {
		List<Long> results = snomedQueryService.eclQueryReturnConceptIdentifiers("*: { 260686004 = *}", 0, -1).getConceptIds();
		Assert.assertEquals(2,results.size());
		Assert.assertTrue(results.contains(new Long("128927009")));
		Assert.assertTrue(results.contains(new Long("8367003")));
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
