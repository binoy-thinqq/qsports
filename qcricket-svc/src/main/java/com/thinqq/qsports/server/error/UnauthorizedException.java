package com.thinqq.qsports.server.error;

public class UnauthorizedException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public UnauthorizedException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
