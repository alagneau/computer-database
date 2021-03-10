package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.logger.CDBLogger;
import com.excilys.formation.model.Company;

import com.excilys.formation.exception.DatabaseAccessException;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyDAO {
	@Autowired
	private DataSource dataSource;
	private CDBLogger logger = new CDBLogger(CompanyDAO.class);
	private static final String COUNT = "SELECT COUNT(id) FROM company;";
	private static final String GET_BY_ID = "SELECT id, name FROM company WHERE id=?;";
	private static final String GET_RANGE = "SELECT id, name FROM company LIMIT ?, ?;";
	private static final String GET_ALL = "SELECT id, name FROM company;";
	private static final String EXISTS = "SELECT COUNT(id) FROM company WHERE id=?;";
	private static final String DELETE_COMPUTER_WITH_COMPANY_ID = "DELETE FROM computer WHERE company_id=?;";
	private static final String DELETE_COMPANY_WITH_ID = "DELETE FROM company WHERE id=?;";

	public int count() throws ReadDataException {
		int value = 0;
		try (Connection connection = dataSource.getConnection()) {
			ResultSet result = connection.createStatement().executeQuery(COUNT);
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return value;
	}

	public Optional<Company> getByID(int id) throws ReadDataException, ArgumentException {
		Optional<Company> company = Optional.empty();
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				company = Optional.ofNullable(new Company.CompanyBuilder().id(id).name(result.getString("name")).build());
			}
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return company;
	}
	
	public List<Optional<Company>> getRange(int offset, int numberOfRows) throws ReadDataException {
		List<Optional<Company>> companies = new ArrayList<>();
		try (Connection connection = dataSource.getConnection()) {

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
		try (Connection connection = dataSource.getConnection()) {
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
		try (Connection connection = dataSource.getConnection()) {
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
	
	public void delete(int id) throws DatabaseAccessException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement deleteComputersStatement = connection.prepareStatement(DELETE_COMPUTER_WITH_COMPANY_ID);
			deleteComputersStatement.setInt(1, id);
			deleteComputersStatement.executeUpdate();
			PreparedStatement deleteCompanyStatement = connection.prepareStatement(DELETE_COMPANY_WITH_ID);
			deleteCompanyStatement.setInt(1, id);
			int res = deleteCompanyStatement.executeUpdate();
			
			if (res<1) {
				throw new DatabaseAccessException("The company with the ID " + id + " is not listed in the database");
			}
			
			connection.commit();
		} catch (SQLException exception) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException exceptionRollback) {
				logger.info(exceptionRollback.getMessage());	
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException exceptionRollback) {
				logger.info(exceptionRollback.getMessage());	
			}
		}
	}
}
