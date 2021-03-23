package com.excilys.formation.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.formation.constants.GlobalConstants;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.validator.ComputerValidator;

@Entity(name = "computer")
@Table(name = "computer")
public class Computer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;
	private LocalDate introduced, discontinued;
	
	private static final String PRINT_FORMAT = "%4s | %-40s| %-40s| %-14s| %-14s";
	public static final String HEADER = String.format(PRINT_FORMAT, "ID", "Nom", "Entreprise", "Introduced", "Discontinued");

	protected Computer() { }
	
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

	public long getId() {
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
	
	public String getCompanyName() {
		if (company != null) {
			return company.getName();
		} else {
			return null;
		}
	}
	
	@Override
	public String toString() {
		
		return String.format(PRINT_FORMAT, getId(), getName(), getCompanyName(), 
					GlobalConstants.localDateToString(getIntroduced()),
					GlobalConstants.localDateToString(getDiscontinued()));
	}
	
	public static class ComputerBuilder {
		private final String name;
		private long id;
		private Company company;
		private LocalDate introduced, discontinued;
		
		public ComputerBuilder(String name) {
			this.name = name;
		}
		public ComputerBuilder id(long id) {
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