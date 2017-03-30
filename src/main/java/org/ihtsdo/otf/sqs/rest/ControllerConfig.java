package org.ihtsdo.otf.sqs.rest;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(Exception.class)
	ResponseEntity<Error> exceptionCatchAll(Exception e) {
		logger.error("{}", e.getMessage(), e);
		return response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<Error> response(String message, HttpStatus status) {
		return new ResponseEntity<>(new Error(status, message), status);
	}

	@JsonPropertyOrder({"status", "statusMessage", "message"})
	private static final class Error {

		private HttpStatus status;
		private String message;

		Error(HttpStatus status, String message) {
			this.status = status;
			this.message = message;
		}

		public int getStatus() {
			return status.value();
		}

		public String getStatusMessage() {
			return status.getReasonPhrase();
		}

		public String getMessage() {
			return message;
		}
	}

}
