package com.excilys.formation.dto.validator;

import java.time.LocalDate;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.InvalidDateException;

public class ComputerDTOValidator {
	
	public static void validName(String name) throws ArgumentException {
		if (name == null || name.isEmpty()) {
			throw new ArgumentException("Not a valid name : '" + name + "'");
		}
	}
	
	public static void validIntroduced(String date) throws ArgumentException {
		validDate(date);
	}
	
	public static void validDiscontinued(String date) throws ArgumentException {
		validDate(date);
	}
	
	public static void validDates(String introduced, String discontinued) throws ArgumentException {
		validIntroduced(introduced);
		validDiscontinued(discontinued);
		if (introduced != null && discontinued != null) {
			if (LocalDate.parse(discontinued).isBefore(LocalDate.parse(introduced))) {
				throw new ArgumentException("Discontinued date is before introduced date.");
			}
		}
	}
	
	private static void validDate(String date) throws ArgumentException {
		if(date != null) {
			try {
				LocalDate.parse(date);
			} catch (Exception exception) {
				throw new InvalidDateException();
			}
		}
	}
}
