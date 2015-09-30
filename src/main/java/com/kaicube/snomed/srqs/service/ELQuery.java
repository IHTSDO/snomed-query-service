package com.kaicube.snomed.srqs.service;

public class ELQuery {

	private boolean focusConceptWildcard;
	private String focusConceptId;
	private String memberOfRefsetId;
	private boolean descendantOf;
	private boolean ancestorOf;
	private boolean includeSelf = true;
	private String attributeName;
	private ExpressionComparisonOperator attributeOperator;
	private String attributeValue;

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

	public void setAttributeOperator(ExpressionComparisonOperator attributeOperator) {
		this.attributeOperator = attributeOperator;
	}

	public ExpressionComparisonOperator getAttributeOperator() {
		return attributeOperator;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setMemberOfRefsetId(String memberOfRefsetId) {
		this.memberOfRefsetId = memberOfRefsetId;
	}

	public String getMemberOfRefsetId() {
		return memberOfRefsetId;
	}

	enum ExpressionComparisonOperator {
		equals, notEquals
	}
}
