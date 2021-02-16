package fr.excilys.formation.model;

import java.util.Date;

public class Computer {
	private String name;
	Company company;
	private Date date;
	private int id;

	public Computer() {
		name = "toto";
	}

	public Computer(String name) {
		this.name = name;
	}
	
	public Computer(Date date) {
		this();
		this.date = date;
	}
	
	public Computer(int id, String name, Company company) {
		this(name);
		this.setCompany(company);
		this.setID(id);
	}

	public Computer(String name, Date date) {
		this(name);
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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
	
	public void equals() {
		
	}
}