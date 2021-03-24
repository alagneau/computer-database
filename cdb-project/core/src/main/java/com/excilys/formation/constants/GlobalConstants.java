package com.excilys.formation.constants;

import java.time.LocalDate;

public class GlobalConstants {
	//public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static LocalDate stringToLocalDate(String input) {
		LocalDate date = null;
		if (input != null && input != "") {
			date = LocalDate.parse(input);
		}
		
		return date;
	}
	
	public static String localDateToString(LocalDate localDate) {
		if (localDate != null) {
			//return localDate.format(DATE_FORMAT);
			return localDate.toString();
		} else {
			return "none";
		}
	}
}
