package com.excilys.formation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.DAOCompany;
import com.excilys.formation.service.DAOComputer;

public class Controller {
	private DAOCompany daoCompany;
	private DAOComputer daoComputer;
	private static Controller controller;
	
	private Controller() {
		daoCompany = new DAOCompany();
		daoComputer = new DAOComputer();
	}
	
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	public int numberOfComputers() {
		return daoComputer.numberOfComputers();
	}
	
	public int numberOfCompanies() {
		return daoCompany.numberOfCompanies();
	}
	
	public List<Computer> getComputers(int offset, int rows) {
		List<Computer> computers = new ArrayList<Computer>();
		computers = daoComputer.getComputers(offset, rows);
		
		return computers;
	}
	
	public List<Company> getCompanies(int offset, int rows) {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getCompanies(offset, rows);
		
		return companies;
	}
	
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getAllCompanies();
		
		return companies;
	}
	
	public Computer getComputerByID(int id) {
		return daoComputer.getComputerByID(id);
	}
	
	public boolean computerExists(int id) {
		return daoComputer.computerExists(id);
	}
	
	public boolean companyExists(int id) {
		return daoCompany.companyExists(id);
	}
	
	public int addComputer(Computer computer) {
		return daoComputer.addComputer(computer);
	}
	
	public boolean changeComputerName(int computerID, String name) {
		return daoComputer.changeComputerName(computerID, name);
	}
	
	public boolean changeComputerCompany(int computerID, int companyID) {
		return daoComputer.changeComputerCompany(computerID, companyID);
	}
	
	public boolean changeComputerIntroduced(int computerID, LocalDate introduced) {
		return daoComputer.changeComputerIntroduced(computerID, introduced);
	}
	
	public boolean changeComputerDiscontinued(int computerID, LocalDate discontinued) {
		return daoComputer.changeComputerIntroduced(computerID, discontinued);
	}
	
	public boolean deleteComputer(int id) {
		return daoComputer.deleteComputer(id);
	}
}
