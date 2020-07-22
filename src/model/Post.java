package model;

import java.util.*;
import model.Database.TableOperations;
import model.Exceptions.InvalidOfferPriceException;
import model.Exceptions.PostClosedException;

/**
 * This class Represents a Post
 * 
 * @author sumeet
 * @version 1.0
 */
public abstract class Post {
	private String id;
	private String title;
	private String desc;
	private String cid;
	private String status;
	private String imagePath;
	private ArrayList<Reply> replies; // ArrayList to store the objects of type Reply

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param title
	 * @param desc
	 * @param cid
	 * @param imagePath
	 */
	public Post(String id, String title, String desc, String cid, String imagePath) {  // constructor to initialize the objects
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.cid = cid;
		replies = new ArrayList<Reply>();
		status = "OPEN";
		if (imagePath != null) {
			this.imagePath = imagePath;
		} else {
			this.imagePath = "Images/No_image_available.jpg";
		}
	}

	/**
	 * Returns the details of the post
	 * 
	 * @return
	 */
	public String getPostDetails() { // method to print the post details
		return (this.id + "::" + this.title + "::" + this.desc + "::"
				+ this.cid + "::" + this.status + "::" + this.imagePath);
	}

	/**
	 * Abstract Method to handle reply
	 * 
	 * @param reply
	 * @return
	 * @throws InvalidOfferPriceException
	 * @throws PostClosedException
	 */
	public abstract boolean handleReply(Reply reply) throws InvalidOfferPriceException,PostClosedException; // abstract method to handle a reply

	/**
	 * Abstract method to return reply details
	 * 
	 * @return
	 */
	public abstract String getReplyDetails(); // abstract method to print the reply details

	// getters and setters
	
	/**
	 * This method will return the post Id of the current object
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method will return the creator Id of the current object
	 * 
	 * @return
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * This method will return the status of the current post object
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method will set the status of the current post object
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * This method will return the image path of the current post object
	 * 
	 * @return
	 */
	public String getImagePath() {
		return imagePath;
	}
	
	/**
	 * This method will set the image path of the current post object
	 * 
	 * @param imagePath
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * This method will return the List of Replies for a given post
	 * 
	 * @param postid
	 * @return {@link ArrayList}
	 */
	public static ArrayList<Reply> getReplies(String postid) {
		ArrayList<Reply> reply = null;
		try {
			TableOperations op = new TableOperations();
			reply = op.getReply(postid);
		} catch(Exception exception) {
			exception.getMessage();
		}
		return reply;
	}
	
	/**
	 * This method will add the reply to the reply List of that post 
	 * 
	 * @param reply
	 */
	public void setReply(ArrayList<Reply> reply) {
		this.replies =reply;
	}

	/**
	 * This method will insert the reply into the database
	 * 
	 * @param reply
	 */
	public void addReply(Reply reply) {
		try {
			this.replies.add(reply);
			TableOperations op = new TableOperations();
			op.insertReplies(reply);
		} catch(Exception exception) {
			exception.getMessage();
		}
	}
	
	/**
	 * This method will return the title of the post object
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * This method will return the description of post object
	 * 
	 * @return
	 */
	public String getDescription() {
		return desc;
	}

	// end of getters and setters
}
