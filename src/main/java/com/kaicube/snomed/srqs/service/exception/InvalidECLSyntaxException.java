package com.kaicube.snomed.srqs.service.exception;

import org.antlr.v4.runtime.RecognitionException;

public class InvalidECLSyntaxException extends BadRequestException {

	public InvalidECLSyntaxException(String ecQuery, RecognitionException e) {
		super("Invalid Expression Constraint Language syntax.", e);
	}

}
