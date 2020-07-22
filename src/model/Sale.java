package model;

import java.sql.SQLException;
import java.util.*;
import model.Database.TableOperations;
import model.Exceptions.DatabaseException;
import model.Exceptions.InvalidOfferPriceException;
import model.Exceptions.PostClosedException;

/**
 * This class represents a Sale post
 * 
 * @author sumeet
 * @version 1.0
 */
public class Sale extends Post {
	private double askprice;
	private double highoffer;
	private double minraise;

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param title
	 * @param desc
	 * @param cid
	 * @param imagePath
	 * @param askprice
	 * @param minraise
	 */
	public Sale(String id, String title, String desc, String cid, String imagePath, double askprice, double minraise) { // constructor to initialize the Sale
		super(id, title, desc, cid, imagePath);
		this.askprice = askprice;
		this.highoffer = 0;
		this.minraise = minraise;
	}

	/**
	 *This method overrides the super method to return the post details of the current post
	 */
	@Override
	public String getPostDetails() { // overridden getPostDetails method from Post
			return (super.getPostDetails() + "::" + this.askprice + "::" + this.minraise + "::"
					+ this.highoffer);
	}

	/**
	 * This method overrides the super method to handle the reply for the current post
	 */
	@Override
	public boolean handleReply(Reply reply) throws InvalidOfferPriceException, PostClosedException{ // overridden handleReply method from Post
		if (this.getStatus().equals("CLOSED")) {
			throw new PostClosedException("Sale is closed!");
		} else if (reply.getValue() <= this.highoffer || (reply.getValue() - this.highoffer) < this.minraise) {
			String str = "";
			if (reply.getValue() <= this.highoffer)
				str = "Offer not accepted: The price is less than the previous highest offer. ";
			else
				str = "Offer not accepted: The price must be $" + this.minraise + " greater than the highest offer. ";
			throw new InvalidOfferPriceException(str);
		} else {
			try {
				TableOperations op = new TableOperations();
				this.addReply(reply);
				this.highoffer = reply.getValue();
				op.updateHighOffer(reply.getPostId(), reply.getValue());
				if (this.highoffer >= this.askprice) {
					this.setStatus("CLOSED");
					op.updatePostStatus(reply.getPostId(), "CLOSED");
				}
			}catch(SQLException | ClassNotFoundException | DatabaseException exception) {
				exception.getMessage();
			}
			return true;
		}
	}

	/**
	 *This method overrides the super method to return the reply details for the current post
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
	 * This method returns the asking price for the current post
	 * @return
	 */
	public double getAskprice() {
		return askprice;
	}
	
	/**
	 * This method returns the highest offer for the current post
	 * @return
	 */
	public double getHighoffer() {
		return highoffer;
	}
	
	/**
	 * This method sets the highest offer for the current post
	 * @return
	 */
	public void setHighoffer(double highoffer) {
		this.highoffer = highoffer;
	}

	/**
	 * This method returns the minimum raise for the current post
	 * @return
	 */
	public double getMinraise() {
		return minraise;
	}

	
}
