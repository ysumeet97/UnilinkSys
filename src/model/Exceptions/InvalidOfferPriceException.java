package model.Exceptions;

/**
 * The exception class to handle Offer Price Exceptions
 * 
 * @author sumeet
 * @version 1.0
 */
public class InvalidOfferPriceException extends Exception{

	private static final long serialVersionUID = 1L;
	private String cause;
	
	public InvalidOfferPriceException(String cause) {
		this.cause = cause;
	}
	
	public String toString() {
		return ("InvalidOfferPriceException Occured: " + cause);
	}
}
