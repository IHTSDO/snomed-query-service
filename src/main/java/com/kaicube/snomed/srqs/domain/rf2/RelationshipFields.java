package com.kaicube.snomed.srqs.domain.rf2;

public interface RelationshipFields extends ComponentFields {

	// id	effectiveTime	active	moduleId	sourceId	destinationId	relationshipGroup	typeId	characteristicTypeId	modifierId
	// 0	1				2		3			4			5				6					7		8						9

	int sourceId = 4;
	int destinationId = 5;
	int relationshipGroup = 6;
	int typeId = 7;
	int characteristicTypeId = 8;
	int modifierId = 9;

}
