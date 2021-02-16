package fr.excilys.formation.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.excilys.formation.model.Company;
import fr.excilys.formation.model.Computer;

public class DAOComputer {
	private static DBConnection dbConnection;

	public DAOComputer() {
		dbConnection = DBConnection.getInstance();
	}

	public List<Computer> getAllComputers(int offset, int numberOfRows) {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT computer.id, computer.name, company.name as etp "
					+ "FROM computer INNER JOIN company ON computer.company_id=company.id " + "ORDER BY computer.id "
					+ "LIMIT " + offset + ", " + numberOfRows + ";";
			ResultSet result = connection.createStatement().executeQuery(query);

			while (result.next()) {
				Computer computer = new Computer(result.getInt("id"), result.getString("name"),
						new Company(result.getString("etp")));
				computers.add(computer);
			}

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computers;
	}

	public Computer getComputerByID(int id) {
		Computer computer = new Computer();
		try (Connection connection = dbConnection.openConnection()) {
			String query = "SELECT computer.name, computer.introduced, computer.discontinued, company.name AS etp" +
						"FROM computer INNER JOIN company ON computer.company_id=company.id " + 
						"WHERE computer.id = " + id + ";";
			ResultSet result = connection.createStatement().executeQuery(query);
			System.out.println(result.getString("name"));
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		return computer;
	}
}
