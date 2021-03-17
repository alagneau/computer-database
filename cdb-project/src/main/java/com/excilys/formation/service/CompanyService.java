package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.model.Company;

@Component
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
	
	public List<Optional<Company>> getRange(int offset, int rows) {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyDAO.getRange(offset, rows);
		
		return companies;
	}
	
	public List<Optional<Company>> getAll() {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyDAO.getAll();
		
		return companies;
	}
	
	public boolean exists(int id) {
		return companyDAO.exists(id);
	}

	public void delete(int id) {
		companyDAO.delete(id);
	}
}
