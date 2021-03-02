package com.excilys.formation.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.model.Company;

public class DAOCompany {
	private static DBConnection dbConnection;
	private static final DAOCompany daoCompanyInstance = new DAOCompany();
	private final String COUNT = "SELECT COUNT(id) FROM company;",
						GET_RANGE = "SELECT id, name FROM company LIMIT ?, ?;",
						GET_ALL = "SELECT id, name FROM company;",
						EXISTS = "SELECT COUNT(id) FROM company WHERE id=?;";

	private DAOCompany() {
		dbConnection = DBConnection.getInstance();
	}
	
	public static DAOCompany getInstance() {
		return daoCompanyInstance;
	}

	public int count() throws ReadDataException {
		int value = 0;
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery(COUNT);
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return value;
	}

	public List<Company> getRange(int offset, int numberOfRows) throws ReadDataException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = dbConnection.openConnection()) {

			PreparedStatement statement = connection.prepareStatement(GET_RANGE);
			statement.setInt(1, offset);
			statement.setInt(2, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				try {
					companies.add(new Company.CompanyBuilder().id(result.getInt("id")).name(result.getString("name"))
											.build());
				} catch (ArgumentException exception) {
					System.out.println(exception.getMessage());
				}
			}

		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return companies;
	}

	public List<Company> getAll() throws ReadDataException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery(GET_ALL);
			
			while (result.next()) {
				try {
					companies.add(new Company.CompanyBuilder().id(result.getInt("id")).name(result.getString("name"))
											.build());
				} catch (ArgumentException exception) {
					System.out.println(exception.getMessage());
				}
			}
			
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return companies;
	}

	public boolean exists(int id) throws ReadDataException {
		boolean returnValue = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(EXISTS);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			result.next();

			returnValue = (result.getInt(1) > 0) ? true : false;
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return returnValue;
	}
}
