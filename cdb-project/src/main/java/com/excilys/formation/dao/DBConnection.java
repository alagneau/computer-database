package com.excilys.formation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.formation.logger.CDBLogger;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DBConnection {
	private static final String URL = "jdbc:mysql://localhost/computer-database-db";
	private static final String username = "root";
	private static final String password = "tapaqual";
	private static final CDBLogger logger = new CDBLogger(DBConnection.class);

	public Connection openConnection() {
		Connection connection = null;
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
