package com.kaicube.snomed.srqs.service.dto;

public class ConceptResult {

	private String id;
	private String fsn;

	public ConceptResult(String id, String fsn) {
		this.id = id;
		this.fsn = fsn;
	}

	public String getId() {
		return id;
	}

	public String getFsn() {
		return fsn;
	}
}
