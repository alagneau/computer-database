package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.constants.GlobalConstants;
import com.excilys.formation.controller.Controller;
import com.excilys.formation.model.Company;

public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private List<Company> companies = new ArrayList<Company>();

	public AddComputer() {
		super();
		controller = Controller.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		companies = controller.getAllCompanies();
		
		request.setAttribute("companies", companies);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		try {
			
			controller.addComputer(request.getParameter("computerName"),
								Integer.parseInt(request.getParameter("companyId")),
								GlobalConstants.StringToLocalDate(request.getParameter("introduced")),
								GlobalConstants.StringToLocalDate(request.getParameter("discontinued")));
			
			 
		} catch (Exception exception) {
			exception.printStackTrace();
			
		}
		doGet(request, response);
	}

}
