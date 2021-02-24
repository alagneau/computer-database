package com.excilys.formation.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.exception.ArgumentException;
import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class DAOComputer {
	private static DBConnection dbConnection;
	private static DAOComputer daoComputer;
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

	public DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}

	public static DAOComputer getInstance() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	public int count() {
		int value = 0;
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement().executeQuery(NUMBER_OF_COMPUTER);
			result.next();

			value = result.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return value;
	}

	public List<Computer> getRange(int offset, int numberOfRows) {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_RANGE);
			statement.setInt(1, offset);
			statement.setInt(2, numberOfRows);
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				try {
					Computer computer = new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
							.company(new Company.CompanyBuilder(result.getInt("companyID"))
									.name(result.getString("companyName"))
									.build())
							.introduced(dateToLocalDate(result.getDate("introduced")))
							.discontinued(dateToLocalDate(result.getDate("introduced")))
							.build();
					computers.add(computer);
				} catch (ArgumentException exception) {
					System.out.println(exception.getMessage());
				}
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computers;
	}

	public Computer getByID(int id) {
		Computer computer = null;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(GET_BY_ID);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();

			if (result.next()) {
				
				try {
					computer = new Computer.ComputerBuilder(result.getString("name")).id(result.getInt("id"))
							.company(new Company.CompanyBuilder(result.getInt("companyID"))
									.name(result.getString("companyName"))
									.build())
							.introduced(dateToLocalDate(result.getDate("introduced")))
							.discontinued(dateToLocalDate(result.getDate("introduced")))
							.build();
				} catch(ArgumentException exception) {
					System.out.println(exception.getMessage());
				}
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computer;
	}

	public boolean exists(int id) {
		boolean returnValue = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(EXISTS);
			statement.setInt(1, id);
			ResultSet result = statement.executeQuery();
			
			result.next();

			returnValue = (result.getInt(1) > 0) ? true : false;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return returnValue;
	}

	public int add(Computer computer) {
		int status = 0;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(ADD_COMPUTER, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, computer.getName());
			statement.setDate(2, localDateToDate(computer.getIntroduced()));
			statement.setDate(3, localDateToDate(computer.getDiscontinued()));
			statement.setInt(4, computer.getCompany().getID());

			System.out.println("company id = " + computer.getCompany().getID());
			statement.executeUpdate();
			ResultSet result = statement.getGeneratedKeys();
			result.next();

			status = result.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return status;
	}

	public boolean updateName(int computerID, String name) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_NAME);
			statement.setString(1, name);
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean updateCompany(int computerID, int companyID) {
		boolean status = false;
		if (companyID <= 0)
			return false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_COMPANY);
			statement.setInt(1, companyID);
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean updateIntroduced(int computerID, LocalDate introduced) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_INTRODUCED);
			statement.setDate(1, localDateToDate(introduced));
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean updateDiscontinued(int computerID, LocalDate discontinued) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(UPDATE_DISCONTINUED);
			statement.setDate(1, localDateToDate(discontinued));
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean delete(int id) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			PreparedStatement statement = connection.prepareStatement(DELETE);
			statement.setInt(1, id);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	private LocalDate dateToLocalDate(Date date) {
		return (date != null) ? date.toLocalDate() : null;
	}

	private Date localDateToDate(LocalDate localDate) {
		return (localDate != null) ? Date.valueOf(localDate) : null;
	}
}
