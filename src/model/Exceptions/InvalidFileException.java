package model.Exceptions;

/**
 * The exception class to handle Invalid File Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class InvalidFileException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public InvalidFileException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("InvalidFileException Occured: " + cause);
	}
}
