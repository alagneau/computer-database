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

import com.excilys.formation.model.Company;
import com.excilys.formation.model.Computer;

public class DAOComputer {
	private static DBConnection dbConnection;
	private static DAOComputer daoComputer;

	public DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}
	
	public static DAOComputer getInstance() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	public int numberOfComputers() {
		int value = 0;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT COUNT(id) FROM computer;";
			ResultSet result = connection.createStatement().executeQuery(query);
			result.next();
			
			value = result.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return value;
	}

	public List<Computer> getComputers(int offset, int numberOfRows) {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT computer.id, computer.name, company.name as etp, computer.introduced, computer.discontinued "
					+ "FROM computer LEFT JOIN company ON computer.company_id=company.id " + "ORDER BY computer.id "
					+ "LIMIT " + offset + ", " + numberOfRows + ";";
			ResultSet result = connection.createStatement().executeQuery(query);

			while (result.next()) {
				Computer computer = new Computer(result.getInt("id"), result.getString("name"),
						new Company(result.getString("etp")),
						dateToLocalDate(result.getDate("introduced")),
						dateToLocalDate(result.getDate("discontinued")));
				computers.add(computer);
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computers;
	}

	public Computer getComputerByID(int id) {
		Computer computer = null;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name AS etp "
					+ "FROM computer INNER JOIN company ON computer.company_id=company.id " + "WHERE computer.id=" + id
					+ ";";
			ResultSet result = connection.createStatement().executeQuery(query);

			if (result.next()) {
				LocalDate introduced = dateToLocalDate(result.getDate("introduced"));
				LocalDate discontinued = dateToLocalDate(result.getDate("discontinued"));
				computer = new Computer(result.getInt("id"), result.getString("name"),
						new Company(result.getString("etp")), introduced, discontinued);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computer;
	}

	public boolean computerExists(int id) {
		boolean returnValue = false;
		try (Connection connection = dbConnection.openConnection()) {
			ResultSet result = connection.createStatement()
					.executeQuery("SELECT COUNT(id) FROM computer WHERE id=" + id + ";");
			result.next();

			returnValue = (result.getInt(1) > 0) ? true : false;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return returnValue;
	}

	public int addComputer(Computer computer) {
		int status = 0;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "INSERT INTO computer(name, introduced, discontinued, company_id) " + "VALUES (?, ?, ?, ?);";

			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
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

	public boolean changeComputerName(int computerID, String name) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "UPDATE computer SET name = ? WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean changeComputerCompany(int computerID, int companyID) {
		boolean status = false;
		if (companyID <= 0)
			return false;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "UPDATE computer SET company_id = ? WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, companyID);
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean changeComputerIntroduced(int computerID, LocalDate introduced) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "UPDATE computer SET introduced = ? WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, localDateToDate(introduced));
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean changeComputerDiscontinued(int computerID, LocalDate discontinued) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "UPDATE computer SET discontinued = ? WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setDate(1, localDateToDate(discontinued));
			statement.setInt(2, computerID);

			statement.executeUpdate();
			status = true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return status;
	}

	public boolean deleteComputer(int id) {
		boolean status = false;
		try (Connection connection = dbConnection.openConnection()) {
			String query = "DELETE FROM computer WHERE id = ?;";
			PreparedStatement statement = connection.prepareStatement(query);
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
