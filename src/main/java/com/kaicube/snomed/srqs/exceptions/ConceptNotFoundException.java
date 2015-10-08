package com.kaicube.snomed.srqs.exceptions;

public class ConceptNotFoundException extends NotFoundException {
	public ConceptNotFoundException(String conceptId) {
		super("Concept with id " + conceptId + " could not be found.");
	}
}
