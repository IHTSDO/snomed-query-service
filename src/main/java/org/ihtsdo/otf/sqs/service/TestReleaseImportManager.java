package org.ihtsdo.otf.sqs.service;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.ihtsdo.otf.snomedboot.ReleaseImportException;
import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentFactoryImpl;
import org.ihtsdo.otf.snomedboot.factory.implementation.standard.ComponentStore;
import org.ihtsdo.otf.sqs.domain.ConceptConstants;
import org.ihtsdo.otf.sqs.service.store.DiskReleaseStore;
import org.ihtsdo.otf.sqs.service.store.RamReleaseStore;
import org.ihtsdo.otf.sqs.service.store.ReleaseStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestReleaseImportManager extends ReleaseImportManager {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	private final ComponentStore componentStore;
	private ComponentFactoryImpl componentFactory;

	private final boolean writeToRam;

	public TestReleaseImportManager(boolean writeToRam) {
		componentStore = new ComponentStore();
		componentFactory = new ComponentFactoryImpl(componentStore);
		this.writeToRam = writeToRam;
	}

	@Override
	public ReleaseStore loadReleaseFilesToDiskBasedIndex(File releaseDirectory, LoadingProfile loadingProfile, File indexDirectory) throws IOException, ReleaseImportException {
		return buildTestTaxonomy(loadingProfile);
	}

	public ReleaseStore buildTestTaxonomy(LoadingProfile loadingProfile) throws IOException, ReleaseImportException {
		logger.info("-- LOADING TEST DATA --");

		// Build a taxonomy, this is not the correct structure, it's purely for testing expression constraint queries

		final ConceptBuilder hasIntentAttribute = addConcept(363703001, "Has intent (attribute)");
		final ConceptBuilder intentsQualifierValue = addConcept(363675004, "Intents (nature of procedure values) (qualifier value)");
		final ConceptBuilder methodAttribute = addConcept(260686004, "Method (attribute)");

		final ConceptBuilder baseCountConcrete = addConcept(3311487004L, "Count of base and modification pair concrete (attribute)");
		final ConceptBuilder modelDataAttribute = addConcept(762706009, "Concept model data attribute (attribute)");
		modelDataAttribute.addChildren(baseCountConcrete);

		final ConceptBuilder cuttingAction = addConcept(360314001, "Cutting - action (qualifier value)");
		final ConceptBuilder actionQualifierValue = addConcept(129264002, "Action (qualifier value)")
				.addChildren(cuttingAction);
		final ConceptBuilder procedureSiteDirectAttribute = addConcept(405813007, "Procedure site - Direct (attribute)");
		final ConceptBuilder modelConcept = addConcept(ConceptConstants.MODEL_CONCEPT, "SNOMED CT Model Component (metadata)")
				.addChildren(hasIntentAttribute, intentsQualifierValue, methodAttribute, actionQualifierValue, cuttingAction, procedureSiteDirectAttribute);

		final ConceptBuilder nailStructure = addConcept(72651009, "Nail structure (body structure)");

		addConcept(ConceptConstants.ROOT_CONCEPT, "SNOMED CT Concept (SNOMED RT+CTV3)")
				.addChildren(
						modelConcept,
						addConcept(123037004, "Body structure (body structure)")
								.addChildren(
										addConcept(442083009, "Anatomical or acquired body structure (body structure)")
											.addChildren(nailStructure)
								),
						addConcept(404684003, "Clinical finding (finding)")
								.addConcreteAttribute(baseCountConcrete, "#100"),
						addConcept(71388002, "Procedure (procedure)")
								.addChildren(
										addConcept(362961001, "Procedure by intent (procedure)")
												.addAttribute(hasIntentAttribute, intentsQualifierValue),
										addConcept(128927009, "Procedure by method (procedure)")
												.addAttribute(methodAttribute, actionQualifierValue)
												.addAttribute(procedureSiteDirectAttribute, nailStructure)
												.addChildren(
														addConcept(8367003, "Clipping nails of patient (procedure)")
																.addAttribute(methodAttribute, cuttingAction)
																// added for range constraint out of range testing
																.addAttribute(methodAttribute, nailStructure)
												)
								)
				);
		return writeToIndex(componentStore.getConcepts(), writeToRam ? new RamReleaseStore() : new DiskReleaseStore(new File("index")), loadingProfile);
	}

	private ConceptBuilder addConcept(String id, String fsn) {
		return addConcept(new Long(id), fsn);
	}

	private ConceptBuilder addConcept(long id, String fsn) {
		final String conceptId = id + "";
		componentFactory.newConceptState(conceptId, "20150731", "1", "900000000000207008", "900000000000074008");
		componentFactory.addConceptFSN(conceptId, fsn);
		componentFactory.newDescriptionState(String.valueOf(new Random().nextLong()), "", "1", "", conceptId, "en", ConceptConstants.FSN, fsn, "");
		return new ConceptBuilder(conceptId);
	}

	class ConceptBuilder {

		private final String id;

		public ConceptBuilder(String id) {
			this.id = id;
		}

		public ConceptBuilder addChildren(ConceptBuilder... conceptBuilder) {
			for (ConceptBuilder builder : conceptBuilder) {
				componentFactory.addInferredConceptParent(builder.id, id);
				componentFactory.newRelationshipState("", "", "1", "", builder.id, id, "0", ConceptConstants.isA, "", "");
			}
			return this;
		}

		public ConceptBuilder addConcreteAttribute(ConceptBuilder type, String value) {
			componentFactory.addInferredConceptConcreteAttribute(id, type.id, value);
			componentFactory.newConcreteRelationshipState("", "20170731", "1", "", id, value, "1", type.id, "900000000000011006", "");
			return this;
		}

		public ConceptBuilder addAttribute(ConceptBuilder type, ConceptBuilder value) {
			componentFactory.addInferredConceptAttribute(id, type.id, value.id);
			componentFactory.newRelationshipState("", "20170731", "1", "", id, "", "1", type.id, "900000000000011006", "");
			return this;
		}
	}

}
