package com.excilys.formation.dto.model;

import com.excilys.formation.dto.validator.CompanyDTOValidator;
import com.excilys.formation.dto.validator.ComputerDTOValidator;
import com.excilys.formation.exception.ArgumentException;

public class ComputerDTOViewDashboard {
	public String id, name, introduced, discontinued, companyName;

	public void validate() throws ArgumentException {
		ComputerDTOValidator.validID(id);
		ComputerDTOValidator.validName(name);
		ComputerDTOValidator.validDates(introduced, discontinued);
		CompanyDTOValidator.validName(companyName);
	}
	
	public String getId() {
		return id;
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

	public String getCompanyName() {
		return companyName;
	}
}
