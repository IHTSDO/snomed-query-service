package com.kaicube.snomed.srqs.service.dto;

public class RelationshipResult {

	final String id;
	final String effectiveTime;
	final String active;
	final String moduleId;
	final String sourceId;
	final String destinationId;
	final String relationshipGroup;
	final String typeId;
	final String characteristicTypeId;
	final String modifierId;

	public RelationshipResult(String id, String effectiveTime, String active, String moduleId, String sourceId, String destinationId, String relationshipGroup, String typeId, String characteristicTypeId, String modifierId) {
		this.id = id;
		this.effectiveTime = effectiveTime;
		this.active = active;
		this.moduleId = moduleId;
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.relationshipGroup = relationshipGroup;
		this.typeId = typeId;
		this.characteristicTypeId = characteristicTypeId;
		this.modifierId = modifierId;
	}

	public String getId() {
		return id;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public String getActive() {
		return active;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public String getRelationshipGroup() {
		return relationshipGroup;
	}

	public String getTypeId() {
		return typeId;
	}

	public String getCharacteristicTypeId() {
		return characteristicTypeId;
	}

	public String getModifierId() {
		return modifierId;
	}
}
