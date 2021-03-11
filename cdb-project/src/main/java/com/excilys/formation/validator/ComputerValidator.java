package com.excilys.formation.validator;

import java.time.LocalDate;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Computer;

public abstract class ComputerValidator {
	private ComputerValidator() {
	}
	
	public static void validComputer(Computer computer) throws ArgumentException {
		validDates(computer);
		validName(computer);
	}
	
	private static void validDates(Computer computer) throws ArgumentException {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		if (introduced != null && discontinued != null && discontinued.isBefore(introduced)) {
			throw new ArgumentException("Discontinued date is before introduced date.");
		}
	}
	
	private static void validName(Computer computer) throws ArgumentException {
		String name = computer.getName();
		if (name == null || name.isEmpty()) {
			throw new ArgumentException("Not a valid computer name : '" + name + "'");
		}
	}
}
