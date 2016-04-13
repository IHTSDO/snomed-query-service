package org.ihtsdo.otf.sqs.service.exception;

public class ConceptNotFoundException extends NotFoundException {
	public ConceptNotFoundException(String conceptId) {
		super("Concept with id " + conceptId + " could not be found.");
	}
}
