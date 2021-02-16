package fr.excilys.formation.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static DBConnection daoconnection;
	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost/computer-database-db", username = "root", password = "tapaqual";

	private DBConnection() {}

	public static DBConnection getInstance() {
		if (daoconnection == null) {
			daoconnection = new DBConnection();
		}
		return daoconnection;
	}

	public Connection openConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, username, password);
		} catch (SQLException | ClassNotFoundException exception) {
			exception.printStackTrace();
		}
		return connection;
	}
}
