package com.excilys.formation.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class Home extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{
		
		
		request.setAttribute("message", "coucou !");
		
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		
	}
}
