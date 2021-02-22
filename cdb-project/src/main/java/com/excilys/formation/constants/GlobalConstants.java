package com.excilys.formation.constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GlobalConstants {
	public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static LocalDate StringToLocalDate(String input) {
		LocalDate date = null;
		if (input != null && input != "") {
			date = LocalDate.parse(input);
		}
		
		return date;
	}
}
