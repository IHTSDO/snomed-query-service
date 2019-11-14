package org.ihtsdo.otf.sqs.service.dto;

public class DescriptionResult {

	private final String id;
	private final String conceptId;
	private final String term;

	public DescriptionResult(String id, String conceptId, String term) {
		this.id = id;
		this.conceptId = conceptId;
		this.term = term;
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
