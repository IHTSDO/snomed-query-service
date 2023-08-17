package org.ihtsdo.otf.sqs.service.dto;

public record RefsetMembershipResult(String refsetId, String refsetFsn) {


	@Override
	public String toString() {
		return "RefsetMembershipResult{" +
				"refsetId='" + refsetId + '\'' +
				", refsetFsn='" + refsetFsn + '\'' +
				'}';
	}
}
