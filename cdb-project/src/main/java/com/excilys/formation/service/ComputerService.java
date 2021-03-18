package com.excilys.formation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.formation.dao.ComputerDAO;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;

@Service
public class ComputerService {
	
	private ComputerDAO computerDAO;
	
	private ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	public int count() {
		return computerDAO.count();
	}
	
	public int filterAndCount(String filter) {
		return computerDAO.filterAndCount(filter);
	}
	
	public List<Computer> getRange(int offset, int rows) {
		return computerDAO.getRange(offset, rows);
	}
	
	public List<Computer> getRangeServlet(ListPage listPage) {
		return computerDAO.getRangeServlet(listPage.getOffset(), listPage.getNumberOfValues(),
								listPage.getSearchValue(), listPage.getOrderByValue().getRequest(),
								listPage.getOrderByDirection().getRequest());
	}
	
	public Optional<Computer> getByID(int id) {
		return computerDAO.getByID(id);
	}
	
	public boolean exists(int id) {
		return computerDAO.exists(id);
	}
	
	public int add(Computer computer) {
		return computerDAO.add(computer);
	}
	
	public void updateName(Computer computer, String name) {
		computerDAO.updateName(computer, name);
	}
	
	public void updateCompany(Computer computer, int companyID) {
		computerDAO.updateCompany(computer, companyID);
	}
	
	public void updateIntroduced(Computer computer, LocalDate introduced) {
		computerDAO.updateIntroduced(computer, introduced);
	}
	
	public void updateDiscontinued(Computer computer, LocalDate discontinued) {
		computerDAO.updateDiscontinued(computer, discontinued);
	}
	
	public void updateAllParameters(Computer computer) {
		computerDAO.updateAllParameters(computer);
	}
	
	public void delete(int computerID) {
		computerDAO.delete(computerID);
	}

}
