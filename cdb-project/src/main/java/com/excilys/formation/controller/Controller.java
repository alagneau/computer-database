package com.excilys.formation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

public class Controller {
	private CompanyService companyService;
	private ComputerService computerService;
	private final static Controller controllerInstance = new Controller();
	
	private Controller() {
		companyService = CompanyService.getInstance();
		computerService = ComputerService.getInstance();
	}
	
	public static Controller getInstance() {
		return controllerInstance;
	}
	
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
	
	public void deleteComputer(Computer computer) throws DeletingDataException {
		computerService.delete(computer);
	}
}
