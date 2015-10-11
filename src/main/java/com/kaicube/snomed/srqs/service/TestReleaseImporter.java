package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;
import com.kaicube.snomed.srqs.domain.ConceptConstants;
import com.kaicube.snomed.srqs.domain.Relationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TestReleaseImporter extends ReleaseImporter {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ReleaseStore loadReleaseZip(String releaseDirPath) throws IOException {
		return buildTestTaxonomy();
	}

	public ReleaseStore buildTestTaxonomy() throws IOException {
		logger.info("-- LOADING TEST DATA --");

		// Build a taxonomy, this is not the correct structure, it's purely for testing expression constraint queries

		final ConceptBuilder hasIntentAttribute = addConcept(363703001, "Has intent (attribute)");
		final ConceptBuilder intentsQualifierValue = addConcept(363675004, "Intents (nature of procedure values) (qualifier value)");
		final ConceptBuilder methodAttribute = addConcept(260686004, "Method (attribute)");
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
						addConcept(404684003, "Clinical finding (finding)"),
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
												)
								)
				);
		return writeToIndex();
	}

	private ConceptBuilder addConcept(String id, String fsn) {
		return addConcept(new Long(id), fsn);
	}

	private ConceptBuilder addConcept(long id, String fsn) {
		final Concept concept = getConcept(id, fsn);
		getConcepts().put(id, concept);
		return new ConceptBuilder(concept);
	}

	private Concept getConcept(long id, String fsn) {
		final Concept concept = new Concept(id);
		concept.setActive(true);
		concept.setFsn(fsn);
		return concept;
	}

	class ConceptBuilder {

		private final Concept concept;

		public ConceptBuilder(Concept concept) {
			this.concept = concept;
		}

		public ConceptBuilder addChildren(ConceptBuilder... conceptBuilder) {
			for (ConceptBuilder builder : conceptBuilder) {
				builder.concept.addParent(concept);
				builder.concept.addRelationship(new Relationship(builder.concept.getId().toString(), concept.getId().toString()));
			}
			return this;
		}

		public ConceptBuilder addAttribute(String type, String value) {
			this.concept.addAttribute(type, value);
			return this;
		}

		public ConceptBuilder addAttribute(ConceptBuilder type, ConceptBuilder value) {
			return addAttribute(type.concept.getId().toString(), value.concept.getId().toString());
		}
	}

}
