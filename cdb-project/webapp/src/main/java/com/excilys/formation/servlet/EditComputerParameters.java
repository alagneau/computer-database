package com.excilys.formation.servlet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.model.CompanyDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewEdit;

@Component
@SessionScope
public class EditComputerParameters {
	private ModelAndView modelAndView;
	private ComputerDTOViewEdit computer;
	private List<CompanyDTOViewAdd> companyList;
	
	public EditComputerParameters() {
		this.modelAndView = new ModelAndView("editComputer");
		computer = null;
		companyList = new ArrayList<>();
	}

	public ComputerDTOViewEdit getComputer() {
		return computer;
	}

	public void setComputer(ComputerDTOViewEdit computer) {
		this.computer = computer;
	}
	
	public List<CompanyDTOViewAdd> getCompanyList() {
		return companyList;
	}
	
	public void setCompanyList(List<CompanyDTOViewAdd> companyList) {
		this.companyList = companyList;
	}

	public ModelAndView getModelAndView() {
		modelAndView.addObject("computer", computer);
		modelAndView.addObject("companyList", companyList);
		return modelAndView;
	}
}
