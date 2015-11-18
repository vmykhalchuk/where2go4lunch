package org.vmykhalchuk.where2go4lunch.service;


public class VoteTimeTooLateException extends Exception {

	private static final long serialVersionUID = -5205582159574403700L;

	public VoteTimeTooLateException() {
		super();
	}

	public VoteTimeTooLateException(String message, Throwable cause) {
		super(message, cause);
	}

	public VoteTimeTooLateException(String message) {
		super(message);
	}

	public VoteTimeTooLateException(Throwable cause) {
		super(cause);
	}

}
