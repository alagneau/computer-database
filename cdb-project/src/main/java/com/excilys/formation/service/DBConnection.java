package com.excilys.formation.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static DBConnection dbconnection;
	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost/computer-database-db", username = "root", password = "tapaqual";

	private DBConnection() {}

	public static DBConnection getInstance() {
		if (dbconnection == null) {
			dbconnection = new DBConnection();
		}
		return dbconnection;
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
