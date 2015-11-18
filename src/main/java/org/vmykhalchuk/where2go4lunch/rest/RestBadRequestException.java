package org.vmykhalchuk.where2go4lunch.rest;

import javax.ws.rs.core.Response.Status;

public class RestBadRequestException extends RestException {

	private static final long serialVersionUID = 6739238344650836535L;

	public RestBadRequestException() {
		super(Status.BAD_REQUEST);
	}

	public RestBadRequestException(String message) {
		super(Status.BAD_REQUEST, message);
	}
	
	public RestBadRequestException(Throwable cause) {
		super(Status.BAD_REQUEST, cause);
	}
	
	public RestBadRequestException(String message, Throwable cause) {
		super(Status.BAD_REQUEST, message, cause);
	}
	
}
