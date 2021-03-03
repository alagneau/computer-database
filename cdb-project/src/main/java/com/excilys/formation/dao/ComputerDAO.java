package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.exception.AddDataException;
import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.exception.DeletingDataException;
import com.excilys.formation.exception.ReadDataException;
import com.excilys.formation.exception.UpdatingDataException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class ComputerDAO {
	private static DBConnection dbConnection;
	private static ComputerDAO daoComputerInstance = new ComputerDAO();
	private final String NUMBER_OF_COMPUTER = "SELECT COUNT(id) FROM computer;",
						GET_RANGE = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as \"companyID\", company.name as \"companyName\" "
								+ "FROM computer LEFT JOIN company ON computer.company_id=company.id "
								+ "ORDER BY computer.id "
								+ "LIMIT ?, ?;",
						GET_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.id as \"companyID\", company.name as \"companyName\" "
								+ "FROM computer INNER JOIN company ON computer.company_id=company.id "
								+ "WHERE computer.id=?"
								+ ";",
						EXISTS = "SELECT COUNT(id) FROM computer WHERE id=?;",
						ADD_COMPUTER = "INSERT INTO computer(name, introduced, discontinued, company_id) " + "VALUES (?, ?, ?, ?);",
						UPDATE_NAME = "UPDATE computer SET name = ? WHERE id = ?;",
						UPDATE_COMPANY = "UPDATE computer SET company_id = ? WHERE id = ?;",
						UPDATE_INTRODUCED = "UPDATE computer SET introduced = ? WHERE id = ?;",
						UPDATE_DISCONTINUED = "UPDATE computer SET discontinued = ? WHERE id = ?;",
						DELETE = "DELETE FROM computer WHERE id = ?;";

	private ComputerDAO() {
		dbConnection = DBConnection.getInstance();
	}

	public static ComputerDAO getInstance() {
		return daoComputerInstance;
	}

	public int count() throws ReadDataException {
		int value = 0;
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery(NUMBER_OF_COMPUTER);
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return value;
	}

	public List<Optional<Computer>> getRange(int offset, int numberOfRows) throws ReadDataException, ArgumentException {
		List<Optional<Computer>> computers = new ArrayList<>();
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_RANGE);
			statement.setInt(1, offset);
			statement.setInt(2, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				Optional<Computer> computer = Optional.ofNullable(new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
						.company(new Company.CompanyBuilder().id(result.getInt("companyID"))
								.name(result.getString("companyName"))
								.build())
						.introduced(dateToLocalDate(result.getDate("introduced")))
						.discontinued(dateToLocalDate(result.getDate("discontinued")))
						.build());
				computers.add(computer);
			}

		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return computers;
	}

	public Optional<Computer> getByID(int id) throws ReadDataException, ArgumentException {
		Optional<Computer> computer = null;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				computer = Optional.ofNullable(new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
						.company(new Company.CompanyBuilder().id(result.getInt("companyID"))
								.name(result.getString("companyName"))
								.build())
						.introduced(dateToLocalDate(result.getDate("introduced")))
						.discontinued(dateToLocalDate(result.getDate("introduced")))
						.build());
			}
		} catch (SQLException sqlException) {
			throw new ReadDataException(sqlException.getMessage());
		}
		return computer;
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

	public int add(Computer computer) throws AddDataException {
		int newID = 0;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_COMPUTER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, computer.getName());
			statement.setDate(2, localDateToDate(computer.getIntroduced()));
			statement.setDate(3, localDateToDate(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany().getID());

			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			result.next();

			newID = result.getInt(1);
		} catch (SQLException sqlException) {
			throw new AddDataException(sqlException.getMessage());
		}

		return newID;
	}

	public void updateName(Computer computer, String name) throws UpdatingDataException {
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_NAME);
			statement.setString(1, name);
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
	}

	public void updateCompany(Computer computer, int companyID) throws ArgumentException, UpdatingDataException {
		if (companyID <= 0) {
			throw new ArgumentException("Invalid company ID : " + companyID);
		}
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
			statement.setInt(1, companyID);
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
	}

	public void updateIntroduced(Computer computer, LocalDate introduced) throws UpdatingDataException {
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_INTRODUCED);
			statement.setDate(1, localDateToDate(introduced));
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
	}

	public void updateDiscontinued(Computer computer, LocalDate discontinued) throws UpdatingDataException {
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_DISCONTINUED);
			statement.setDate(1, localDateToDate(discontinued));
			statement.setInt(2, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new UpdatingDataException(sqlException.getMessage());
		}
	}

	public void delete(Computer computer) throws DeletingDataException {
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE);
			statement.setInt(1, computer.getID());

			statement.executeUpdate();
		} catch (SQLException sqlException) {
			throw new DeletingDataException(sqlException.getMessage());
		}
	}

	private LocalDate dateToLocalDate(Date date) {
		return (date != null) ? date.toLocalDate() : null;
	}

	private Date localDateToDate(LocalDate localDate) {
		return (localDate != null) ? Date.valueOf(localDate) : null;
	}
}
