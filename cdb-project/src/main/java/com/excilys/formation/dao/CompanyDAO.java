package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.model.Company;

public class CompanyDAO {
	private static DBConnection dbConnection = DBConnection.getInstance();
	private static final CompanyDAO daoCompanyInstance = new CompanyDAO();
	private static final String COUNT = "SELECT COUNT(id) FROM company;";
	private static final String GET_RANGE = "SELECT id, name FROM company LIMIT ?, ?;";
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String EXISTS = "SELECT COUNT(id) FROM company WHERE id=?;";
	
	public static CompanyDAO getInstance() {
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

	public List<Optional<Company>> getRange(int offset, int numberOfRows) throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		try (Connection connection = dbConnection.openConnection()) {

			PreparedStatement statement = connection.prepareStatement(GET_RANGE);
			statement.setInt(1, offset);
			statement.setInt(2, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				try {
					companies.add(Optional.ofNullable(new Company.CompanyBuilder()
														.id(result.getInt("id"))
														.name(result.getString("name"))
														.build()));
				} catch (ArgumentException exception) {
					companies.add(Optional.empty());
				}
			}

		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return companies;
	}

	public List<Optional<Company>> getAll() throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery(GET_ALL);
			
			while (result.next()) {
				try {
					companies.add(Optional.ofNullable(new Company.CompanyBuilder()
									.id(result.getInt("id"))
									.name(result.getString("name"))
									.build()));
				} catch(ArgumentException exception) {
					companies.add(Optional.empty());
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
