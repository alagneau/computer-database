package fr.excilys.formation.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Model {
	List<Company> lc = new ArrayList<Company>();
	private static Model model;
	private static final String URL = "jdbc:mysql://localhost/computer-database-db", username = "root",
			password = "tapaqual";
	private Connection con;

	private Model() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(URL, username, password);
		if (con != null)
			con.close();
	}

	public static Model getInstance() {
		if (model == null) {
			try {
				model = new Model();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return model;
	}

	public void trouverordinateurparcompany(Company comp) {
		lc.add(comp);
	}

}
