package com.excilys.formation.dto.mapper;

import static com.excilys.formation.constants.GlobalConstants.localDateToString;
import static com.excilys.formation.constants.GlobalConstants.stringToLocalDate;

import com.excilys.formation.dto.model.CompanyDTODatabase;
import com.excilys.formation.dto.model.ComputerDTODatabase;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.dto.model.ComputerDTOViewEdit;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerDTOMapper {
	
	
	private ComputerDTOMapper() { }
	
	public static ComputerDTOViewDashboard computerToDTOViewDashboard(Computer computer) {
		ComputerDTOViewDashboard computerDTO = new ComputerDTOViewDashboard();
		computerDTO.id = Long.toString(computer.getId());
		computerDTO.name = computer.getName();
		computerDTO.introduced = localDateToString(computer.getIntroduced());
		computerDTO.discontinued = localDateToString(computer.getDiscontinued());
		computerDTO.companyName = computer.getCompanyName();
		
		return computerDTO;
	}

	public static Computer dtoViewDashboardToComputer(ComputerDTOViewDashboard computerDTO) throws ArgumentException {
		computerDTO.validate();
		return new Computer.ComputerBuilder(computerDTO.name)
						.introduced(stringToLocalDate(computerDTO.introduced))
						.discontinued(stringToLocalDate(computerDTO.discontinued))
						.company(new Company.CompanyBuilder().name(computerDTO.companyName).build())
						.build();
	}
	
	public static ComputerDTOViewAdd computerToDTOViewAdd(Computer computer) {
		ComputerDTOViewAdd computerDTO = new ComputerDTOViewAdd();
		computerDTO.name = computer.getName();
		computerDTO.introduced = localDateToString(computer.getIntroduced());
		computerDTO.discontinued = localDateToString(computer.getDiscontinued());
		if (computer.getCompany() != null) {
			computerDTO.companyId = String.valueOf(computer.getCompany().getId());
		}
		
		return computerDTO;
	}
	
	public static Computer dtoViewAddToComputer(ComputerDTOViewAdd computerDTO) throws ArgumentException {
		computerDTO.validate();
		Company company = null;
		long companyId = Long.parseLong(computerDTO.companyId);
		if (companyId > 0) {
			company = new Company.CompanyBuilder().id(companyId).build();
		}
		
		Computer computer = new Computer.ComputerBuilder(computerDTO.name)
						.introduced(stringToLocalDate(computerDTO.introduced))
						.discontinued(stringToLocalDate(computerDTO.discontinued))
						.company(company)
						.build();
		return computer;
	}
	
	public static ComputerDTOViewEdit computerToDTOViewEdit(Computer computer) {
		ComputerDTOViewEdit computerDTO = new ComputerDTOViewEdit();
		computerDTO.id = String.valueOf(computer.getId());
		computerDTO.name = computer.getName();
		computerDTO.introduced = localDateToString(computer.getIntroduced());
		computerDTO.discontinued = localDateToString(computer.getDiscontinued());
		if (computer.getCompany() != null) {
			computerDTO.companyID = String.valueOf(computer.getCompany().getId());
		}
		
		return computerDTO;
	}
	
	public static Computer dtoViewEditToComputer(ComputerDTOViewEdit computerDTO) throws ArgumentException {
		computerDTO.validate();
		Company company = null;
		long companyId = Long.parseLong(computerDTO.companyID);
		if (companyId > 0) {
			company = new Company.CompanyBuilder().id(companyId).build();
		}
		
		return new Computer.ComputerBuilder(computerDTO.name)
						.id(Long.parseLong(computerDTO.id))
						.introduced(stringToLocalDate(computerDTO.introduced))
						.discontinued(stringToLocalDate(computerDTO.discontinued))
						.company(company)
						.build();
	}
	
	public static ComputerDTODatabase computerToDTODatabase(Computer computer) {
		ComputerDTODatabase computerDTO = new ComputerDTODatabase();
		computerDTO.setId(computer.getId());
		computerDTO.setName(computer.getName());
		computerDTO.setIntroduced(computer.getIntroduced());
		computerDTO.setDiscontinued(computer.getDiscontinued());
		computerDTO.setCompany(CompanyDTOMapper.companyToDTODatabase(computer.getCompany()));
		
		return computerDTO;
	}
	
	public static Computer dtoDatabaseToComputer(ComputerDTODatabase computerDTO) {
		try {
			CompanyDTODatabase companyDTO = computerDTO.getCompany();
			Company company = null;
			if (companyDTO != null) {
				company = CompanyDTOMapper.dtoDatabaseToCompany(computerDTO.getCompany());
			}
			
			return new Computer.ComputerBuilder(computerDTO.getName())
						.id(computerDTO.getId())
						.introduced(computerDTO.getIntroduced())
						.discontinued(computerDTO.getDiscontinued())
						.company(company)
						.build();
		} catch (ArgumentException exception) {
			return null;
		}
	}
}
