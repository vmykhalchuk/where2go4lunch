package org.vmykhalchuk.where2go4lunch.rest;

import javax.ws.rs.core.Response.Status;

public class RestException extends Exception {

	private static final long serialVersionUID = -167447334439277324L;
	
	private Status statusCode = Status.INTERNAL_SERVER_ERROR;

	public RestException() {
		super();
	}

	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestException(String message) {
		super(message);
	}

	public RestException(Throwable cause) {
		super(cause);
	}

	public RestException(Status statusCode) {
		super();
		this.statusCode = statusCode;
	}
	
	public RestException(Status statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public RestException(Status statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public RestException(Status statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}
	
	public Status getStatusCode() {
		return statusCode;
	}
	
}
