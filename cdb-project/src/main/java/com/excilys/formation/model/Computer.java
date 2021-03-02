package com.excilys.formation.model;

import java.time.LocalDate;

import com.excilys.formation.constants.GlobalConstants;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.validator.ComputerValidator;

public class Computer {
	private final int id;
	private final String name;
	private final Company company;
	private final LocalDate introduced, discontinued;
	
	private final static String printFormat = "%4s | %-40s| %-40s| %-14s| %-14s";
	public final static String HEADER = String.format(printFormat, "ID", "Nom", "Entreprise", "Introduced", "Discontinued");

	private Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.company = builder.company;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
	}
	
	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}

	public Company getCompany() {
		return company;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}
	
	@Override
	public String toString() {
		
		return String.format(printFormat, getID(), getName(), getCompany().getName(), 
					GlobalConstants.localDateToString(getIntroduced()),
					GlobalConstants.localDateToString(getDiscontinued()));
	}
	
	public static class ComputerBuilder {
		private final String name;
		private int id;
		private Company company;
		private LocalDate introduced, discontinued;
		
		public ComputerBuilder(String name) {
			this.name = name;
		}
		public ComputerBuilder id(int id) {
			this.id = id;
			return this;
		}
		public ComputerBuilder company(Company company) {
			this.company = company;
			return this;
		}
		public ComputerBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		public ComputerBuilder discontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		public Computer build() throws ArgumentException {
			Computer computer = new Computer(this);
			ComputerValidator.validComputer(computer);
			return computer;
		}
	}
}