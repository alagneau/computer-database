package com.excilys.formation.exception;

public class UpdatingDataException extends DatabaseAccessException {
	private static final long serialVersionUID = 2804674381366890371L;
	
	public UpdatingDataException(String message) {
		super(message);
	}

}
