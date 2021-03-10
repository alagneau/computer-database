package com.excilys.formation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
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
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Controller {
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	
	public int numberOfComputers() throws ReadDataException {
		return computerService.count();
	}
	
	public int numberOfCompanies() throws ReadDataException {
		return companyService.count();
	}
	
	public List<Optional<Computer>> getComputers(int offset, int rows) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		computers = computerService.getRange(offset, rows);
		
		return computers;
	}
	
	public List<Optional<Company>> getCompanies(int offset, int rows) throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyService.getRange(offset, rows);
		
		return companies;
	}
	
	public List<Optional<Company>> getAllCompanies() throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyService.getAll();
		
		return companies;
	}
	
	public Optional<Computer> getComputerByID(int id) throws ReadDataException, ArgumentException {
		return computerService.getByID(id);
	}
	
	public Optional<Company> getCompanyByID(int id) throws ReadDataException, ArgumentException {
		return companyService.getByID(id);
	}
	
	public boolean computerExists(int id) throws ReadDataException {
		return computerService.exists(id);
	}
	
	public boolean companyExists(int id) throws ReadDataException {
		return companyService.exists(id);
	}
	
	public int addComputer(Computer computer) throws AddDataException {
		return computerService.add(computer);
	}
	
	public void changeComputerName(Computer computer, String name) throws UpdatingDataException {
		computerService.updateName(computer, name);
	}
	
	public void changeComputerCompany(Computer computer, int companyID) throws UpdatingDataException, ArgumentException {
		computerService.updateCompany(computer, companyID);
	}
	
	public void changeComputerIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		computerService.updateIntroduced(computer, introduced);
	}
	
	public void changeComputerDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		computerService.updateDiscontinued(computer, discontinued);
	}
	
	public void deleteComputer(int computerID) throws DeletingDataException {
		computerService.delete(computerID);
	}
	
	public void deleteCompany(int companyID) throws DatabaseAccessException {
		companyService.delete(companyID);
	}
}
