package com.kaicube.snomed.srqs.domain;

import com.kaicube.snomed.srqs.domain.rf2.DescriptionFields;

public class Description {

	public static final String ID = "desc_id";
	public static final String TERM = "desc_term";
	public static final String CONCEPT_ID = "desc_conceptId";

	private final String id;
	private final String term;
	private final String conceptId;

	public Description(String[] values) {
		id = values[DescriptionFields.id];
		term = values[DescriptionFields.term];
		conceptId = values[DescriptionFields.conceptId];
	}

	public Description(String term, String conceptId) {
		this.id = "";
		this.term = term;
		this.conceptId = conceptId;
	}

	public String getId() {
		return id;
	}

	public String getTerm() {
		return term;
	}

	public String getConceptId() {
		return conceptId;
	}
}
