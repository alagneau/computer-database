package com.excilys.formation.exception;

public class DBConnectionException extends Exception {
	private static final long serialVersionUID = 1L;

	public DBConnectionException() {
		super();
	}
	
	public DBConnectionException(String message) {
		super(message);
	}
}
