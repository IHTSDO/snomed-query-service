package org.ihtsdo.otf.sqs.service.dto;

public record RelationshipResult(String id, String effectiveTime, String active, String moduleId, String sourceId,
								 String destinationId, String relationshipGroup, String typeId,
								 String characteristicTypeId, String modifierId) {


}
