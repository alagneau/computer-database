package com.excilys.formation.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.excilys.formation.exception.ArgumentException;

@Entity
@Table(name = "company")
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;

	protected Company() {
	}

	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public static class CompanyBuilder {
		private long id;
		private String name;

		public CompanyBuilder() {
		}

		public CompanyBuilder id(long id) {
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		CompanyBuilder other = (CompanyBuilder) obj;
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
