package com.excilys.formation.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.CompanyRepository;
import com.excilys.formation.dao.ComputerRepository;
import com.excilys.formation.dto.mapper.CompanyDTOMapper;
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
		return Optional.ofNullable(CompanyDTOMapper.dtoDatabaseToCompany(companyRepository.findById(id)));
	}
	
	public List<Company> getRange(ListPage listPage) {
		return companyRepository.findAll(PageRequest.of(listPage.getIndex(), listPage.getNumberOfValues())).getContent()
				.stream().map(CompanyDTOMapper::dtoDatabaseToCompany).collect(Collectors.toList());
	}
	
	public List<Company> getRangeServlet(ListPage listPage) {
		return companyRepository.findAllByNameContaining(listPage.getSearchValue(),
												PageRequest.of(listPage.getIndex() - 1,
													listPage.getNumberOfValues(),
													listPage.getOrderByDirection(),
													listPage.getOrderByValue().getRequest())
			).stream().map(CompanyDTOMapper::dtoDatabaseToCompany).collect(Collectors.toList());
	}
	
	public List<Company> getAll() {
		return companyRepository.findAll().stream().map(CompanyDTOMapper::dtoDatabaseToCompany).collect(Collectors.toList());
	}
	
	public boolean exists(long id) {
		return companyRepository.existsById(id);
	}

	@Transactional
	public void delete(long id) {
		computerRepository.deleteByCompany(id);
		companyRepository.deleteById(id);
	}
}
