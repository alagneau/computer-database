package com.excilys.formation.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.DAOCompany;
import com.excilys.formation.service.DAOComputer;

public class Controller {
	private DAOCompany daoCompany;
	private DAOComputer daoComputer;
	private static Controller controller;
	
	private Controller() {
		daoCompany = DAOCompany.getInstance();
		daoComputer = DAOComputer.getInstance();
	}
	
	public static Controller getInstance() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	public int numberOfComputers() throws ReadDataException {
		return daoComputer.count();
	}
	
	public int numberOfCompanies() throws ReadDataException {
		return daoCompany.count();
	}
	
	public List<Computer> getComputers(int offset, int rows) throws ReadDataException {
		List<Computer> computers = new ArrayList<Computer>();
		computers = daoComputer.getRange(offset, rows);
		
		return computers;
	}
	
	public List<Company> getCompanies(int offset, int rows) throws ReadDataException {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getRange(offset, rows);
		
		return companies;
	}
	
	public List<Company> getAllCompanies() throws ReadDataException {
		List<Company> companies = new ArrayList<Company>();
		companies = daoCompany.getAll();
		
		return companies;
	}
	
	public Computer getComputerByID(int id) throws ReadDataException, ArgumentException {
		return daoComputer.getByID(id);
	}
	
	public boolean computerExists(int id) throws ReadDataException {
		return daoComputer.exists(id);
	}
	
	public boolean companyExists(int id) throws ReadDataException {
		return daoCompany.exists(id);
	}
	
	public int addComputer(Computer computer) throws AddDataException {
		return daoComputer.add(computer);
	}
	
	public void changeComputerName(Computer computer, String name) throws UpdatingDataException {
		daoComputer.updateName(computer, name);
	}
	
	public void changeComputerCompany(Computer computer, int companyID) throws UpdatingDataException, ArgumentException {
		daoComputer.updateCompany(computer, companyID);
	}
	
	public void changeComputerIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		daoComputer.updateIntroduced(computer, introduced);
	}
	
	public void changeComputerDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		daoComputer.updateIntroduced(computer, discontinued);
	}
	
	public void deleteComputer(Computer computer) throws DeletingDataException {
		daoComputer.delete(computer);
	}
}
