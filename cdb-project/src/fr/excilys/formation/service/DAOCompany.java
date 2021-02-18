package fr.excilys.formation.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.model.Company;

public class DAOCompany {
	private static DBConnection dbConnection;
	
	public DAOCompany() {
		dbConnection = DBConnection.getInstance();
	}

	public int numberOfCompanies() {
		int value = 0;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT COUNT(id) FROM company;";
			ResultSet result = connection.createStatement().executeQuery(query);
			result.next();
			
			value = result.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return value;
	}
	
	public List<Company> getAllCompanies(int offset, int numberOfRows) {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = dbConnection.openConnection()) {
			
			ResultSet result = connection.createStatement().executeQuery("SELECT name FROM company LIMIT " + offset + ", " + numberOfRows + ";");

			while (result.next()) {
				companies.add(new Company(result.getString("name")));
			}
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return companies;
	}
	
	public boolean companyExists(int id) {
		boolean returnValue = false;
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery("SELECT COUNT(id) FROM company WHERE id=" + id + ";");
			result.next();
			
			returnValue = (result.getInt(1) > 0) ? true : false;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return returnValue;
	}
}
