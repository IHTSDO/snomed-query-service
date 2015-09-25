package com.kaicube.snomed.srqs.domain;

import java.util.HashSet;
import java.util.Set;

public class Concept {

	public static final String ID = "id";
	public static final String ACTIVE = "active";
	public static final String ANCESTOR = "ancestors";

	private final Long id;
	private boolean active;
	private final Set<Concept> parents;

	public Concept(Long id) {
		this.id = id;
		parents = new HashSet<>();
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public Set<Long> getAncestorIds() {
		return collectParentIds(this, new HashSet<Long>());
	}

	private Set<Long> collectParentIds(Concept concept, Set<Long> ancestors) {
		for (Concept parent : concept.parents) {
			ancestors.add(parent.getId());
			collectParentIds(parent, ancestors);
		}
		return ancestors;
	}
}
