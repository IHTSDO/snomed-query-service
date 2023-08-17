package org.ihtsdo.otf.sqs.service.dto;

import java.util.List;

public class ConceptResults {

	private final List<ConceptResult> items;
	private final int offset;
	private final int total;
	private final int limit;

	public ConceptResults(List<ConceptResult> items, int offset, int total, int limit) {
		this.items = items;
		this.offset = offset;
		this.total = total;
		this.limit = limit;
	}

	public List<ConceptResult> getItems() {
		return items;
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
