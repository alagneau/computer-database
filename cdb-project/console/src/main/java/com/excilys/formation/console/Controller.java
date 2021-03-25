package com.excilys.formation.console;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

@Component
@Scope("singleton")
public class Controller {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	public long numberOfComputers() throws ReadDataException {
		return computerService.count();
	}
	
	public long numberOfCompanies() throws ReadDataException {
		return companyService.count();
	}
	
	public List<Computer> getComputerPage(ListPage listPage) throws ReadDataException, ArgumentException {
		return computerService.getRange(listPage);
	}
	
	public List<Company> getCompanyPage(ListPage listPage) throws ReadDataException {
		return companyService.getRange(listPage);
	}
	
	public List<Company> getAllCompanies() throws ReadDataException {
		return companyService.getAll();
	}
	
	public Optional<Computer> getComputerByID(long id) throws ReadDataException, ArgumentException {
		return computerService.getByID(id);
	}
	
	public Optional<Company> getCompanyByID(long id) throws ReadDataException, ArgumentException {
		return companyService.getByID(id);
	}
	
	public boolean computerExists(long id) throws ReadDataException {
		return computerService.exists(id);
	}
	
	public boolean companyExists(long id) throws ReadDataException {
		return companyService.exists(id);
	}
	
	public long addComputer(Computer computer) throws AddDataException {
		return computerService.add(computer);
	}
	
	public void changeComputerName(Computer computer, String name) throws UpdatingDataException {
		computerService.updateName(computer, name);
	}
	
	public void changeComputerCompany(Computer computer, long companyId) throws UpdatingDataException, ArgumentException {
		computerService.updateCompany(computer, companyService.getByID(companyId).get());
	}
	
	public void changeComputerIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		computerService.updateIntroduced(computer, introduced);
	}
	
	public void changeComputerDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		computerService.updateDiscontinued(computer, discontinued);
	}
	
	public void deleteComputer(long computerID) throws DeletingDataException {
		computerService.delete(computerID);
	}
	
	public void deleteCompany(Company company) throws DatabaseAccessException {
		companyService.delete(company);
	}
}
