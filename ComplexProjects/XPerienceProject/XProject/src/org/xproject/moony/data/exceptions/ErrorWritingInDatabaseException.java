package org.xproject.moony.data.exceptions;

public class ErrorWritingInDatabaseException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;
	
	public ErrorWritingInDatabaseException(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Exception: Error writing in database. "+message + "" + super.toString();
	}
}
