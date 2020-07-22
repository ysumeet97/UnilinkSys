package model.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Event;
import model.Job;
import model.Post;
import model.Reply;
import model.Sale;
import model.Exceptions.DatabaseException;

/**
 * This class is responsible for handling all the DML operations on the table
 * 
 * @author sumeet
 * @version 1.0
 */
public class TableOperations {
	
	private Statement statement;
	private Connection connection;
	private PreparedStatement ps;
	private ArrayList<Post> postList;
	
	/**
	 * Constructor
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	public TableOperations() throws ClassNotFoundException, SQLException, DatabaseException {
		connection = DatabaseConnection.getConnection("UNILINK");
		if (connection != null) {
			statement = connection.createStatement();
		} else {
			throw new DatabaseException("Connection to DB failed");
		}
	}
	
	/**
	 * This method inserts posts into the Posts Table
	 * 
	 * @param postid
	 * @param title
	 * @param description
	 * @param creatorid
	 * @param status
	 * @param venue
	 * @param date
	 * @param capacity
	 * @param attcount
	 * @param proprice
	 * @param lowoffer
	 * @param askprice
	 * @param highoffer
	 * @param minraise
	 * @param imagePath
	 * @return
	 * @throws SQLException
	 */
	public int insertPosts(String postid, String title, String description, String creatorid, String status,
			String venue, String date, int capacity, int attcount, double proprice,
			double lowoffer, double askprice, double highoffer, double minraise, String imagePath) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("INSERT INTO POSTS VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		ps.setString(1, postid);
		ps.setString(2, title);
		ps.setString(3, description);
		ps.setString(4, creatorid);
		ps.setString(5, status);
		ps.setString(6, venue);
		ps.setString(7, date);
		ps.setInt(8, capacity);
		ps.setInt(9, attcount);
		ps.setDouble(10, proprice);
		ps.setDouble(11, lowoffer);
		ps.setDouble(12, askprice);
		ps.setDouble(13, highoffer);
		ps.setDouble(14, minraise);
		ps.setString(15, imagePath);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method inserts replies into the Replies Table
	 * 
	 * @param reply
	 * @return
	 * @throws SQLException
	 */
	public int insertReplies(Reply reply) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("INSERT INTO REPLIES VALUES(?, ?, ?)");
		ps.setString(1, reply.getPostId());
		ps.setDouble(2, reply.getValue());
		ps.setString(3, reply.getResponderId());
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method returns posts based on the filters applied to {@link Unilink}
	 * 
	 * @param typeValue
	 * @param statusValue
	 * @param creatorValue
	 * @return {@link ArrayList}
	 * @throws SQLException
	 */
	public ArrayList<Post> getFilteredPosts(String typeValue, String statusValue, String creatorValue) throws SQLException{
		postList = new ArrayList<Post>();
		String query = "";
		if (statement == null) {
			statement = connection.createStatement();
		}
		if(typeValue.equalsIgnoreCase("All")) {
			if(statusValue.equalsIgnoreCase("All")){
				if(creatorValue.equalsIgnoreCase("All")) {
					query = "SELECT * FROM POSTS";
				}
				else {
					query = String.format("SELECT * fROM POSTS WHERE Creator_ID = '%s'", creatorValue.toUpperCase());
				}
			}else {
				if(creatorValue.equalsIgnoreCase("All")) {
					query = String.format("SELECT * FROM POSTS WHERE Status = '%s'", statusValue.toUpperCase());
				}
				else {
					query = String.format("SELECT * FROM POSTS WHERE Status = '%s' AND Creator_ID = '%s'", statusValue.toUpperCase(), creatorValue.toUpperCase());
				}	
			}
		}else {
			
			if(statusValue.equalsIgnoreCase("All")){
				if(creatorValue.equalsIgnoreCase("All")) {
					query = String.format("SELECT * FROM POSTS WHERE UPPER(SUBSTR(Post_ID, 1, 3)) = '%s'", typeValue.substring(0, 3).toUpperCase());
				}
				else {
					query = String.format("SELECT * FROM POSTS WHERE SUBSTRING(Post_ID, 1, 3) = '%s' AND Creator_ID = '%s'", typeValue.substring(0, 3).toUpperCase(), creatorValue.toUpperCase());
				}
			}else {
				if(creatorValue.equalsIgnoreCase("All")) {
					query = String.format("SELECT * FROM POSTS WHERE SUBSTRING(Post_ID, 1, 3) = '%s' AND Status = '%s'",typeValue.substring(0, 3).toUpperCase(), statusValue.toUpperCase());
				}
				else {
					query = String.format("SELECT * FROM POSTS WHERE SUBSTRING(Post_ID, 1, 3) = '%s' AND Status = '%s' AND Creator_ID = '%s'",typeValue.substring(0, 3).toUpperCase(), statusValue.toUpperCase(), creatorValue.toUpperCase());
				}
			}
		}
		ResultSet rs = statement.executeQuery(query);
		connection.commit();
		while(rs.next()) {
			String postId = rs.getString("Post_ID");
			String title = rs.getString("Title");
			String description = rs.getString("Description");
			String creatorId = rs.getString("Creator_ID");
			String status = rs.getString("Status");
			String imagePath = rs.getString("Image_Path");
			String venue = "";
			String date = "";
			int capacity = 0;
			int attcount = 0;
			double proprice = 0;
			double lowoffer = 0;
			double askprice = 0;
			double highoffer = 0;
			double minraise = 0;
			Event event;
			Job job;
			Sale sale;
			if(postId.substring(0, 3).equalsIgnoreCase("EVE")) {
				venue = rs.getString("Venue");
				date = rs.getString("Date");
				capacity = rs.getInt("Capacity");
				attcount = rs.getInt("Attendee_Count");
				event = new Event(postId, title, description, creatorId, imagePath, venue, date, capacity);
				event.setStatus(status);
				event.setAttcount(attcount);
				postList.add(event);
			}
			else if(postId.substring(0, 3).equalsIgnoreCase("JOB")) {
				proprice = rs.getDouble("Proposed_Price");
				lowoffer = rs.getDouble("Lowest_Offer");
				job = new Job(postId, title, description, creatorId, imagePath, proprice);
				job.setStatus(status);
				job.setLowoffer(lowoffer);
				postList.add(job);
			}
			else if(postId.substring(0, 3).equalsIgnoreCase("SAL")){
				askprice = rs.getDouble("Ask_Price");
				highoffer = rs.getDouble("Highest_Offer");
				minraise = rs.getDouble("Minimum_Raise");
				sale = new Sale(postId, title, description, creatorId, imagePath, askprice, minraise);
				sale.setStatus(status);
				sale.setHighoffer(highoffer);
				postList.add(sale);
			}
		}
		return postList;
	}
	
	/**
	 * This method returns replies for a given post to (@link Unilink}
	 * 
	 * @param postid
	 * @return {@link ArrayList}
	 * @throws SQLException
	 */
	public ArrayList<Reply> getReply(String postid) throws SQLException{
		if (statement == null) {
			statement = connection.createStatement();
		}
		String query="";
		ArrayList<Reply> reply = new ArrayList<Reply>();
		if(postid.equalsIgnoreCase("ALL"))
			query = String.format("SELECT * FROM REPLIES");
		else
			query = String.format("SELECT * FROM REPLIES WHERE Post_ID = '%s'", postid);
		ResultSet rs = statement.executeQuery(query);
		connection.commit();
		while(rs.next()) {
			String postId = rs.getString("Post_ID");
			double value = rs.getDouble("Value");
			String responderId = rs.getString("Responder_ID");
			Reply r = new Reply(postId, value, responderId);
			reply.add(r);
		}
		return reply;
		
	}
	
	/**
	 * This method updates imagePath for a given post
	 * 
	 * @param postId
	 * @param path
	 * @return
	 * @throws SQLException
	 */
	public int updateImagePath(String postId, String path) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Image_Path = ? where Post_ID = ?");
		ps.setString(1, path);
		ps.setString(2, postId);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method updates the post statius for a given post
	 * 
	 * @param postid
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	public int updatePostStatus(String postid, String status) throws SQLException{
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Status = ? where Post_ID = ?");
		ps.setString(1, status);
		ps.setString(2, postid);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method updates the attendee count for a given post
	 * 
	 * @param postid
	 * @param count
	 * @return
	 * @throws SQLException
	 */
	public int updateAttendeeCount(String postid, int count) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Attendee_Count = ? where Post_ID = ?");
		ps.setInt(1, count);
		ps.setString(2, postid);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method updates the Highest Offer for a given post
	 * 
	 * @param postid
	 * @param highoffer
	 * @return
	 * @throws SQLException
	 */
	public int updateHighOffer(String postid, double highoffer) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Highest_Offer = ? where Post_ID = ?");
		ps.setDouble(1, highoffer);
		ps.setString(2, postid);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method updates the Lowest Offer for a given post
	 * 
	 * @param postid
	 * @param lowoffer
	 * @return
	 * @throws SQLException
	 */
	public int updateLowOffer(String postid, double lowoffer) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Lowest_Offer = ? where Post_ID = ?");
		ps.setDouble(1, lowoffer);
		ps.setString(2, postid);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
	
	/**
	 * This method deletes a post fron Posts table
	 * 
	 * @param postid
	 * @return
	 * @throws SQLException
	 */
	public int deletePost(String postid) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Delete from POSTS where Post_ID = ?");
		ps.setString(1, postid);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}

	/**
	 * This method updates the details of the given Event post
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param venue
	 * @param date
	 * @param capacity
	 * @return
	 * @throws SQLException
	 */
	public int updateEvent(String postId, String title, String description, String venue, String date, int capacity) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Title = ?, Description = ?, Venue = ?, Date = ?, Capacity = ? where Post_ID = ?");
		ps.setString(1, title);
		ps.setString(2, description);
		ps.setString(3, venue);
		ps.setString(4, date);
		ps.setInt(5, capacity);
		ps.setString(6, postId);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}

	/**
	 * This method updates the details of the given Job post
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param proprice
	 * @return
	 * @throws SQLException
	 */
	public int updateJob(String postId, String title, String description, double proprice) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Title = ?, Description = ?, Proposed_Price = ? where Post_ID = ?");
		ps.setString(1, title);
		ps.setString(2, description);
		ps.setDouble(3, proprice);
		ps.setString(4, postId);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}

	/**
	 * This method updates the details of the given Sale post
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param askprice
	 * @param minraise
	 * @return
	 * @throws SQLException
	 */
	public int updateSale(String postId, String title, String description, double askprice, double minraise) throws SQLException {
		if (statement == null) {
			statement = connection.createStatement();
		}
		ps = connection.prepareStatement("Update POSTS set Title = ?, Description = ?, Ask_Price = ?, Minimum_Raise = ? where Post_ID = ?");
		ps.setString(1, title);
		ps.setString(2, description);
		ps.setDouble(3, askprice);
		ps.setDouble(4, minraise);
		ps.setString(1, postId);
		int result = ps.executeUpdate();
		connection.commit();
		return result;
	}
}
