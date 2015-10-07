package com.kaicube.snomed.srqs.service;

import com.kaicube.snomed.srqs.domain.Concept;

import java.io.IOException;

public class TestReleaseImporter extends ReleaseImporter {

	public ReleaseStore buildTestTaxonomy() throws IOException {

		// Build a taxonomy, purely for testing expression constraint queries
		
		final ConceptBuilder hasIntentAttribute = addConcept(363703001, "Has intent (attribute)");
		final ConceptBuilder intentsQualifierValue = addConcept(363703001, "Intents (nature of procedure values) (qualifier value)");
		final ConceptBuilder methodAttribute = addConcept(260686004, "Method (attribute)");
		final ConceptBuilder actionQualifierValue = addConcept(129264002, "Action (qualifier value)");
		final ConceptBuilder cuttingAction = addConcept(360314001, "Cutting - action (qualifier value)");

		addConcept(138875005, "SNOMED CT Concept (SNOMED RT+CTV3)")
				.addChildren(
						hasIntentAttribute,
						intentsQualifierValue,
						methodAttribute,
						actionQualifierValue.addChildren(cuttingAction),
						addConcept(123037004, "Body structure (body structure)"),
						addConcept(404684003, "Clinical finding (finding)"),
						addConcept(71388002, "Procedure (procedure)")
								.addChildren(
										addConcept(362961001, "Procedure by intent (procedure)")
												.addAttribute(hasIntentAttribute, intentsQualifierValue),
										addConcept(128927009, "Procedure by method (procedure)")
												.addAttribute(methodAttribute, actionQualifierValue)
												.addChildren(
														addConcept(8367003, "Clipping nails of patient (procedure)")
																.addAttribute(methodAttribute, cuttingAction)
												)
								)
				);
		return writeToIndex();
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
