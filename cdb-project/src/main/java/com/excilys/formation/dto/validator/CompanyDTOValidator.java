package com.excilys.formation.dto.validator;

import com.excilys.formation.exception.ArgumentException;

public class CompanyDTOValidator {

	public static void validName(String name) throws ArgumentException {
		if (name == null || name.isEmpty()) {
			throw new ArgumentException("Not a valid name : '" + name + "'");
		}
	}

	public static void validID(String id) throws ArgumentException {
		try {
			if (Integer.parseInt(id) <= 0) {
				throw new ArgumentException("Not a valid ID : " + id);
			}
		} catch (Exception exception) {
			throw new ArgumentException(exception.getMessage());
		}
	}

}
