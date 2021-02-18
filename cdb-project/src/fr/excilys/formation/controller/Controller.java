package fr.excilys.formation.controller;

import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.model.Company;
import fr.excilys.formation.model.Computer;
import fr.excilys.formation.service.DAOCompany;
import fr.excilys.formation.service.DAOComputer;

public class Controller {
	private DAOCompany daoCompany;
	private DAOComputer daoComputer;
	
	public Controller() {
		daoCompany = new DAOCompany();
		daoComputer = new DAOComputer();
	}
	public int numberOfComputers() {
		return daoComputer.numberOfComputers();
	}
	
	public int numberOfCompanies() {
		return daoCompany.numberOfCompanies();
	}
	
	public List<Computer> getComputers(int offset, int rows) {
		List<Computer> computers = new ArrayList<Computer>();
		computers = daoComputer.getAllComputers(offset, rows);
		
		return computers;
	}
	
	public List<Company> getCompanies(int offset, int rows) {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getAllCompanies(offset, rows);
		
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
	
	public boolean deleteComputer(int id) {
		return daoComputer.deleteComputer(id);
	}
}
