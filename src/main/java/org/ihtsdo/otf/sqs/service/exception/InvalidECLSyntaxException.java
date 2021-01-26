package org.ihtsdo.otf.sqs.service.exception;

import org.antlr.v4.runtime.RecognitionException;
import org.snomed.langauges.ecl.ECLException;

public class InvalidECLSyntaxException extends BadRequestException {

	public InvalidECLSyntaxException(String ecQuery, RecognitionException e) {
		super("Invalid Expression Constraint Language syntax.", e);
	}

	public InvalidECLSyntaxException(String ecQuery, ECLException e) {
		super("Invalid Expression Constraint Language syntax.", e);
	}

}
