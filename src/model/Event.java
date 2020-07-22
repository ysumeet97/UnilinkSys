package model;

import java.sql.SQLException;
import java.util.*;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;
import model.Exceptions.PostClosedException;

/**
 * This class represents a Event post
 * 
 * @author sumeet
 * @version 1.0
 */
public class Event extends Post {
	private String venue;
	private String date;
	private int capacity;
	private int attcount;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param title
	 * @param desc
	 * @param cid
	 * @param imagePath
	 * @param venue
	 * @param date
	 * @param capacity
	 */
	public Event(String id, String title, String desc, String cid, String imagePath, String venue, String date, int capacity) { // constructor to initialize the event
		super(id, title, desc, cid, imagePath);
		this.venue = venue;
		this.date = date;
		this.capacity = capacity;
		attcount = 0;
	}

	
	/**
	 *This method ovverrides the super method and returns the post details
	 */
	@Override
	public String getPostDetails() { // overridden getPostDetails method from Post
		return (super.getPostDetails() + "::" + this.venue + "::" + this.date + "::"
				+ this.capacity + "::" + this.attcount);
	}

	/**
	 * This method overrides the super method to handle reply to a event post
	 */
	@Override
	public boolean handleReply(Reply reply) throws PostClosedException{ // overridden handleReply method from Post
		if (!(this.getStatus().equals("CLOSED"))) {
			try {
				TableOperations op = new TableOperations();
				op.updateAttendeeCount(reply.getPostId(), ++this.attcount);
			}catch(SQLException | ClassNotFoundException | DatabaseException exception) {
				exception.getMessage();
			}
			if (this.attcount == this.capacity) {
				try {
					this.setStatus("CLOSED");
					TableOperations op = new TableOperations();
					op.updatePostStatus(reply.getPostId(), "CLOSED");
				}catch(SQLException | ClassNotFoundException | DatabaseException exception) {
					exception.getMessage();
				}
			}
			this.addReply(reply);
			return true;
		} else
			throw new PostClosedException("Event is closed!");
	}

	/**
	 *This method overrides the super method to return reply details for the current post
	 */
	@Override
	public String getReplyDetails() { // overridden getReplyDetails method from Post
		ArrayList<Reply> rep = Post.getReplies(this.getId());
		String s = "";
		for (Reply r : rep)
			s += r.getResponderId() + ", ";
		if (this.attcount <= 0)
			s = "Empty";
		return (this.getPostDetails() + "\nAttendee List: " + s);
	}

	/**
	 * This method returns the Venue of the current post
	 * 
	 * @return
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * This method returns the date of the current post
	 * 
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * This method returns the capacity of the current post
	 * 
	 * @return
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * This method returns the attendee count of the current post
	 * 
	 * @return
	 */
	public int getAttcount() {
		return attcount;
	}

	/**
	 * Thsi method sets the attendee count of the current post
	 * 
	 * @param attcount
	 */
	public void setAttcount(int attcount) {
		this.attcount = attcount;
	}
	
}
