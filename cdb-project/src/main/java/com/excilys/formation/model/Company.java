package com.excilys.formation.model;

import com.excilys.formation.exception.ArgumentException;

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

	public static class CompanyBuilder {
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
			// CompanyValidator.validCompany(company);
			return company;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Company other = (Company) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
