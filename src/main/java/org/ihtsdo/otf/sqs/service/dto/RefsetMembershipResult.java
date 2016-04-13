package org.ihtsdo.otf.sqs.service.dto;

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

	@Override
	public String toString() {
		return "RefsetMembershipResult{" +
				"refsetId='" + refsetId + '\'' +
				", refsetFsn='" + refsetFsn + '\'' +
				'}';
	}
}
