package model;

import java.sql.SQLException;
import java.util.*;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidOfferPriceException;
import model.Exceptions.PostClosedException;

/**
 * This class represents a Job post
 * 
 * @author sumeet
 * @version 1.0
 */
public class Job extends Post {
	private double proprice;
	private double lowoffer;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param title
	 * @param desc
	 * @param cid
	 * @param imagePath
	 * @param proprice
	 */
	public Job(String id, String title, String desc, String cid, String imagePath, double proprice) { // constructor to initialize the job
		super(id, title, desc, cid, imagePath);
		this.proprice = proprice;
		this.lowoffer = proprice;
	}

	/**
	 * This method overrides the super method to return the post details
	 */
	@Override
	public String getPostDetails() { // overridden getPostDetails method from Post
		return (super.getPostDetails() + "::" + this.proprice + "::" + this.lowoffer);
	}

	/**
	 * This method overrides the super method to handle the reply of current post
	 */
	@Override
	public boolean handleReply(Reply reply) throws InvalidOfferPriceException, PostClosedException{ // overridden handleReply method from Post
		if (this.getStatus().equals("CLOSED"))
			throw new PostClosedException("Job is closed!");
		else if (reply.getValue() >= this.lowoffer)
			throw new InvalidOfferPriceException("Proposed price is greater than lowoffer.");
		else {
			try {
				TableOperations op = new TableOperations();
				this.lowoffer = reply.getValue();
				op.updateLowOffer(reply.getPostId(), this.lowoffer);
				this.addReply(reply);
			}catch(SQLException | ClassNotFoundException | DatabaseException exception) {
				exception.getMessage();
			}
			return true;
		}
	}

	/**
	 * This method overrides the super method to return reply details of the current post
	 */
	@Override
	public String getReplyDetails() { // overridden getReplyDetails method from Post
		ArrayList<Reply> r = Post.getReplies(this.getId());
		String details = "-- Offer History --";
		for (int i = r.size() - 1; i >= 0; i--) {
			details += "\n" + r.get(i).getResponderId() + ": $" + r.get(i).getValue();
		}
		return (this.getPostDetails() + "\n" + details);
	}

	/**
	 * This method returns the proposed price of the current post
	 * @return
	 */
	public double getProprice() {
		return proprice;
	}

	/**
	 * This method returns the lowest offer of the current post
	 * @return
	 */
	public double getLowoffer() {
		return lowoffer;
	}

	/**
	 * This method sets the lowest offer of the current post
	 * @param lowoffer
	 */
	public void setLowoffer(double lowoffer) {
		this.lowoffer = lowoffer;
	}
	

}
