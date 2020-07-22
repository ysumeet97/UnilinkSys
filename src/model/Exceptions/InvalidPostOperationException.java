package model.Exceptions;

/**
 * The exception class to handle Invalid Post Operations Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class InvalidPostOperationException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public InvalidPostOperationException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("InvalidPostOperationException Occured: " + cause);
	}
}
