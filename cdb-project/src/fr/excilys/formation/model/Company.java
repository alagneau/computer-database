package fr.excilys.formation.model;

public class Company {
	private String name;
	private int id;
	
	public Company(int id) {
		this.setID(id);
	}
	
	public Company(String name) {
		this.setName(name);
	}
	
	public Company(int id, String name) {
		this(id);
		this.name = name;
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

}
