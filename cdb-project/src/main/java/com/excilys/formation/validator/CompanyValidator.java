package com.excilys.formation.validator;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;

public abstract class CompanyValidator {
	public static void validCompany(Company company) throws ArgumentException {
		validName(company);
		validID(company);
	}
	
	private static void validName(Company company) throws ArgumentException {
		String name = company.getName();
		if (name == null || name == "") {
			throw new ArgumentException("Not a valid name : '" + name + "'");
		}
	}
	
	private static void validID(Company company) throws ArgumentException {
		int id = company.getID();
		if (id <= 0) {
			throw new ArgumentException("Not a valid ID : " + id);
		}
	}
}
