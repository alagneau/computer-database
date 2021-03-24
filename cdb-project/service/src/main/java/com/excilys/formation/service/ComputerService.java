package com.excilys.formation.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.dao.ComputerRepository;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;

@Service
public class ComputerService {
	CDBLogger logger = new CDBLogger(ComputerService.class);
	
	private ComputerRepository computerRepository;
	
	public ComputerService(ComputerRepository computerRepository) {
		this.computerRepository = computerRepository;
	}
	
	public long count() {
		return computerRepository.count();
	}
	
	public long filterAndCount(String filter) {
		return computerRepository.countByNameContaining(filter);
	}
	
	public List<Computer> getRange(ListPage listPage) {
		return computerRepository.findAll(PageRequest.of(listPage.getIndex() - 1, listPage.getNumberOfValues())).getContent();
	}
	
	public List<Computer> getRangeServlet(ListPage listPage) {
		return computerRepository.findAllByNameContaining(listPage.getSearchValue(),
												PageRequest.of(listPage.getIndex() - 1,
													listPage.getNumberOfValues(),
													listPage.getOrderByDirection(),
													listPage.getOrderByValue().getRequest())
												);
	}
	
	public Optional<Computer> getByID(long id) {
		return computerRepository.findById(id);
	}
	
	public boolean exists(long id) {
		return computerRepository.existsById(id);
	}
	
	@Transactional
	public long add(Computer computer) {
		return computerRepository.save(computer).getId();
	}
	
	public void updateName(Computer computer, String name) {
		computerRepository.updateName(computer.getId(), name);
	}
	
	public void updateCompany(Computer computer, long companyID) {
		computerRepository.updateCompany(computer.getId(), companyID);
	}
	
	public void updateIntroduced(Computer computer, LocalDate introduced) {
		computerRepository.updateIntroduced(computer.getId(), introduced);
	}
	
	public void updateDiscontinued(Computer computer, LocalDate discontinued) {
		computerRepository.updateDiscontinued(computer.getId(), discontinued);
	}
	
	public void updateAllParameters(Computer computer) {
		computerRepository.save(computer);
	}
	
	public void delete(long computerID) {
		computerRepository.deleteById(computerID);
	}
	
}
