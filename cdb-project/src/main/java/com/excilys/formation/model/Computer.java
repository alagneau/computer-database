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
	
	private static final String PRINT_FORMAT = "%4s | %-40s| %-40s| %-14s| %-14s";
	public static final String HEADER = String.format(PRINT_FORMAT, "ID", "Nom", "Entreprise", "Introduced", "Discontinued");

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

	public int getId() {
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
	
	public Integer getCompanyId() {
		Integer res = null;
		if (company != null && company.getID() > 0) {
			res = company.getID();
		}
		return res;
	}
	
	@Override
	public String toString() {
		
		return String.format(PRINT_FORMAT, getId(), getName(), getCompany().getName(), 
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