package com.thinqq.qsports.server.error;

import java.util.Map;

/**
 * Exception is thrown when the Sufficient parameters to satisfy a service are not
 * passes in the request 
 *
 */
public class InvalidRequestArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**Invalid parameters*/
	Map<String, String> invalidParams;

	String message;

	public InvalidRequestArgumentException() {

	}

	public InvalidRequestArgumentException(String message) {
		super(message);
		this.message = message;
	}

	public InvalidRequestArgumentException(String message,
			Map<String, String> invalidParams) {
		super(message);
		this.message = message;
		this.invalidParams = invalidParams;
	}

	public Map<String, String> getInvalidParams() {
		return invalidParams;
	}

	public void setInvalidParams(Map<String, String> invalidParams) {
		this.invalidParams = invalidParams;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
