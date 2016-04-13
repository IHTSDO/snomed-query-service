package org.ihtsdo.otf.sqs.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
public class ConceptResult {

	private String id;
	private String effectiveTime;
	private String active;
	private String moduleId;
	private String definitionStatusId;
	private String fsn;
	private List<RelationshipResult> relationships;
	private List<DescriptionResult> descriptions;
	private List<RefsetMembershipResult> refsetMemberships;

	public ConceptResult(String id) {
		this.id = id;
	}

	public ConceptResult(String id, String effectiveTime, String active, String moduleId, String definitionStatusId, String fsn, List<RefsetMembershipResult> memberOfRefsets) {
		this.id = id;
		this.effectiveTime = effectiveTime;
		this.active = active;
		this.moduleId = moduleId;
		this.definitionStatusId = definitionStatusId;
		this.fsn = fsn;
		this.refsetMemberships = memberOfRefsets;
	}

	public String getId() {
		return id;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public boolean isActive() {
		return active.equals("1");
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getDefinitionStatusId() {
		return definitionStatusId;
	}

	public String getFsn() {
		return fsn;
	}

	public List<RelationshipResult> getRelationships() {
		return relationships;
	}

	public void addRelationship(RelationshipResult relationshipResult) {
		if (relationships == null) relationships = new ArrayList<>();
		relationships.add(relationshipResult);
	}

	public List<DescriptionResult> getDescriptions() {
		return descriptions;
	}

	public void addDescription(DescriptionResult descriptionResult) {
		if (descriptions == null) descriptions = new ArrayList<>();
		descriptions.add(descriptionResult);
	}

	public List<RefsetMembershipResult> getRefsetMemberships() {
		return refsetMemberships;
	}

	public void addRefsetMembership(RefsetMembershipResult result) {
		if (refsetMemberships == null) refsetMemberships = new ArrayList<>();
		refsetMemberships.add(result);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ConceptResult that = (ConceptResult) o;

		return id.equals(that.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String toString() {
		return "ConceptResult{" +
				"id='" + id + '\'' +
				'}';
	}
}
