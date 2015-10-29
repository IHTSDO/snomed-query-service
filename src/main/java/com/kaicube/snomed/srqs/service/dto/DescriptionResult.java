package com.kaicube.snomed.srqs.service.dto;

public class DescriptionResult {

	private final String id;
	private final String term;

	public DescriptionResult(String id, String term) {
		this.id = id;
		this.term = term;
	}

	public String getId() {
		return id;
	}

	public String getTerm() {
		return term;
	}
}
