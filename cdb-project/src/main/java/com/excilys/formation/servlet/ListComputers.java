package com.excilys.formation.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.controller.Controller;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;

public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private static Logger logger = LoggerFactory.getLogger(ListComputers.class);

	public ListComputers() {
		super();
		controller = Controller.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		ListPage<Computer> listPage = getPage(session);

		int maxComputers = controller.numberOfComputers();
		int numberOfValues = getIntParameter(request.getParameter("numberOfValues"));
		listPage.setMaxComputers(maxComputers);
		
		listPage.changePage(getIntParameter(request.getParameter("pageIndex")));
		listPage.changeNumberOfValues(numberOfValues);

		listPage.setValues(controller.getComputers(listPage.getOffset(), listPage.getNumberOfValues()));
		request.setAttribute("listComputers", listPage.getValues());
		request.setAttribute("maxComputers", maxComputers);
		request.setAttribute("numberOfValues", numberOfValues);
		request.setAttribute("pageIndex",listPage.getIndex());
		request.setAttribute("maxPage", listPage.getMaxPageValue());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private ListPage<Computer> getPage(HttpSession session) {
		ListPage<Computer> listPage = null;
		try {
			listPage = (ListPage<Computer>) session.getAttribute("listPage");
		} catch(ClassCastException exception) {
			logger.error(exception.getMessage());
		}
		if (listPage == null) {
			listPage = new ListPage.ListPageBuilder<Computer>().index(1).numberOfValues(10).build();
		}
		
		return listPage;
	}
	
	private int getIntParameter(String name) {
		try {
			return Integer.parseInt(name);
		} catch (Exception exception) {
			return -1;
		}
	}

}
