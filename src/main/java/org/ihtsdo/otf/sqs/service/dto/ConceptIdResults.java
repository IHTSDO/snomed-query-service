package org.ihtsdo.otf.sqs.service.dto;

import java.util.List;

public class ConceptIdResults {

	private final List<Long> conceptIds;
	private final int offset;
	private final int total;
	private final int limit;

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
