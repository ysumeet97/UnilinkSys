package model.Database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Exceptions.DatabaseException;

/**
 * This class creates Tables related to Unilink System
 * 
 * @author sumeet
 * @version 1.0
 */
public class CreateTables {
	
	/**
	 * This method is responsible for deleting the tales in database
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void deleteTables() throws SQLException, ClassNotFoundException {
		try (Connection con = DatabaseConnection.getConnection("UNILINK");
				Statement stmt = con.createStatement();) {
			stmt.executeUpdate("DROP TABLE REPLIES");
			stmt.executeUpdate("DROP TABLE POSTS");
			
		}
	}

	/**
	 * This method is responsible for creating tables in the database
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws DatabaseException
	 */
	public void createNewTables() throws SQLException, ClassNotFoundException, DatabaseException {
		try (Connection con = DatabaseConnection.getConnection("UNILINK");
				Statement stmt = con.createStatement();) {
			String tables[] = {"POSTS", "REPLIES"};
			for (int count = 0; count < 2; count++) {
				try {
					checkTableExisits(con, tables[count]);
				} catch (DatabaseException exception) {
					int result = -1;
					if(count == 0) {
						result = stmt.executeUpdate("CREATE TABLE " + tables[count]
								+ "(Post_ID varchar(10), Title varchar(100), Description varchar(200),Creator_ID varchar(10), Status varchar(10), Venue varchar(100), Date varchar(10), Capacity integer, Attendee_Count integer, Proposed_Price double, Lowest_Offer double, Ask_Price double, Highest_Offer double, Minimum_Raise double, Image_Path varchar(500), primary key(Post_ID))");
						System.out.println("created table " + tables[count]);
					} else {
						result = stmt.executeUpdate("CREATE TABLE " + tables[count]
								+ "(Post_ID varchar(10), Value double, Responder_ID varchar(10), foreign key(Post_ID) references POSTS(Post_ID))");
						System.out.println("created table"+ tables[count]);
					}
					if (result == 0) {
						System.out.println("Table " + tables[count] + " has been created successfully");
					} else 
						throw new DatabaseException("Table " + tables[count] + " is not created");
				}
			}
		}
	}
	
	/**
	 * This method checks whether the table already exists in the database
	 * 
	 * @param con
	 * @param tableName
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public void checkTableExisits(Connection con, String tableName) throws SQLException, DatabaseException {
		DatabaseMetaData dbm = con.getMetaData();
		ResultSet tables = dbm.getTables(null, null, tableName.toUpperCase(), null);
		if (tables != null) {
			if (!tables.next()) {
				System.out.println("Table " + tableName + " does not exist.");
				throw new DatabaseException("Table " + tableName + " does not exist.");
			}
			tables.close();
		} else {
			throw new DatabaseException("Problem with retrieving database");
		}
	}

}
