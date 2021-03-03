package com.excilys.formation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Computer;

public class ComputerService {
	private final static ComputerService computerServiceInstance = new ComputerService();
	private final static ComputerDAO computerDAO = ComputerDAO.getInstance();

	public static ComputerService getInstance() {
		return computerServiceInstance;
	}
	
	public int count() throws ReadDataException {
		return computerDAO.count();
	}
	
	public List<Optional<Computer>> getRange(int offset, int rows) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		computers = computerDAO.getRange(offset, rows);
		
		return computers;
	}
	
	public Optional<Computer> getByID(int id) throws ReadDataException, ArgumentException {
		return computerDAO.getByID(id);
	}
	
	public boolean exists(int id) throws ReadDataException {
		return computerDAO.exists(id);
	}
	
	public int add(Computer computer) throws AddDataException {
		return computerDAO.add(computer);
	}
	
	public void updateName(Computer computer, String name) throws UpdatingDataException {
		computerDAO.updateName(computer, name);
	}
	
	public void updateCompany(Computer computer, int companyID) throws UpdatingDataException, ArgumentException {
		computerDAO.updateCompany(computer, companyID);
	}
	
	public void updateIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		computerDAO.updateIntroduced(computer, introduced);
	}
	
	public void updateDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		computerDAO.updateDiscontinued(computer, discontinued);
	}
	
	public void delete(Computer computer) throws DeletingDataException {
		computerDAO.delete(computer);
	}

}
