package com.excilys.formation.webapp.controller.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.model.CompanyDTOView;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;


@Component
@SessionScope
public class AddComputerParameters {
	private ModelAndView modelAndView;
	private ComputerDTOViewAdd computer;
	private List<CompanyDTOView> companyList;
	
	public AddComputerParameters() {
		this.modelAndView = new ModelAndView("addComputer");
		this.computer = null;
		this.companyList = new ArrayList<>();
	}

	public ComputerDTOViewAdd getComputer() {
		return computer;
	}

	public void setComputer(ComputerDTOViewAdd computer) {
		this.computer = computer;
	}
	
	public List<CompanyDTOView> getCompanyList() {
		return companyList;
	}
	
	public void setCompanyList(List<CompanyDTOView> companyList) {
		this.companyList = companyList;
	}

	public ModelAndView getModelAndView() {
		modelAndView.addObject("companyList", companyList);
		return modelAndView;
	}
}