package ua.khnu.shtefanyankovska.db;

import java.sql.Connection;
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
		Context envCtx = (Context) (new InitialContext().lookup("java:comp/env"));
		DataSource ds = (DataSource) envCtx.lookup("jdbc/coursework");
		return ds.getConnection();
	}

}
