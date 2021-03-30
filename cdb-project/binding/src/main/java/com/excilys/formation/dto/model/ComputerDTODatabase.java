package com.excilys.formation.dto.model;

import java.time.LocalDate;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerDTODatabase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	@OneToOne
	private CompanyDTODatabase company;
	
	
	public ComputerDTODatabase() {
	}
	
	public ComputerDTODatabase(long id, String name, LocalDate introduced, LocalDate discontinued, CompanyDTODatabase company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getIntroduced() {
		return this.introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public LocalDate getDiscontinued() {
		return this.discontinued;
	}

	public void setCompany(CompanyDTODatabase company) {
		this.company = company;
	}

	public CompanyDTODatabase getCompany() {
		return this.company;
	}

	public String toString() {
		return "Name : " + this.getName() + ", Date introduce : "
				+ (Optional.ofNullable(this.introduced).isPresent() ? this.introduced : null) + ", Date discontinued : "
				+ (Optional.ofNullable(this.discontinued).isPresent() ? this.discontinued : null) + ", Company name : "
				+ (Optional.ofNullable(this.company).isPresent() ? this.company.getName() : null);
	}
}