package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DatabaseAccessException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Computer;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.ComputerService;

@WebServlet("/dashboard")
public class ListComputers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private ComputerService computerService;
	private static CDBLogger logger = new CDBLogger(ListComputers.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		ListPage<Optional<Computer>> listPage = getPage(session);
		try {
			listPage.changeSearchValue(request.getParameter("search"));
			String searchValue = listPage.getSearchValue();
			
			String orderValue = request.getParameter("orderByValue");
			try {
				if (orderValue != null) {
					listPage.changeOrderByValue(ListPage.OrderByValues.valueOf(orderValue));
				}
			} catch (IllegalArgumentException exception) {
				logger.info("Invalid value of ORDER_BY_VALUES : " + orderValue);
			}
			
			
			int maxComputers = computerService.filterAndCount(searchValue);
			int numberOfValues = getIntParameter(request.getParameter("numberOfValues"));
			listPage.setMaxComputers(maxComputers);

			listPage.changePage(getIntParameter(request.getParameter("pageIndex")));
			listPage.changeNumberOfValues(numberOfValues);

			listPage.setValues(computerService.getRangeServlet(listPage.getOffset(), listPage.getNumberOfValues(),
													searchValue, listPage.getOrderByValue().getRequest(),
													listPage.getOrderByDirection().getRequest()));

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
			request.setAttribute("ORDER_BY_VALUES", ListPage.OrderByValues.values());

		} catch (DatabaseAccessException | ArgumentException exception) {
			logger.info(exception.getMessage());
		}

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String listOfDeletion = request.getParameter("selection");

		if (!listOfDeletion.isEmpty()) {
			Arrays.asList(listOfDeletion.split(",")).stream().map(value -> Integer.parseInt(value)).forEach(value -> {

				try {
					computerService.delete(value);
				} catch (DeletingDataException exception) {
					logger.info(exception.getMessage());
				}
			});
		}

		doGet(request, response);
	}

	@SuppressWarnings("unchecked")
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
