package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.model.Company;

@Service
public class CompanyService {
	
	private CompanyDAO companyDAO;
	
	private CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public int count() {
		return companyDAO.count();
	}
	
	public Optional<Company> getByID(int id) {
		return companyDAO.getByID(id);
	}
	
	public List<Company> getRange(int offset, int rows) {
		return companyDAO.getRange(offset, rows);
	}
	
	public List<Company> getAll() {
		return companyDAO.getAll();
	}
	
	public boolean exists(int id) {
		return companyDAO.exists(id);
	}

	public void delete(int id) {
		companyDAO.delete(id);
	}
}
