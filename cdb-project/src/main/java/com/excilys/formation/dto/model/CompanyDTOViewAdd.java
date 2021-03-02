package com.excilys.formation.dto.model;

import com.excilys.formation.dto.validator.CompanyDTOValidator;
import com.excilys.formation.exception.ArgumentException;

public class CompanyDTOViewAdd {
	public String id, name;
	
	public void validate() throws ArgumentException {
		CompanyDTOValidator.validID(id);
		CompanyDTOValidator.validName(name);
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}
}
