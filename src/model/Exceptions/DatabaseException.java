package model.Exceptions;

/**
 * The exception class to handle Datatabase Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class DatabaseException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public DatabaseException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("DatabaseException Occured: " + cause);
	}
}
