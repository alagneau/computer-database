package com.excilys.formation.dto.model;

import com.excilys.formation.dto.validator.CompanyDTOValidator;
import com.excilys.formation.dto.validator.ComputerDTOValidator;
import com.excilys.formation.exception.ArgumentException;

public class ComputerDTOViewAdd {
	public String name, introduced, discontinued, companyId;
	
	public void validate() throws ArgumentException {
		ComputerDTOValidator.validName(name);
		ComputerDTOValidator.validDates(introduced, discontinued);
		CompanyDTOValidator.validID(companyId);
	}
	
	public String getName() {
		return name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}
}
