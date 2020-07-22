package model.Exceptions;

/**
 * The exception class to handle Post Not Found Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class PostNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public PostNotFoundException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("PostNotFoundException Occured: " + cause);
	}
}