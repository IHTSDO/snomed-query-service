package org.ihtsdo.otf.sqs.service.dto;

import java.util.List;

public class ConceptIdResults {

	private List<Long> conceptIds;
	private int offset;
	private int total;
	private int limit;

	public ConceptIdResults(List<Long> conceptIds, int offset, int total, int limit) {
		this.conceptIds = conceptIds;
		this.offset = offset;
		this.total = total;
		this.limit = limit;
	}

	public List<Long> getConceptIds() {
		return conceptIds;
	}

	public int getOffset() {
		return offset;
	}

	public int getTotal() {
		return total;
	}

	public int getLimit() {
		return limit;
	}
}
