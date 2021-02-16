package fr.excilys.formation.controller;

import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.model.Company;
import fr.excilys.formation.model.Computer;
import fr.excilys.formation.service.DAOCompany;
import fr.excilys.formation.service.DAOComputer;

public class Controller {
	private DAOCompany serviceCompany;
	private DAOComputer serviceComputer;
	
	public Controller() {
		serviceCompany = new DAOCompany();
		serviceComputer = new DAOComputer();
	}

	public List<Computer> getComputers(int offset, int rows) {
		List<Computer> computers = new ArrayList<Computer>();
		computers = serviceComputer.getAllComputers(offset, rows);
		
		return computers;
	}
	
	public List<Company> getCompanies(int offset, int rows) {
		List<Company> companies = new ArrayList<Company>();
		companies = serviceCompany.getAllCompanies(offset, rows);
		
		return companies;
	}
	
	public Computer getComputerByID(int id) {
		return serviceComputer.getComputerByID(id);
	}
	
	
}
