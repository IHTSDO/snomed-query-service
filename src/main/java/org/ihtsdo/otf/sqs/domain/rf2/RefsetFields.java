package org.ihtsdo.otf.sqs.domain.rf2;

public interface RefsetFields extends ComponentFields {

	// id	effectiveTime	active	moduleId	refsetId	referencedComponentId
	// 0	1				2		3			4			5

	int refsetId = 4;
	int referencedComponentId = 5;

}
