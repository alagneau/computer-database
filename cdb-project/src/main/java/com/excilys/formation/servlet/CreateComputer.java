package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.constants.GlobalConstants;
import com.excilys.formation.controller.Controller;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

@WebServlet("/CreateComputer")
public class CreateComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private List<Company> companies = new ArrayList<Company>();

	public CreateComputer() {
		super();
		controller = Controller.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		companies = controller.getAllCompanies();
		
		request.setAttribute("companies", companies);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/createComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		try {
			Company company = new Company.CompanyBuilder(Integer.parseInt(request.getParameter("select-company"))).build();
			Computer computer = new Computer.ComputerBuilder(request.getParameter("nom"))
											.company(company)
											.introduced(GlobalConstants.StringToLocalDate(request.getParameter("introduced")))
											.discontinued(GlobalConstants.StringToLocalDate(request.getParameter("discontinued")))
											.build();
			controller.addComputer(computer);
			this.getServletContext().getRequestDispatcher("/WEB-INF/validationComputer.jsp").forward(request, response);
		} catch (ArgumentException exception) {
			exception.printStackTrace();
			doGet(request, response);
		}
		
	}

}
