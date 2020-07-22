package model.Exceptions;

/**
 * The exception class to handle Invalid Input Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class InvalidInputException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public InvalidInputException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("InvalidInputException Occured: " + cause);
	}
}
