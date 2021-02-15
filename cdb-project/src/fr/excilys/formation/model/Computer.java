package fr.excilys.formation.model;

import java.util.Date;

public class Computer {
	private String name;
	private Date date;

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
}