package com.excilys.formation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Computer;

@Component
public class ComputerService {
	
	private ComputerDAO computerDAO;
	
	private ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	public int count() throws ReadDataException {
		return computerDAO.count();
	}
	
	public int filterAndCount(String filter) throws ReadDataException {
		return computerDAO.filterAndCount(filter);
	}
	
	public List<Optional<Computer>> getRange(int offset, int rows) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		computers = computerDAO.getRange(offset, rows);
		
		return computers;
	}
	
	public List<Optional<Computer>> getRangeServlet(int offset, int numberOfRows, String search, String orderByValue, String orderByDirection) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		computers = computerDAO.getRangeServlet(offset, numberOfRows, search, orderByValue, orderByDirection);
		
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
	
	public void updateAllParameters(Computer computer) throws UpdatingDataException {
		computerDAO.updateAllParameters(computer);
	}
	
	public void delete(int computerID) throws DeletingDataException {
		computerDAO.delete(computerID);
	}

}
