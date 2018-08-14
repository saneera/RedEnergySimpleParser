package com.redenergy.exception;


/**
 * The Class SimpleNem12ParserException. this is handling the exception with common format
 * 
 * @author Saneera Yapa
 */
public class SimpleNem12ParserException extends Exception {
	
	/**
	 * constructor for a SimpleNem12ParserException exception.
	 *
	 * @param message the message
	 */
	public SimpleNem12ParserException(String message) {
        super(message);
    }

    /**
     * constructor for a SimpleNem12ParserException exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public SimpleNem12ParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
