package org.xproject.moony.data.exceptions;

public class UnrecognizedDatabaseObjectException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;

	public UnrecognizedDatabaseObjectException(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message + " " + super.toString();
	}
}
