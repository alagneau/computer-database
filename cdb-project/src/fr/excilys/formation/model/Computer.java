package fr.excilys.formation.model;

import java.time.LocalDate;

public class Computer {
	private String name;
	Company company;
	private LocalDate introduced, discontinued;
	private int id;

	public Computer() {
		name = "toto";
	}
	public Computer(int id) {
		this.id = id;
	}

	public Computer(String name) {
		this.name = name;
	}
	
	public Computer(int id, String name, Company company) {
		this(name);
		setCompany(company);
		setID(id);
	}
	
	public Computer(int id, String name, Company company, LocalDate introduced, LocalDate discontinued) {
		this(id, name, company);
		setIntroduced(introduced);
		setDiscontinued(discontinued);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public boolean equals(Object other) {
		if (other == null) return false;
		if (this == other) return true;
		if (other instanceof Computer) {
			return ((Computer) other).equals(other);
		}
		return false;
	}
	
	public int hashcode() {
		return name.hashCode() * company.hashCode() * introduced.hashCode() * discontinued.hashCode();
	}
}