package model.Exceptions;

/**
 * The exception class to handle ImportExport Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class ImportExportException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public ImportExportException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("ImportExportException Occured: " + cause);
	}
}
