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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ConceptResult that = (ConceptResult) o;

		return id.equals(that.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ConceptResult{" +
				"id='" + id + '\'' +
				'}';
	}
}
