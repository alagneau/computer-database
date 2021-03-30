package com.excilys.formation.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.dto.mapper.CompanyDTOMapper;
import com.excilys.formation.dto.model.CompanyDTOView;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	CompanyService companyService;

	private CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping()
	public ResponseEntity<List<CompanyDTOView>> getPage(int pageIndex, int numberOfValues, String search) {
		ListPage listPage = new ListPage.ListPageBuilder().index(pageIndex).numberOfValues(numberOfValues)
				.searchValue(search).build();
		
		return new ResponseEntity<> (companyService.getRangeServlet(listPage)
						.stream().map(CompanyDTOMapper::companyToDTOView)
						.collect(Collectors.toList())
					, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_list}")
	public ResponseEntity<Boolean> delete(@PathVariable("id_list") List<Long> id_list) {

		for(long id : id_list) {
			companyService.delete(id);
		}
		return new ResponseEntity<> (true, HttpStatus.OK);
	}
}
