package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import com.kaicube.snomed.srqs.service.dto.ConceptResults;
import com.kaicube.snomed.srqs.service.exception.NotFoundException;
import com.kaicube.snomed.srqs.service.exception.ServiceException;
import com.kaicube.snomed.srqs.service.store.ReleaseStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntegrationTest {

	private ReleaseReader releaseReader;

	@Before
	public void setup() throws IOException {
		final ReleaseStore releaseStore = new TestReleaseImporter().buildTestTaxonomy();
		releaseReader = new ReleaseReader(releaseStore);
	}

	@Test
	public void testWordSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = releaseReader.search("action", 0, 10).getItems();
		Assert.assertEquals(2, conceptResults.size());
		Assert.assertTrue(conceptResults.contains(releaseReader.retrieveConcept("360314001")));
		Assert.assertTrue(conceptResults.contains(releaseReader.retrieveConcept("129264002")));
	}

	@Test
	public void testWordWildcardSearch() throws ServiceException {
		final List<ConceptResult> conceptResults = releaseReader.search("val*", 0, 10).getItems();
		Assert.assertEquals(3, conceptResults.size());
		Assert.assertTrue(conceptResults.contains(releaseReader.retrieveConcept("363675004")));
		Assert.assertTrue(conceptResults.contains(releaseReader.retrieveConcept("360314001")));
		Assert.assertTrue(conceptResults.contains(releaseReader.retrieveConcept("129264002")));
	}

	@Test
	public void testExpressionConstraintQuery_wildcardFocusConcept() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*").getItems();
		Assert.assertEquals(16, conceptResults.size());
	}

	@Test
	public void testExpressionConstraintQuery_pagination() throws Exception {
		final List<ConceptResult> fullItems = releaseReader.expressionConstraintQuery("*").getItems();

		ConceptResults results = releaseReader.expressionConstraintQuery("*", 0, 5);
		Assert.assertEquals(0, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(5, results.getLimit());
		Assert.assertEquals(5, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(0, 5)));

		results = releaseReader.expressionConstraintQuery("*", 0, 10);
		Assert.assertEquals(0, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(10, results.getLimit());
		Assert.assertEquals(10, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(0, 10)));

		results = releaseReader.expressionConstraintQuery("*", 10, 10);
		Assert.assertEquals(10, results.getOffset());
		Assert.assertEquals(16, results.getTotal());
		Assert.assertEquals(10, results.getLimit());
		Assert.assertEquals(6, results.getItems().size());
		Assert.assertTrue(results.getItems().containsAll(fullItems.subList(10, 16)));
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionOnlyRootOverlap() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery(">442083009 AND >8367003").getItems();
		assertResultSet(conceptResults, 138875005);
	}

	@Test
	public void testExpressionConstraintQuery_ancestorOfConjunctionSomeOverlap() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery(">>128927009 AND >8367003").getItems();
		assertResultSet(conceptResults, 138875005, 71388002, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfConjunction() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("<71388002 AND <128927009").getItems();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfDisjunction() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("<123037004 OR <71388002").getItems();
		assertResultSet(conceptResults, 442083009, 362961001, 128927009, 8367003, 72651009);
	}

	@Test
	public void testExpressionConstraintQuery_descendantOfExclusion() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("<71388002 MINUS <<362961001").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeName() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=*").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValue() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=129264002").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=<<129264002").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueDescendantOf() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=<129264002").getItems();
		assertResultSet(conceptResults, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOf() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=>360314001").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_refinement_attributeValueAncestorOrSelfOf() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=>>360314001").getItems();
		assertResultSet(conceptResults, 8367003, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_conjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=* AND 405813007=*").getItems();
		assertResultSet(conceptResults, 128927009);
	}

	@Test
	public void testExpressionConstraintQuery_disjunctionAttributeSet() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*:260686004=* OR 405813007=*").getItems();
		assertResultSet(conceptResults, 128927009, 8367003);
	}

	@Test
	public void testExpressionConstraintQuery() throws Exception {
		releaseReader.expressionConstraintQuery("< 404684003 |clinical finding|: 116676008 |associated morphology| = ((<< 56208002 |ulcer| AND << 50960005 |hemorrhage|) MINUS << 26036001 |obstruction|)");
	}

	@Test(expected = NotFoundException.class)
	public void testRetrieveConceptNotFound() throws Exception {
		releaseReader.retrieveConcept("121111004");
	}

	@Test
	public void testRetrieveConcept() throws Exception {
		final ConceptResult conceptResult = releaseReader.retrieveConcept("123037004");
		Assert.assertEquals("Body structure (body structure)", conceptResult.getFsn());
		Assert.assertEquals("20150731", conceptResult.getEffectiveTime());
		Assert.assertEquals(true, conceptResult.isActive());
		Assert.assertEquals("900000000000207008", conceptResult.getModuleId());
		Assert.assertEquals("900000000000074008", conceptResult.getDefinitionStatusId());
		Assert.assertEquals(1, conceptResult.getDescriptions().size());
	}

	@Test
	public void testRetrieveConceptAncestors() throws Exception {
		final List<ConceptResult> ancestors = releaseReader.retrieveConceptAncestors("128927009").getItems();
		Assert.assertEquals(2, ancestors.size());
		Assert.assertTrue(ancestors.contains(new ConceptResult("138875005")));
		Assert.assertTrue(ancestors.contains(new ConceptResult("71388002")));
	}

	@Test
	public void testRetrieveConceptDescendants() throws Exception {
		final List<ConceptResult> descendants = releaseReader.retrieveConceptDescendants("71388002").getItems();
		Assert.assertEquals(3, descendants.size());
		Assert.assertTrue(descendants.contains(new ConceptResult("362961001")));
		Assert.assertTrue(descendants.contains(new ConceptResult("128927009")));
		Assert.assertTrue(descendants.contains(new ConceptResult("8367003")));
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
