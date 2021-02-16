package fr.excilys.formation.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.model.Company;

public class DAOCompany {
	private static DBConnection daoconnection;
	
	public DAOCompany() {
		daoconnection = DBConnection.getInstance();
	}
	
	public List<Company> getAllCompanies(int offset, int numberOfRows) {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = daoconnection.openConnection()) {
			
			ResultSet result = connection.createStatement().executeQuery("SELECT name FROM company LIMIT " + offset + ", " + numberOfRows + ";");

			while (result.next()) {
				companies.add(new Company(result.getString("name")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companies;
	}
}
