package com.excilys.formation.exception;

public class ArgumentException extends Exception {
	private static final long serialVersionUID = 1L;

	public ArgumentException() {
		super();
	}
	
	public ArgumentException(String message) {
		super(message);
	}
}
