package com.excilys.formation.servlet;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewEdit;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Computer;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerService;
	private CompanyService companyService;
	private static CDBLogger logger = new CDBLogger(EditComputer.class);
		
	public EditComputer() {
		computerService = ComputerService.getInstance();
		companyService = CompanyService.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Optional<Computer> computer = computerService.getByID(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("computer", ComputerDTOMapper.computerToDTOViewEdit(computer.get()));
			request.setAttribute("companyList", CompanyDTOMapper.companyListToDTOListViewAdd(companyService.getAll()));
			
		} catch (ReadDataException | ArgumentException exception) {
			logger.info(exception.getMessage());
		} catch (NumberFormatException exception) {
			logger.info("invalid computer ID : '" + request.getParameter("id") + "'");
		}
		request.setAttribute("id", request.getParameter("id"));
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ComputerDTOViewEdit computerDTO = new ComputerDTOViewEdit();
			computerDTO.id = request.getParameter("id");
			computerDTO.name = request.getParameter("computerName");
			computerDTO.introduced = request.getParameter("introduced");
			computerDTO.discontinued = request.getParameter("discontinued");
			computerDTO.companyID = request.getParameter("companyId");
			Computer computer = ComputerDTOMapper.dtoViewEditToComputer(computerDTO);
			computerService.updateAllParameters(computer);
			
			request.setAttribute("id", computerDTO.id);
		} catch(ArgumentException | UpdatingDataException exception) {
			logger.info(exception.getMessage());
		}
		
		doGet(request, response);
	}
}
