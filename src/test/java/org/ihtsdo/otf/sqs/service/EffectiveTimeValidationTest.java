package org.ihtsdo.otf.sqs.service;

import org.ihtsdo.otf.snomedboot.ReleaseImportException;
import org.ihtsdo.otf.snomedboot.domain.Concept;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStoreComponentFactoryImpl;
import org.ihtsdo.otf.sqs.domain.ConceptFieldNames;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Effective times are compared as strings, which is only equivalent to
 * comparing dates while they really are fixed-width {@code yyyyMMdd}. A value
 * that is not a calendar date in that form must abort the import.
 *
 * <p>That is stricter than the lenient date parsing it replaces, which read
 * {@code 2015-07-31} as 7 December 2014 and {@code 20230230} as 2 March 2023,
 * and indexed both silently. There is no findings channel in an index builder,
 * so the alternative to failing is an effective time invented here that
 * nothing downstream can tell apart from one the release actually carried.
 */
public class EffectiveTimeValidationTest {

	private static final String CONCEPT_ID = "138875005";

	@Test
	public void valueThatIsNotADateIsRejected() throws Exception {
		for (String effectiveTime : List.of("2015-07-31", "", "2015073", "201507310",
				"20230230", "20231399", "20230001", "20230100", "20230229", "19000229")) {
			try {
				buildDocumentFor(effectiveTime);
				fail("effectiveTime '" + effectiveTime + "' must not be indexed");
			} catch (IllegalArgumentException e) {
				assertTrue(e.getMessage(), e.getMessage().contains("'" + effectiveTime + "'"));
				assertTrue(e.getMessage(), e.getMessage().contains("not a yyyyMMdd date"));
			}
		}
	}

	@Test
	public void dateIsIndexedVerbatim() throws Exception {
		for (String effectiveTime : List.of("20150731", "20240229", "20000229", "20231231", "20230101")) {
			assertEquals(effectiveTime, buildDocumentFor(effectiveTime));
		}
	}

	/**
	 * Through the import itself: documents are built on a parallel stream, and
	 * the import declares ReleaseImportException, so that is what a rejected
	 * value must surface as.
	 */
	@Test
	public void importOfAConceptWithAnInvalidDateFailsWithReleaseImportException() throws Exception {
		Map<Long, ? extends Concept> concepts = conceptWith("20230230");
		try {
			new ReleaseImportManager().writeToIndex(concepts, new RamReleaseStore(), LoadingProfile.light);
			fail("the import must fail");
		} catch (ReleaseImportException e) {
			assertTrue(e.getMessage(), e.getMessage().contains("'20230230'"));
			assertTrue(e.getCause() instanceof IllegalArgumentException);
		}
	}

	/** @return the indexed effective time, or throws if the value is rejected. */
	private String buildDocumentFor(String effectiveTime) throws Exception {
		Concept concept = conceptWith(effectiveTime).get(Long.parseLong(CONCEPT_ID));
		try (ReleaseWriter releaseWriter = new ReleaseWriter(new RamReleaseStore())) {
			return releaseWriter.buildDocument(concept, false).get(ConceptFieldNames.EFFECTIVE_TIME);
		}
	}

	private static Map<Long, ? extends Concept> conceptWith(String effectiveTime) {
		ComponentStore componentStore = new ComponentStore();
		ComponentStoreComponentFactoryImpl factory = new ComponentStoreComponentFactoryImpl(componentStore);
		factory.newConceptState("", 1L, CONCEPT_ID, effectiveTime, "1", "900000000000207008", "900000000000074008");
		factory.addConceptFSN(CONCEPT_ID, "SNOMED CT Concept (SNOMED RT+CTV3)");
		return componentStore.getConcepts();
	}
}
