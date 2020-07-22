package model.Exceptions;

/**
 * The exception class to handle Post Status Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class PostClosedException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public PostClosedException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("IpostClosedException Occured: " + cause);
	}
}