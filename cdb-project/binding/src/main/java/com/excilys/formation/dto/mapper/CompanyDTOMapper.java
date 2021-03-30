package com.excilys.formation.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.dto.model.CompanyDTOView;
import com.excilys.formation.dto.model.CompanyDTODatabase;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;

public class CompanyDTOMapper {
	private CompanyDTOMapper() {

	}

	public static CompanyDTOView companyToDTOView(Company company) {
		CompanyDTOView companyDTO = new CompanyDTOView();
		companyDTO.name = company.getName();
		companyDTO.id = Long.toString(company.getId());

		return companyDTO;
	}

	public static Company dtoViewAddToCompany(CompanyDTOView companyDTO) throws ArgumentException {
		companyDTO.validate();
		return new Company.CompanyBuilder().name(companyDTO.name).id(Long.parseLong(companyDTO.id)).build();
	}

	public static List<CompanyDTOView> companyListToDTOViewList(List<Company> companyList) {
		List<CompanyDTOView> companyDTOList = new ArrayList<>();
		for (Company company : companyList) {
			companyDTOList.add(companyToDTOView(company));
		}
		return companyDTOList;
	}

	public static CompanyDTODatabase companyToDTODatabase(Company company) {
		return new CompanyDTODatabase(company.getId(), company.getName());
	}

	public static Company dtoDatabaseToCompany(CompanyDTODatabase companyDTO) {
		try {
			return new Company.CompanyBuilder()
					.name(companyDTO.getName())
					.id(companyDTO.getId())
					.build();
		} catch(ArgumentException exception) {
			return null;
		}
	}
}
