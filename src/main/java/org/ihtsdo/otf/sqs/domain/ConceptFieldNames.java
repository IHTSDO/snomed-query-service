package org.ihtsdo.otf.sqs.domain;

public interface ConceptFieldNames {

	String ID = "id";

	/** Discriminates document kinds. Only concept documents exist today. */
	String TYPE = "type";

	/** Value of {@link #TYPE} on a concept document. */
	String TYPE_CONCEPT = "concept";

	/** Numeric copy of {@link #ID}, so hits can be read without decompressing stored fields. */
	String ID_DOC_VALUES = "id-dv";
	String EFFECTIVE_TIME = "effectiveTime";
	String ACTIVE = "active";
	String MODULE_ID = "moduleId";
	String DEFINITION_STATUS_ID = "definitionStatusId";
	String FSN = "fsn";
	String FSN_LENGTH = "fsn-len";
	String ANCESTOR = "ancestor";
	String MEMBER_OF = "memberOf";
	String DESCRIPTION_IDS = "descriptionIds";
	String TOTAL_GROUPS = "_totalGrp";
	String CARDINALITY = "_card";
	String GROUP_CARDINALITY = "_grpCard";
}
