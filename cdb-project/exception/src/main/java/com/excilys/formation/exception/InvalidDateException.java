package com.excilys.formation.exception;

public class InvalidDateException extends ArgumentException {
	private static final long serialVersionUID = 1L;
	
	public InvalidDateException() {
		super("Not a valid date format");
	}

}
