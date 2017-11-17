package org.ihtsdo.otf.sqs;

import java.util.HashSet;
import java.util.Set;

import org.ihtsdo.otf.snomedboot.factory.LoadingProfile;

public class LoadingProfileConfig {
	
	private LoadingProfile loadingProfile = new LoadingProfile();
	private LoadingProfileConfig lightProfile;
	private LoadingProfileConfig completeProfile;
	
	private boolean inferredAttributeMapOnConcept;
	private boolean statedAttributeMapOnConcept;
	private boolean statedRelationships;
	private boolean descriptions;
	private boolean fullDescriptionObjects;
	private boolean fullRelationshipObjects;
	private boolean inactiveConcepts;
	private boolean inactiveDescriptions;
	private boolean inactiveRelationships;
	private boolean inactiveRefsetMembers;
	private boolean allRefsets;
	private boolean fullRefsetMemberObjects;
	private boolean justRefsets;
	private Set<String> refsetIds = new HashSet<>();
	private Set<String> includedReferenceSetFilenamePatterns = new HashSet<>();
	
	private boolean useDefaultLight;
	private boolean useDefaultComplete;

	public boolean getUseDefaultLight() {
		return useDefaultLight;
	}

	public void setUseDefaultLight(boolean useDefaultLight) {
		this.useDefaultLight = useDefaultLight;
	}

	public boolean getUseDefaultComplete() {
		return useDefaultComplete;
	}

	public void setUseDefaultComplete(boolean useDefaultComplete) {
		this.useDefaultComplete = useDefaultComplete;
	}

	public LoadingProfileConfig getLightProfile() {
		return lightProfile;
	}

	public void setLightProfile(LoadingProfileConfig lightProfile) {
		this.lightProfile = lightProfile;
	}

	public LoadingProfileConfig getCompleteProfile() {
		return completeProfile;
	}

	public void setCompleteProfile(LoadingProfileConfig completeProfile) {
		this.completeProfile = completeProfile;
	}

	public LoadingProfileConfig(){
		this.loadingProfile = new LoadingProfile(); 
	}
	
	public LoadingProfileConfig(LoadingProfile loadingProfile){
		this.loadingProfile = loadingProfile;
	}
	
	public boolean getInferredAttributeMapOnConcept() {
		return inferredAttributeMapOnConcept;
	}
	public void setInferredAttributeMapOnConcept(boolean inferredAttributeMapOnConcept) {
		this.inferredAttributeMapOnConcept = inferredAttributeMapOnConcept;
	}
	public boolean getStatedAttributeMapOnConcept() {
		return statedAttributeMapOnConcept;
	}
	public void setStatedAttributeMapOnConcept(boolean statedAttributeMapOnConcept) {
		this.statedAttributeMapOnConcept = statedAttributeMapOnConcept;
	}
	
	public boolean getStatedRelationships() {
		return statedRelationships;
	}
	public void setStatedRelationships(boolean statedRelationships) {
		this.statedRelationships = statedRelationships;
	}
	public boolean getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(boolean descriptions) {
		this.descriptions = descriptions;
	}
	public boolean getFullDescriptionObjects() {
		return fullDescriptionObjects;
	}
	public void setFullDescriptionObjects(boolean fullDescriptionObjects) {
		this.fullDescriptionObjects = fullDescriptionObjects;
	}
	public boolean getFullRelationshipObjects() {
		return fullRelationshipObjects;
	}
	public void setFullRelationshipObjects(boolean fullRelationshipObjects) {
		this.fullRelationshipObjects = fullRelationshipObjects;
	}
	public boolean getInactiveConcepts() {
		return inactiveConcepts;
	}
	public void setInactiveConcepts(boolean inactiveConcepts) {
		this.inactiveConcepts = inactiveConcepts;
	}
	public boolean getInactiveDescriptions() {
		return inactiveDescriptions;
	}
	public void setInactiveDescriptions(boolean inactiveDescriptions) {
		this.inactiveDescriptions = inactiveDescriptions;
	}
	public boolean getInactiveRelationships() {
		return inactiveRelationships;
	}
	public void setInactiveRelationships(boolean inactiveRelationships) {
		this.inactiveRelationships = inactiveRelationships;
	}
	public boolean getInactiveRefsetMembers() {
		return inactiveRefsetMembers;
	}
	public void setInactiveRefsetMembers(boolean inactiveRefsetMembers) {
		this.inactiveRefsetMembers = inactiveRefsetMembers;
	}
	public boolean getAllRefsets() {
		return allRefsets;
	}
	public void setAllRefsets(boolean allRefsets) {
		this.allRefsets = allRefsets;
	}
	public boolean getFullRefsetMemberObjects() {
		return fullRefsetMemberObjects;
	}
	public void setFullRefsetMemberObjects(boolean fullRefsetMemberObjects) {
		this.fullRefsetMemberObjects = fullRefsetMemberObjects;
	}
	public boolean getJustRefsets() {
		return justRefsets;
	}
	public void setJustRefsets(boolean justRefsets) {
		this.justRefsets = justRefsets;
	}
	public Set<String> getRefsetIds() {
		return refsetIds;
	}
	public void setRefsetIds(Set<String> refsetIds) {
		this.refsetIds = refsetIds;
	}
	public Set<String> getIncludedReferenceSetFilenamePatterns() {
		return includedReferenceSetFilenamePatterns;
	}
	public void setIncludedReferenceSetFilenamePatterns(Set<String> includedReferenceSetFilenamePatterns) {
		this.includedReferenceSetFilenamePatterns = includedReferenceSetFilenamePatterns;
	}
	
	public LoadingProfile createLoadingProfile(){
	
		if (this.useDefaultComplete){
			this.loadingProfile = LoadingProfile.complete;
		}
		else if (this.useDefaultLight){
			this.loadingProfile = LoadingProfile.light;
		}
		else if (this.lightProfile != null){
			loadingProfile = LoadingProfile.light;
			
			if (this.lightProfile.fullRelationshipObjects){
				loadingProfile = loadingProfile.withFullRelationshipObjects();
			}
			
			if (this.lightProfile.fullDescriptionObjects){
				loadingProfile = loadingProfile.withFullDescriptionObjects();
			}
		}
		
		return loadingProfile;
	}

}
