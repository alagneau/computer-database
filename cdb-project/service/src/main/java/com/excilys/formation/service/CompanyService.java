package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.CompanyRepository;
import com.excilys.formation.dao.ComputerRepository;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.ListPage;

@Service
public class CompanyService {
	
	private CompanyRepository companyRepository;
	private ComputerRepository computerRepository;
	
	private CompanyService(CompanyRepository companyRepository, ComputerRepository computerRepository) {
		this.companyRepository = companyRepository;
		this.computerRepository = computerRepository;
	}
	
	public long count() {
		return companyRepository.count();
	}
	
	public Optional<Company> getByID(long id) {
		return companyRepository.findById(id);
	}
	
	public List<Company> getRange(ListPage listPage) {
		return companyRepository.findAll(PageRequest.of(listPage.getIndex(), listPage.getNumberOfValues())).getContent();
	}
	
	public List<Company> getAll() {
		return companyRepository.findAll();
	}
	
	public boolean exists(long id) {
		return companyRepository.existsById(id);
	}

	@Transactional
	public void delete(Company company) {
		computerRepository.deleteByCompany(company);
		companyRepository.delete(company);
	}
}
