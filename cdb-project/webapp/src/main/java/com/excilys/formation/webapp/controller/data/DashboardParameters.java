package com.excilys.formation.webapp.controller.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.model.ListPage;

@Component
@SessionScope
public class DashboardParameters {
	private ListPage listPage;
	private ModelAndView modelAndView;
	private List<ComputerDTOViewDashboard> listComputers = new ArrayList<>();
	
	public DashboardParameters() {
		listPage = new ListPage.ListPageBuilder().index(1).numberOfValues(10).build();
		modelAndView = new ModelAndView("dashboard");
	}
	
	public void setPageIndex(int pageIndex) {
		listPage.setPageIndex(pageIndex);
	}
	
	public void setNumberOfValues(int numberOfValues) {
		listPage.setNumberOfValues(numberOfValues);
	}
	
	public void setSearchValue(String search) {
		listPage.setSearchValue(search);
	}
	/*
	public void setValues(List<ComputerDatabaseDTO> values) throws ArgumentException {
		listComputers = new ArrayList<>();
		for (ComputerDatabaseDTO computer : values) {
			ComputerDTOViewDashboard computerDTO;
			computerDTO = ComputerDTOMapper.computerToDTOViewDashboard(computer);
			listComputers.add(computerDTO);
		}
	}*/
	
	public void setMaxComputers(long value) {
		listPage.setMaxComputers(value);
	}
	
	public void setOrderByValue(ListPage.OrderByValues value) {
		listPage.setOrderByValue(value);
	}
	
	public ListPage getPage() {
		return listPage;
	}
	
	public String getSearchValue() {
		return listPage.getSearchValue();
	}
	
	public ModelAndView getModelAndView() {
		modelAndView.addObject("pageIndex", listPage.getIndex());
		modelAndView.addObject("numberOfValues", listPage.getNumberOfValues());
		modelAndView.addObject("maxComputers", listPage.getMaxComputers());
		modelAndView.addObject("listComputers", listComputers);
		modelAndView.addObject("maxPage", listPage.getMaxPageValue());
		modelAndView.addObject("ORDER_BY_VALUES", ListPage.OrderByValues.values());
		return modelAndView;
	}
}
