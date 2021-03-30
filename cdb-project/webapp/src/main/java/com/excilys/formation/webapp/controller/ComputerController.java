package com.excilys.formation.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.dto.mapper.ComputerDTOMapper;
import com.excilys.formation.dto.model.ComputerDTOViewAdd;
import com.excilys.formation.dto.model.ComputerDTOViewDashboard;
import com.excilys.formation.dto.model.ComputerDTOViewEdit;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.ListPage;
import com.excilys.formation.service.ComputerService;

@RestController
@RequestMapping("/computers")
public class ComputerController {

	ComputerService computerService;

	private ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}

	@GetMapping()
	public ResponseEntity<List<ComputerDTOViewDashboard>> getPage(Integer pageIndex, Integer numberOfValues, String search) {
		ListPage listPage = new ListPage.ListPageBuilder().index(pageIndex).numberOfValues(numberOfValues)
				.searchValue(search).build();

		return new ResponseEntity<> (computerService.getRangeServlet(listPage).stream().map(ComputerDTOMapper::computerToDTOViewDashboard).collect(Collectors.toList()), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ComputerDTOViewDashboard> getOne(@PathVariable("id") long id) {
		try {
			return new ResponseEntity<> (ComputerDTOMapper.computerToDTOViewDashboard(computerService.getByID(id).orElseThrow(Exception::new)), HttpStatus.OK);
		} catch (Exception exception) {
			return new ResponseEntity<> (null, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/count")
	public ResponseEntity<Long> getCount() {
		return new ResponseEntity<> (computerService.count(), HttpStatus.OK);
	}
	
	@DeleteMapping("/{id_list}")
	public ResponseEntity<Long> delete(@PathVariable("id_list") List<Long> id_list) {

		long result = computerService.delete(id_list);
		if (result > 0) {
			return new ResponseEntity<Long>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping()
	public ResponseEntity<Boolean> edit(@RequestBody ComputerDTOViewEdit computerDTO) {
		try {
			computerService.updateAllParameters(ComputerDTOMapper.dtoViewEditToComputer(computerDTO));
			return new ResponseEntity<> (true, HttpStatus.OK);
		} catch (ArgumentException exception) {
			return new ResponseEntity<> (false, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping()
	public ResponseEntity<Boolean> addComputerPost(@RequestBody ComputerDTOViewAdd computerDTO) {
		try {
			computerService.add(ComputerDTOMapper.dtoViewAddToComputer(computerDTO));
			return new ResponseEntity<> (true, HttpStatus.OK);
		} catch (ArgumentException exception) {
			return new ResponseEntity<> (false, HttpStatus.BAD_REQUEST);
		}
	}
}
