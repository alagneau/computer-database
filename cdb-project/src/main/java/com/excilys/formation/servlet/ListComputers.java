package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.ComputerService;

public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private static Logger logger = LoggerFactory.getLogger(ListComputers.class);

	public ListComputers() {
		super();
		computerService = ComputerService.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		ListPage<Optional<Computer>> listPage = getPage(session);
		try {
			int maxComputers = computerService.count();
			int numberOfValues = getIntParameter(request.getParameter("numberOfValues"));
			listPage.setMaxComputers(maxComputers);
	
			listPage.changePage(getIntParameter(request.getParameter("pageIndex")));
			listPage.changeNumberOfValues(numberOfValues);
	
			listPage.setValues(computerService.getRange(listPage.getOffset(), listPage.getNumberOfValues()));

		List<ComputerDTOViewDashboard> listOfComputers = new ArrayList<>();
		for (Optional<Computer> computer : listPage.getValues()) {
			ComputerDTOViewDashboard computerDTO;
			if (computer.isPresent()) {
				computerDTO = ComputerDTOMapper.computerToDTOViewDashboard(computer.get());
			} else {
				computerDTO = new ComputerDTOViewDashboard();
			}
			listOfComputers.add(computerDTO);
		}

		request.setAttribute("listComputers", listOfComputers);
		request.setAttribute("maxComputers", maxComputers);
		request.setAttribute("numberOfValues", numberOfValues);
		request.setAttribute("pageIndex", listPage.getIndex());
		request.setAttribute("maxPage", listPage.getMaxPageValue());
		
		} catch(DatabaseAccessException | ArgumentException exception) {
			logger.info(exception.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	private ListPage<Optional<Computer>> getPage(HttpSession session) {
		ListPage<Optional<Computer>> listPage = null;
		try {
			listPage = (ListPage<Optional<Computer>>) session.getAttribute("listPage");
		} catch (ClassCastException exception) {
			logger.error(exception.getMessage());
		}
		if (listPage == null) {
			listPage = new ListPage.ListPageBuilder<Optional<Computer>>().index(1).numberOfValues(10).build();
			session.setAttribute("listPage", listPage);
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
