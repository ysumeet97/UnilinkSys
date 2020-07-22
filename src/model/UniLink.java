package model;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

import controller.AlertController;
import javafx.scene.control.Alert;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidOfferPriceException;
import model.Exceptions.InvalidPostOperationException;
import model.Exceptions.PostClosedException;
import model.Exceptions.PostNotFoundException;
import view.UnilinkGUI;

/**
 * This class is responsible for the operations carried out on Posts
 * 
 * @author sumeet
 * @version 1.0
 */
public class UniLink { 

	/**
	 * This method creates a new Event post 
	 * 
	 * @param title
	 * @param description
	 * @param creatorid
	 * @param imagePath
	 * @param venue
	 * @param date
	 * @param capacity
	 * @return
	 */
	public boolean newEvent(String title, String description, String creatorid, String imagePath, String venue, String date, int capacity) { // method to create a new Event
		boolean check = false;
		String postid = "EVE" + String.format("%03d", generatePostId("EVE"));
		try {
			TableOperations op = new TableOperations();
			int result = op.insertPosts(postid, title, description, creatorid, "OPEN", venue, date, capacity, 0, 0, 0, 0, 0, 0, imagePath);
			if(result == 1)
				check = true;
		}catch(SQLException | DatabaseException | ClassNotFoundException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}

	/**
	 * This method creates a new Sale Post
	 * 
	 * @param title
	 * @param description
	 * @param creatorid
	 * @param imagePath
	 * @param askPrice
	 * @param minraise
	 * @return
	 */
	public boolean newSale(String title, String description, String creatorid, String imagePath, double askPrice, double minraise) { // method to create a new Sale
		boolean check = false;
		String postid = "SAL" + String.format("%03d", generatePostId("SAL"));
		try {
			TableOperations op = new TableOperations();
			int result = op.insertPosts(postid, title, description, creatorid, "OPEN", "", "", 0, 0, 0, 0, askPrice, 0, minraise, imagePath);
			if(result == 1)
				check = true;
		}catch(SQLException | DatabaseException | ClassNotFoundException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}

	/**
	 * This method creates a new Job post
	 * 
	 * @param title
	 * @param description
	 * @param creatorid
	 * @param imagePath
	 * @param proprice
	 * @return
	 */
	public boolean newJob(String title, String description, String creatorid, String imagePath, double proprice) { // method to create a new Job
		boolean check = false;
		String postid = "JOB" + String.format("%03d", generatePostId("JOB"));
		try {
			TableOperations op = new TableOperations();
			int result = op.insertPosts(postid, title, description, creatorid, "OPEN", "", "", 0, 0, proprice, proprice, 0, 0, 0, imagePath);
			if(result == 1)
				check = true;
		}catch(SQLException | DatabaseException | ClassNotFoundException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}
	
	/**
	 * This method is used to reply to a current post
	 * 
	 * @param postid
	 * @param offer
	 * @param username
	 * @return
	 * @throws PostNotFoundException
	 * @throws InvalidPostOperationException
	 * @throws InvalidOfferPriceException
	 * @throws PostClosedException
	 */
	public boolean replyPost(String postid, double offer, String username) throws PostNotFoundException, InvalidPostOperationException, InvalidOfferPriceException, PostClosedException { // method to reply to a Post
		ArrayList<Post> post = UnilinkGUI.getPost("ALL", "ALL", "ALL");
		if (Pattern.matches("JOB|EVE|SAL", postid.substring(0, 3).toUpperCase())) {
			int k = 0;
			boolean b = false;
			for (int i = 0; i < post.size(); i++) {
				if (post.get(i).getId().equals(postid)) {
					k = i;
					b = true;
				}
			}
			if (post.get(k).getCid().equalsIgnoreCase(username))
				throw new InvalidPostOperationException("Creator cannot reply for his own post!");
			else {
				if (b) {
					Reply reply = new Reply(postid, offer, username);
					boolean offerstatus = post.get(k).handleReply(reply);
					return offerstatus;
				} else
					throw new PostNotFoundException("Post id not present!");
			}
		} else
			throw new PostNotFoundException("Enter valid post id!");
	}
	
	/**
	 * This method closes a given post
	 * 
	 * @param postid
	 * @param username
	 * @return
	 * @throws PostNotFoundException
	 * @throws InvalidPostOperationException
	 */
	public boolean closePost(String postid, String username) throws PostNotFoundException, InvalidPostOperationException {
		ArrayList<Post> post = UnilinkGUI.getPost("ALL", "ALL", "ALL");
		boolean check = false;
		if (Pattern.matches("JOB|EVE|SAL", postid.substring(0, 3).toUpperCase())) {
			int k = 0;
			boolean b = false;
			for (int i = 0; i < post.size(); i++) {
				if (post.get(i).getId().equals(postid)) {
					k = i;
					b = true;
				}
			}
			if (b == true) {
				if(post.get(k).getCid().equalsIgnoreCase(username)) {
					try {
						TableOperations op = new TableOperations();
						int result = op.updatePostStatus(postid, "CLOSED");
						if(result == 1)
							check = true;
					}catch(SQLException | ClassNotFoundException | DatabaseException exception) {
						Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
						exceptionAlert.show();
					}
				}else
					throw new InvalidPostOperationException("Only creator can close the post.");
			} else
				throw new PostNotFoundException("Post id not present!");
		}else
			throw new PostNotFoundException("Enter valid post id!");
		return check;
	}
	
	/**
	 * This method deletes a given post
	 * 
	 * @param postid
	 * @param username
	 * @return
	 * @throws InvalidPostOperationException
	 * @throws PostNotFoundException
	 */
	public boolean deletePost(String postid, String username) throws InvalidPostOperationException, PostNotFoundException {
		ArrayList<Post> post = UnilinkGUI.getPost("ALL", "ALL", "ALL");
		boolean check = false;
		if (Pattern.matches("JOB|EVE|SAL", postid.substring(0, 3).toUpperCase())) {
			int k = 0;
			boolean b = false;
			for (int i = 0; i < post.size(); i++) {
				if (post.get(i).getId().equals(postid)) {
					k = i;
					b = true;
				}
			}
			if (b == true) {
				if(post.get(k).getCid().equalsIgnoreCase(username)) {
					try {
						TableOperations op = new TableOperations();
						op.deletePost(postid);
						check = true;
					} catch(SQLException | ClassNotFoundException | DatabaseException exception) {
						Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
						exceptionAlert.show();
					}
				} else
					throw new InvalidPostOperationException("Only creator can delete the post.");
			} else
				throw new PostNotFoundException("Post id not present!");
		} else
			throw new PostNotFoundException("Enter valid post id!");
		return check;
	}
	
	/**
	 * This method updates the event details
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param venue
	 * @param date
	 * @param capacity
	 * @return
	 */
	public boolean updateEventDetails(String postId, String title, String description, String venue, String date, int capacity) {
		boolean check = false;
		try {
			TableOperations op = new TableOperations();
			op.updateEvent(postId, title, description, venue, date, capacity);
			check = true;
		} catch (ClassNotFoundException | SQLException | DatabaseException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}
	
	/**
	 * This method updates the Job details
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param proprice
	 * @return
	 */
	public boolean updateJobDetails(String postId, String title, String description, double proprice) {
		boolean check = false;
		try {
			TableOperations op = new TableOperations();
			op.updateJob(postId, title, description, proprice);
			check = true;
		} catch (ClassNotFoundException | SQLException | DatabaseException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}
	
	/**
	 * This method updates the Sale details
	 * 
	 * @param postId
	 * @param title
	 * @param description
	 * @param askprice
	 * @param minraise
	 * @return
	 */
	public boolean updateSaleDetails(String postId, String title, String description, double askprice, double minraise) {
		boolean check = false;
		try {
			TableOperations op = new TableOperations();
			op.updateSale(postId, title, description, askprice, minraise);
			check = true;
		} catch (ClassNotFoundException | SQLException | DatabaseException exception) {
			Alert exceptionAlert = AlertController.pushAlerts("WARNING", exception.toString());
			exceptionAlert.show();
		}
		return check;
	}
	
	/**
	 * This method sets the image path of the given post
	 * 
	 * @param postId
	 * @param path
	 * @return
	 */
	public boolean setImagePath(String postId, String path) {
		boolean check = false;
		try {
			TableOperations op = new TableOperations();
			op.updateImagePath(postId, path);
			check = true;
		} catch (SQLException | ClassNotFoundException | DatabaseException e) {
			e.toString();
		}
		return check;
	}
	
	/**
	 * This method generates the unique post Id for a given post
	 * 
	 * @param type
	 * @return
	 */
	private int generatePostId(String type) {
		int count = 0;
		ArrayList<Post> postList = UnilinkGUI.getPost("ALL", "ALL", "ALL");
		if(postList == null || postList.isEmpty())
			count = count + 1;
		else {
			for(int i = postList.size() - 1; i >= 0; i--) {
				if(postList.get(i).getId().substring(0,3).equalsIgnoreCase(type)) {
					count = Integer.parseInt(postList.get(i).getId().substring(3)) + 1;
					break;
				}
				else
					count = 1;
			}
		}
		return count;
	}
		
}
