package com.excilys.formation.dto.mapper;

import com.excilys.formation.dto.model.CompanyDTOViewAdd;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;

public class CompanyDTOMapper {
	public static CompanyDTOViewAdd companyToDTOViewAdd(Company company) {
		CompanyDTOViewAdd companyDTO = new CompanyDTOViewAdd();
		companyDTO.name = company.getName();
		companyDTO.id = Integer.toString(company.getID());
		
		return companyDTO;
	}
	
	public static Company dtoViewAddToCompany(CompanyDTOViewAdd companyDTO) throws ArgumentException{
		companyDTO.validate();
		return new Company.CompanyBuilder().name(companyDTO.name)
						.id(Integer.parseInt(companyDTO.id))
						.build();
	}
}
