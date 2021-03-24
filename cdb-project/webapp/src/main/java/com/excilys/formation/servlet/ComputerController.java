package com.excilys.formation.servlet;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewEdit;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.CompanyService;
import com.excilys.formation.service.ComputerService;

@Controller
public class ComputerController {
	private static CDBLogger logger = new CDBLogger(ComputerController.class);

	ComputerService computerService;
	CompanyService companyService;
	DashboardParameters dashboardParams;
	EditComputerParameters editComputerParameters;
	AddComputerParameters addComputerParameters;

	private ComputerController(ComputerService computerService, CompanyService companyService,
			DashboardParameters dashboardParams, AddComputerParameters addComputerParameters,
			EditComputerParameters editComputerParameters) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.dashboardParams = dashboardParams;
		this.editComputerParameters = editComputerParameters;
		this.addComputerParameters = addComputerParameters;
	}

	@GetMapping("/dashboard")
	public ModelAndView dashboardGet(@RequestParam(required = false) Integer pageIndex,
			@RequestParam(required = false) Integer numberOfValues, @RequestParam(required = false) String search,
			@RequestParam(required = false) ListPage.OrderByValues orderByValue) {
		readDashboardParams(pageIndex, numberOfValues, search, orderByValue);
		getDashboardValuesFromDAO();

		return dashboardParams.getModelAndView();
	}

	private void getDashboardValuesFromDAO() {
		dashboardParams.setMaxComputers(computerService.filterAndCount(dashboardParams.getSearchValue()));
		try {
			dashboardParams.setValues(computerService.getRangeServlet(dashboardParams.getPage()));
		} catch (ArgumentException exception) {
			logger.info(exception.getMessage());
		}
	}

	private void readDashboardParams(Integer pageIndex, Integer numberOfValues, String search,
			ListPage.OrderByValues orderByValue) {
		if (pageIndex != null) {
			dashboardParams.setPageIndex(pageIndex);
		}
		if (numberOfValues != null) {
			dashboardParams.setNumberOfValues(numberOfValues);
		}
		if (search != null) {
			dashboardParams.setSearchValue(search);
		}
		if (orderByValue != null) {
			dashboardParams.setOrderByValue(orderByValue);
		}
	}

	@PostMapping("/dashboard")
	public ModelAndView dashboardPost(@RequestParam(required = false) String selection) {
		if (selection != null) {
			Arrays.asList(selection.split(",")).stream()
				.map(value -> Integer.parseInt(value))
				.forEach(value -> {
					computerService.delete(value);
				});
		}
		getDashboardValuesFromDAO();

		return dashboardParams.getModelAndView();
	}

	@GetMapping("/editComputer")
	public ModelAndView editComputerGet(@RequestParam(required = false) Integer computerId) {
		if (computerId != null) {
			editComputerParameters.setComputer(
					ComputerDTOMapper.computerToDTOViewEdit(computerService.getByID(computerId).orElse(null)));
		}
		editComputerParameters.setCompanyList(CompanyDTOMapper.companyListToDTOListViewAdd(companyService.getAll()));
		return editComputerParameters.getModelAndView();
	}

	@PostMapping("/editComputer")
	public ModelAndView editComputerPost(String computerId, String computerName, String introduced, String discontinued,
			String companyId) {
		ComputerDTOViewEdit computerDTO = new ComputerDTOViewEdit();
		computerDTO.id = computerId;
		computerDTO.name = computerName;
		computerDTO.introduced = introduced;
		computerDTO.discontinued = discontinued;
		computerDTO.companyID = companyId;
		editComputerParameters.setComputer(computerDTO);
		try {
			computerService.updateAllParameters(ComputerDTOMapper.dtoViewEditToComputer(computerDTO));
		} catch (ArgumentException exception) {
			logger.info(exception.getMessage());
		}
		return editComputerParameters.getModelAndView();
	}

	@GetMapping("/addComputer")
	public ModelAndView addComputerGet() {
		addComputerParameters.setCompanyList(CompanyDTOMapper.companyListToDTOListViewAdd(companyService.getAll()));
		return addComputerParameters.getModelAndView();
	}

	@PostMapping("/addComputer")
	public ModelAndView addComputerPost(String computerId, String computerName, String introduced, String discontinued,
			String companyId) {
		ComputerDTOViewAdd computerDTO = new ComputerDTOViewAdd();
		computerDTO.name = computerName;
		computerDTO.introduced = introduced;
		computerDTO.discontinued = discontinued;
		computerDTO.companyID = companyId;
		addComputerParameters.setComputer(computerDTO);
		try {
			computerService.add(ComputerDTOMapper.dtoViewAddToComputer(computerDTO));
		} catch (ArgumentException exception) {
			logger.info(exception.getMessage());
		}
		return addComputerParameters.getModelAndView();
	}
}
