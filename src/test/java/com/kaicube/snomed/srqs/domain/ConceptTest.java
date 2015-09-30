package com.kaicube.snomed.srqs.domain;

import org.junit.Assert;
import org.junit.Test;

public class ConceptTest {

	@Test
	public void testIsConceptId() throws Exception {
		Assert.assertEquals(true, Concept.isConceptId("123004"));
		Assert.assertEquals(false, Concept.isConceptId("123014"));
		Assert.assertEquals(false, Concept.isConceptId("123024"));
		Assert.assertEquals(true, Concept.isConceptId("123104"));
		Assert.assertEquals(false, Concept.isConceptId("123114"));
		Assert.assertEquals(false, Concept.isConceptId("123124"));
	}
}
