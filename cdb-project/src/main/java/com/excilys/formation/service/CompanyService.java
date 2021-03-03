package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.model.Company;

public class CompanyService {
	private final static CompanyService companyServiceInstance = new CompanyService();
	private final static CompanyDAO companyDAO = CompanyDAO.getInstance();

	public static CompanyService getInstance() {
		return companyServiceInstance;
	}
	
	public int count() throws ReadDataException {
		return companyDAO.count();
	}
	
	public List<Optional<Company>> getRange(int offset, int rows) throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyDAO.getRange(offset, rows);
		
		return companies;
	}
	
	public List<Optional<Company>> getAll() throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyDAO.getAll();
		
		return companies;
	}
	
	public boolean exists(int id) throws ReadDataException {
		return companyDAO.exists(id);
	}

}
