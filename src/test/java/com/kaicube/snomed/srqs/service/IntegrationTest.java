package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.exceptions.NotFoundException;
import com.kaicube.snomed.srqs.service.dto.ConceptResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class IntegrationTest {

	private ReleaseReader releaseReader;

	@Before
	public void setup() throws IOException {
		final ReleaseStore releaseStore = new TestReleaseImporter().buildTestTaxonomy();
		releaseReader = new ReleaseReader(releaseStore);
	}

	@Test(expected = NotFoundException.class)
	public void testRetrieveConceptNotFound() throws Exception {
		releaseReader.retrieveConcept("123");
	}

	@Test
	public void testRetrieveConcept() throws Exception {
		Assert.assertEquals("Body structure (body structure)", releaseReader.retrieveConcept("123037004").getFsn());
	}

	@Test
	public void testExpressionConstraintQuery() throws Exception {
		final List<ConceptResult> conceptResults = releaseReader.expressionConstraintQuery("*");
		Assert.assertEquals(10, conceptResults.size());
	}

	@Test
	public void testRetrieveConceptAncestors() throws Exception {
		final Set<ConceptResult> ancestors = releaseReader.retrieveConceptAncestors("128927009");
		Assert.assertEquals(2, ancestors.size());
		Assert.assertTrue(ancestors.contains(new ConceptResult("138875005", null)));
		Assert.assertTrue(ancestors.contains(new ConceptResult("71388002", null)));
	}

	@Test
	public void testRetrieveConceptDescendants() throws Exception {
		final Set<ConceptResult> descendants = releaseReader.retrieveConceptDescendants("71388002");
		Assert.assertEquals(3, descendants.size());
		Assert.assertTrue(descendants.contains(new ConceptResult("362961001", null)));
		Assert.assertTrue(descendants.contains(new ConceptResult("128927009", null)));
		Assert.assertTrue(descendants.contains(new ConceptResult("8367003", null)));
	}
}
