package com.excilys.formation.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.dto.model.CompanyDTOViewAdd;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;

public class CompanyDTOMapper {
	private CompanyDTOMapper() {

	}

	public static CompanyDTOViewAdd companyToDTOViewAdd(Company company) {
		CompanyDTOViewAdd companyDTO = new CompanyDTOViewAdd();
		companyDTO.name = company.getName();
		companyDTO.id = Integer.toString(company.getID());

		return companyDTO;
	}

	public static Company dtoViewAddToCompany(CompanyDTOViewAdd companyDTO) throws ArgumentException {
		companyDTO.validate();
		return new Company.CompanyBuilder().name(companyDTO.name).id(Integer.parseInt(companyDTO.id)).build();
	}

	public static List<CompanyDTOViewAdd> companyListToDTOListViewAdd(List<Optional<Company>> companyList) {
		List<CompanyDTOViewAdd> companyDTOList = new ArrayList<>();
		for (Optional<Company> company : companyList) {
			if (company.isPresent()) {
				companyDTOList.add(companyToDTOViewAdd(company.get()));
			}
		}
		return companyDTOList;
	}
}
