package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.formation.logger.CDBLogger;

public class DBConnection {
	private static DBConnection dbconnection;
	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost/computer-database-db";
	private static final String username = "root";
	private static final String password = "tapaqual";
	private static final CDBLogger logger = new CDBLogger(DBConnection.class);

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
			logger.info("Erreur lors de la connection à la BDD");
			logger.info(exception.getMessage());
		}
		return connection;
	}
}
