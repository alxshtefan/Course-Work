package ua.khnu.shtefanyankovska.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBManager {

	private static DBManager instance;

	private DBManager() {
		// nothing to do
	}

	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}

		return instance;
	}

	public static Connection getConnection() throws NamingException, SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/coursework?" +
			"user=root&password=root&&useSSL=false");
	}

}
