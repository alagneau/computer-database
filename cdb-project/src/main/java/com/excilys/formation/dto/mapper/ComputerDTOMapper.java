package com.excilys.formation.dto.mapper;

import static com.excilys.formation.constants.GlobalConstants.*;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerDTOMapper {
	
	public static ComputerDTOViewDashboard computerToDTOViewDashboard(Computer computer) {
		ComputerDTOViewDashboard computerDTO = new ComputerDTOViewDashboard();
		computerDTO.name = computer.getName();
		computerDTO.introduced = localDateToString(computer.getIntroduced());
		computerDTO.discontinued = localDateToString(computer.getDiscontinued());
		computerDTO.companyName = computer.getCompany().getName();
		
		return computerDTO;
	}

	public static Computer dtoViewDashboardToComputer(ComputerDTOViewDashboard computerDTO) throws ArgumentException {
		computerDTO.validate();
		return new Computer.ComputerBuilder(computerDTO.name)
						.introduced(stringToLocalDate(computerDTO.introduced))
						.discontinued(stringToLocalDate(computerDTO.introduced))
						.company(new Company.CompanyBuilder().name(computerDTO.companyName).build())
						.build();
	}
	
	public static ComputerDTOViewAdd computerToDTOViewAdd(Computer computer) {
		ComputerDTOViewAdd computerDTO = new ComputerDTOViewAdd();
		computerDTO.name = computer.getName();
		computerDTO.introduced = localDateToString(computer.getIntroduced());
		computerDTO.discontinued = localDateToString(computer.getDiscontinued());
		computerDTO.companyID = Integer.toString(computer.getCompany().getID());
		
		return computerDTO;
	}
	
	public static Computer dtoViewAddToComputer(ComputerDTOViewAdd computerDTO) throws ArgumentException{
		computerDTO.validate();
		return new Computer.ComputerBuilder(computerDTO.name)
						.introduced(stringToLocalDate(computerDTO.introduced))
						.discontinued(stringToLocalDate(computerDTO.introduced))
						.company(new Company.CompanyBuilder().id(Integer.parseInt(computerDTO.companyID)).build())
						.build();
	}
}
