package com.excilys.formation.dto.validator;

import com.excilys.formation.exception.ArgumentException;

public class CompanyDTOValidator {
	private CompanyDTOValidator() {
	}

	public static void validName(String name) throws ArgumentException {
//		if (name == null || name.isEmpty()) {
//			throw new ArgumentException("Not a valid company name : '" + name + "'");
//		}
	}

	public static void validID(String id) throws ArgumentException {
		if (id != null && id != "") {
			try {
				Integer.parseInt(id);
			} catch (NumberFormatException exception) {
				throw new ArgumentException("Not a valid company ID : '" + id + "'");
			}
		}
	}
}
