package com.kaicube.snomed.srqs.domain;

import java.util.HashSet;
import java.util.Set;

public class Concept {

	public static final String ID = "id";
	public static final String ACTIVE = "active";
	public static final String FSN = "fsn";
	public static final String ANCESTOR = "ancestors";

	private final Long id;
	private boolean active;
	private final Set<Concept> parents;
	private String fsn;

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

	public void setFsn(String fsn) {
		this.fsn = fsn;
	}

	public String getFsn() {
		return fsn;
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
}
