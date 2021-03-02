package com.excilys.formation.model;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.validator.CompanyValidator;

public class Company {
	private final int id;
	private final String name;
	
	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}

	public int getID() {
		return id;
	}
	
	public static class CompanyBuilder{
		private int id;
		private String name;
		
		public CompanyBuilder() {
		}
		
		public CompanyBuilder id(int id) {
			this.id = id;
			return this;
		}
		public CompanyBuilder name(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() throws ArgumentException {
			Company company = new Company(this);
			//CompanyValidator.validCompany(company);
			return company;
		}
	}

}
