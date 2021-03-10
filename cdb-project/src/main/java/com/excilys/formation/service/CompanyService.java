package com.excilys.formation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.dao.CompanyDAO;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.model.Company;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyService {
	@Autowired
	private CompanyDAO companyDAO;
	
	public int count() throws ReadDataException {
		return companyDAO.count();
	}
	
	public Optional<Company> getByID(int id) throws ReadDataException, ArgumentException {
		return companyDAO.getByID(id);
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

	public void delete(int id) throws DatabaseAccessException {
		companyDAO.delete(id);
	}
}
