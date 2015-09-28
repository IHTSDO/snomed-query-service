package com.kaicube.snomed.srqs.service;

public class ELQuery {

	private boolean focusConceptWildcard;
	private String focusConceptId;
	private boolean descendantOf;
	private boolean ancestorOf;
	private boolean includeSelf = true;
	private String attributeName;

	public void setFocusConceptId(String focusConceptId) {
		this.focusConceptId = focusConceptId;
	}

	public String getFocusConceptId() {
		return focusConceptId;
	}

	public void descendantOf() {
		descendantOf = true;
		includeSelf = false;
	}

	public void descendantOrSelfOf() {
		descendantOf = true;
	}

	public void ancestorOf() {
		ancestorOf = true;
		includeSelf = false;
	}

	public void ancestorOrSelfOf() {
		ancestorOf = true;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public boolean isDescendantOf() {
		return descendantOf;
	}

	public boolean isAncestorOf() {
		return ancestorOf;
	}

	public boolean isIncludeSelf() {
		return includeSelf;
	}

	public void setFocusConceptWildcard() {
		focusConceptWildcard = true;
	}

	public boolean isFocusConceptWildcard() {
		return focusConceptWildcard;
	}
}
