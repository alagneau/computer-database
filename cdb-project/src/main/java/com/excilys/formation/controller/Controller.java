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
		return daoComputer.count();
	}
	
	public int numberOfCompanies() {
		return daoCompany.count();
	}
	
	public List<Computer> getComputers(int offset, int rows) {
		List<Computer> computers = new ArrayList<Computer>();
		computers = daoComputer.getRange(offset, rows);
		
		return computers;
	}
	
	public List<Company> getCompanies(int offset, int rows) {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getRange(offset, rows);
		
		return companies;
	}
	
	public List<Company> getAllCompanies() {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getAll();
		
		return companies;
	}
	
	public Computer getComputerByID(int id) {
		return daoComputer.getByID(id);
	}
	
	public boolean computerExists(int id) {
		return daoComputer.exists(id);
	}
	
	public boolean companyExists(int id) {
		return daoCompany.exists(id);
	}
	
	public int addComputer(Computer computer) {
		return daoComputer.add(computer);
	}
	
	public boolean changeComputerName(int computerID, String name) {
		return daoComputer.updateName(computerID, name);
	}
	
	public boolean changeComputerCompany(int computerID, int companyID) {
		return daoComputer.updateCompany(computerID, companyID);
	}
	
	public boolean changeComputerIntroduced(int computerID, LocalDate introduced) {
		return daoComputer.updateIntroduced(computerID, introduced);
	}
	
	public boolean changeComputerDiscontinued(int computerID, LocalDate discontinued) {
		return daoComputer.updateIntroduced(computerID, discontinued);
	}
	
	public boolean deleteComputer(int id) {
		return daoComputer.delete(id);
	}
}
