package com.excilys.formation.exception;

public class DeletingDataException extends DatabaseAccessException {
	private static final long serialVersionUID = 4814936248446087718L;
	
	public DeletingDataException(String description) {
		super("Error while trying to delete " + description);
	}
}
