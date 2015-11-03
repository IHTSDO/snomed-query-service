package com.kaicube.snomed.srqs.service.dto;

public class RefsetMembershipResult {

	private final String refsetId;
	private final String refsetFsn;

	public RefsetMembershipResult(String refsetId, String refsetFsn) {
		this.refsetId = refsetId;
		this.refsetFsn = refsetFsn;
	}

	public String getRefsetId() {
		return refsetId;
	}

	public String getRefsetFsn() {
		return refsetFsn;
	}
}
