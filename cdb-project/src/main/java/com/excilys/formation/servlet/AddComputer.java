package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private static Logger logger = LoggerFactory.getLogger(ListComputers.class);

	public AddComputer() {
		super();
		controller = Controller.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<CompanyDTOViewAdd> listOfCompanies = new ArrayList<>();
		
		try {
			List<Company> companies = controller.getAllCompanies();
		
			for(Company company : companies) {
				listOfCompanies.add(CompanyDTOMapper.companyToDTOViewAdd(company));
			}
		} catch(DatabaseAccessException exception) {
			System.out.println(exception.getMessage());
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
			
			controller.addComputer(ComputerDTOMapper.dtoViewAddToComputer(computerDTO));
			
			/*
			controller.addComputer(new Computer.ComputerBuilder(request.getParameter("computerName"))
									.company(new Company.CompanyBuilder().id(Integer.parseInt(request.getParameter("companyId"))).build())
									.introduced(stringToLocalDate(request.getParameter("introduced")))
									.discontinued(stringToLocalDate(request.getParameter("discontinued")))
									.build());
									*/
			
			 
		} catch (Exception exception) {
			logger.info(exception.getMessage());
			
		}
		doGet(request, response);
	}

}
