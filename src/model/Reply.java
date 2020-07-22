package model;

/**
 * This class represents a Reply
 * 
 * @author sumeet
 * @version 1.0
 */
public class Reply {
	private String postId;
	private double value;
	private String responderId;

	/**
	 * Constructor
	 * 
	 * @param postId
	 * @param value
	 * @param responderId
	 */
	public Reply(String postId, double value, String responderId) { // constructor to initialize the Reply
		this.postId = postId;
		this.value = value;
		this.responderId = responderId;
	}
	
	/**
	 * This method return the reply details of the current reply
	 * 
	 * @return
	 */
	public String getReplyDetails() {
		return (this.postId + "::" + this.value + "::" + this.responderId);
	}

	/**
	 * This method returns the post Id of the  current reply
	 * 
	 * @return
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * This method returns the offer amount of the current reply
	 * 
	 * @return
	 */
	public double getValue() {
		return value;
	}

	/**
	 * This method returns the responder Id of the current reply
	 * 
	 * @return
	 */
	public String getResponderId() {
		return responderId;
	}	

	// end of getters and setters
}
