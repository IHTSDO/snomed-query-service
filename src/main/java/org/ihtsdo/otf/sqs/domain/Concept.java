package org.ihtsdo.otf.sqs.domain;

import org.ihtsdo.otf.sqs.domain.rf2.ConceptFields;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Concept {

	public static final String ID = "id";
	public static final String EFFECTIVE_TIME = "effectiveTime";
	public static final String ACTIVE = "active";
	public static final String MODULE_ID = "moduleId";
	public static final String DEFINITION_STATUS_ID = "definitionStatusId";
	public static final String FSN = "fsn";
	public static final String ANCESTOR = "ancestor";
	public static final String MEMBER_OF = "memberOf";

	private final Long id;
	private String effectiveTime;
	private boolean active;
	private String moduleId;
	private String definitionStatusId;
	private String fsn;
	private final MultiValueMap<String, String> attributes;
	private final Set<Concept> parents;
	private final Set<Long> memberOfRefsetIds;
	private final List<Relationship> relationships;
	private final List<Description> descriptions;

	public Concept(Long id) {
		this.id = id;
		attributes = new LinkedMultiValueMap<>();
		parents = new HashSet<>();
		memberOfRefsetIds = new HashSet<>();
		relationships = new ArrayList<>();
		descriptions = new ArrayList<>();
	}

	public void update(String[] values) {
		effectiveTime = values[ConceptFields.effectiveTime];
		active = "1".equals(values[ConceptFields.active]);
		moduleId = values[ConceptFields.moduleId];
		definitionStatusId = values[ConceptFields.definitionStatusId];
	}

	public void addMemberOfRefsetId(Long refsetId) {
		memberOfRefsetIds.add(refsetId);
	}

	public Set<Long> getMemberOfRefsetIds() {
		return memberOfRefsetIds;
	}

	public static boolean isConceptId(String componentId) {
		if (componentId != null) {
			final int length = componentId.length();
			return length > 3 && componentId.substring(length - 2, length - 1).equals("0");
		}
		return false;
	}

	/**
	 * @return A set of all ancestors
	 * @throws IllegalStateException if an active relationship is found pointing to an inactive parent concept.
	 */
	public Set<Long> getAncestorIds() throws IllegalStateException {
		return collectParentIds(this, new HashSet<Long>());
	}

	private Set<Long> collectParentIds(Concept concept, Set<Long> ancestors) throws IllegalStateException{
		for (Concept parent : concept.parents) {
			if (!parent.isActive()) {
				throw new IllegalStateException("Active isA relationship from active concept " + concept.id + " to inactive concept " + parent.id);
			}
			ancestors.add(parent.getId());
			collectParentIds(parent, ancestors);
		}
		return ancestors;
	}

	public boolean isActive() {
		return active;
	}

	public void addParent(Concept parentConcept) {
		parents.add(parentConcept);
	}

	public Long getId() {
		return id;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public String getModuleId() {
		return moduleId;
	}

	public String getDefinitionStatusId() {
		return definitionStatusId;
	}

	public void setFsn(String fsn) {
		this.fsn = fsn;
	}

	public String getFsn() {
		return fsn;
	}

	public MultiValueMap<String, String> getAttributes() {
		return attributes;
	}

	public void addAttribute(String type, String value) {
		attributes.add(type, value);
	}

	public void addRelationship(Relationship relationship) {
		relationships.add(relationship);
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public void addDescription(Description description) {
		descriptions.add(description);
	}

	public List<Description> getDescriptions() {
		return descriptions;
	}
}
