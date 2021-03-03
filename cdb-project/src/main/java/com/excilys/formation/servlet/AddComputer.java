package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.controller.Controller;
import com.excilys.formation.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.CompanyDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.model.Company;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private CompanyService companyService;
	private static Logger logger = LoggerFactory.getLogger(ListComputers.class);

	public AddComputer() {
		super();
		computerService = ComputerService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<CompanyDTOViewAdd> listOfCompanies = new ArrayList<>();
		
		try {
			List<Optional<Company>> companies = companyService.getAll();
		
			for(Optional<Company> company : companies) {
				if(company.isPresent()) {
					listOfCompanies.add(CompanyDTOMapper.companyToDTOViewAdd(company.get()));
				} else {
					listOfCompanies.add(new CompanyDTOViewAdd());
				}
			}
		} catch(DatabaseAccessException exception) {
			logger.info(exception.getMessage());
		}
			
		request.setAttribute("companies", listOfCompanies);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			ComputerDTOViewAdd computerDTO = new ComputerDTOViewAdd();
			computerDTO.name = request.getParameter("computerName");
			computerDTO.introduced = request.getParameter("introduced");
			computerDTO.discontinued = request.getParameter("discontinued");
			computerDTO.companyID = request.getParameter("companyId");
			
			computerService.add(ComputerDTOMapper.dtoViewAddToComputer(computerDTO));
			
			 
		} catch (Exception exception) {
			logger.info(exception.getMessage());
			
		}
		doGet(request, response);
	}

}
