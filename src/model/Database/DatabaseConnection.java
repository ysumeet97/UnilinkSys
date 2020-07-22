package model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is responsible for operations related to the connection to Unilink Database
 * 
 * @author sumeet
 * @version 1.0
 */
public class DatabaseConnection {

	/**
	 * Constructor of the class
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DatabaseConnection() throws ClassNotFoundException, SQLException {
		String dbName = "UNILINK";
		Connection con = getConnection(dbName);
		if (con != null) {
			System.out.println("Database created successfully!");
		} else {
			throw new ClassNotFoundException("Error establishing Connection to DB");
		}
	}

	/**
	 * returns a connection to database if exists or creates a new connection
	 * 
	 * @param dbName
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public static Connection getConnection(String dbName) throws SQLException, ClassNotFoundException {
		Class.forName("org.hsqldb.jdbc.JDBCDriver");
		return DriverManager.getConnection("jdbc:hsqldb:file:database/" + dbName, "admin","admin");
	}
}