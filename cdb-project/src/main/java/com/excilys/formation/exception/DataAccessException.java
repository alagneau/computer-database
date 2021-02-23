package com.excilys.formation.exception;

public class DataAccessException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataAccessException() {
		super();
	}
	
	public DataAccessException(String message) {
		super(message);
	}
}
