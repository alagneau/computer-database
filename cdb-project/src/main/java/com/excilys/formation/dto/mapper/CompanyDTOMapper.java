package com.excilys.formation.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.model.CompanyDTOViewAdd;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;

public class CompanyDTOMapper {
	private CompanyDTOMapper() {

	}

	public static CompanyDTOViewAdd companyToDTOViewAdd(Company company) {
		CompanyDTOViewAdd companyDTO = new CompanyDTOViewAdd();
		companyDTO.name = company.getName();
		companyDTO.id = Long.toString(company.getID());

		return companyDTO;
	}

	public static Company dtoViewAddToCompany(CompanyDTOViewAdd companyDTO) throws ArgumentException {
		companyDTO.validate();
		return new Company.CompanyBuilder().name(companyDTO.name).id(Long.parseLong(companyDTO.id)).build();
	}

	public static List<CompanyDTOViewAdd> companyListToDTOListViewAdd(List<Company> companyList) {
		List<CompanyDTOViewAdd> companyDTOList = new ArrayList<>();
		for (Company company : companyList) {
			companyDTOList.add(companyToDTOViewAdd(company));
		}
		return companyDTOList;
	}
}
