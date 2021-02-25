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
import com.excilys.formation.model.Computer;

public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	List<Computer> listComputers = new ArrayList<Computer>();
	private Controller controller;
	private int numberOfRows = 10, offset = 0, maxComputer = 0;
	private String searchValue = "";
	private static Logger logger = LoggerFactory.getLogger(ListComputers.class);

	public ListComputers() {
		super();
		controller = Controller.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		changePageIndex(request.getParameter("pageIndex"));
		changeNumberOfRows(request.getParameter("numberOfRows"));
		
		maxComputer = controller.numberOfComputers();

		Optional<String> switchPage = Optional.ofNullable((String)request.getParameter("switchPage"));
		
		if (switchPage.isPresent()) {
			switch(switchPage.get()) {
			case "Precedent":
				offset = offset - numberOfRows;
				offset = (offset < 0) ? maxComputer + offset : offset;
				logger.info("Affichage de la page précédente");
				break;
			case "Suivant":
				offset = offset + numberOfRows;
				offset = (offset >= maxComputer) ? 0 : offset;
				logger.info("Affichage de la page suivante");
				break;
			}
		}

		listComputers = controller.getComputers(offset, numberOfRows);
		request.setAttribute("listComputers", listComputers);
		request.setAttribute("indiceComputer", offset);
		request.setAttribute("maxComputers", maxComputer);
		request.setAttribute("numberOfRows", numberOfRows);
		request.setAttribute("pageIndex", pageIndex());
		request.setAttribute("maxPage", maxPage());

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private int pageIndex() {
		return offset / numberOfRows + 1;
	}
	
	private int maxPage() {
		return (maxComputer-1) / numberOfRows + 1;
	}
	
	private void changeNumberOfRows(String value) {
		int newRows = 0;
		if (value != null) {
			newRows = Integer.parseInt(value);
		}
		if (newRows > 0 && newRows <= 50) {
			numberOfRows = newRows;
		}
	}
	
	private void changePageIndex(String value) {
		if (value != null) {
			offset = (Integer.parseInt(value)-1) * numberOfRows;
		}
	}

}
