package com.kaicube.snomed.srqs.domain;

import com.kaicube.snomed.srqs.domain.rf2.RelationshipFields;

public class Relationship {

	public static final String ID = "rel_id";
	public static final String EFFECTIVE_TIME = "rel_effectiveTime";
	public static final String ACTIVE = "rel_active";
	public static final String MODULE_ID = "rel_moduleId";
	public static final String SOURCE_ID = "rel_sourceId";
	public static final String DESTINATION_ID = "rel_destinationId";
	public static final String RELATIONSHIP_GROUP = "rel_relationshipGroup";
	public static final String TYPE_ID = "rel_typeId";
	public static final String CHARACTERISTIC_TYPE_ID = "rel_characteristicTypeId";
	public static final String MODIFIER_ID = "rel_modifierId";

	private final String id;
	private final String effectiveTime;
	private final String active;
	private final String moduleId;
	private final String sourceId;
	private final String destinationId;
	private final String relationshipGroup;
	private final String typeId;
	private final String characteristicTypeId;
	private final String modifierId;

	public Relationship(String[] values) {
		id = values[RelationshipFields.id];
		effectiveTime = values[RelationshipFields.effectiveTime];
		active = values[RelationshipFields.active];
		moduleId = values[RelationshipFields.moduleId];
		sourceId = values[RelationshipFields.sourceId];
		destinationId = values[RelationshipFields.destinationId];
		relationshipGroup = values[RelationshipFields.relationshipGroup];
		typeId = values[RelationshipFields.typeId];
		characteristicTypeId = values[RelationshipFields.characteristicTypeId];
		modifierId = values[RelationshipFields.modifierId];
	}

	public Relationship(String sourceId, String destinationId) {
		this.destinationId = destinationId;
		this.sourceId = sourceId;

		id = "";
		effectiveTime = "";
		active = "";
		moduleId = "";
		relationshipGroup = "";
		typeId = "";
		characteristicTypeId = "";
		modifierId = "";
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
