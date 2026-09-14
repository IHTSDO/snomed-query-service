package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStoreComponentFactoryImpl;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Effective times are compared as strings, which is only equivalent to
 * comparing dates while they really are fixed-width {@code yyyyMMdd}. An
 * unparseable value must abort the import.
 *
 * <p>That is stricter than the lenient date parsing it replaces, which would
 * have read {@code 2015-07-31} as 7 December 2014 and indexed it silently.
 * There is no findings channel in an index builder, so the alternative to
 * failing is an effective time invented here that nothing downstream can tell
 * apart from one the release actually carried.
 */
public class EffectiveTimeValidationTest {

	@Test
	public void malformedConceptEffectiveTimeAbortsTheBuild() throws Exception {
		try {
			buildDocumentFor("2015-07-31");
			fail("a non-yyyyMMdd effectiveTime must not be indexed");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("2015-07-31"));
			assertTrue(e.getMessage(), e.getMessage().contains("not yyyyMMdd"));
		}
	}

	@Test
	public void blankConceptEffectiveTimeAbortsTheBuild() throws Exception {
		try {
			buildDocumentFor("");
			fail("a blank effectiveTime must not be indexed");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("not yyyyMMdd"));
		}
	}

	@Test
	public void wellFormedEffectiveTimeIsIndexedVerbatim() throws Exception {
		assertEquals("20150731", buildDocumentFor("20150731"));
	}

	/** @return the indexed effective time, or throws if the value is rejected. */
	private String buildDocumentFor(String effectiveTime) throws Exception {
		ComponentStore componentStore = new ComponentStore();
		ComponentStoreComponentFactoryImpl factory = new ComponentStoreComponentFactoryImpl(componentStore);
		factory.newConceptState("", 1L, "138875005", effectiveTime, "1", "900000000000207008", "900000000000074008");
		factory.addConceptFSN("138875005", "SNOMED CT Concept (SNOMED RT+CTV3)");
		Concept concept = componentStore.getConcepts().get(138875005L);

		try (ReleaseWriter releaseWriter = new ReleaseWriter(new RamReleaseStore())) {
			return releaseWriter.buildDocument(concept, false)
					.get(org.ihtsdo.otf.sqs.domain.ConceptFieldNames.EFFECTIVE_TIME);
		}
	}
}
