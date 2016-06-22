package org.spartan.net;

public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ServiceException(String message) {
		super(message);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 */
	public ServiceException(Throwable cause) {
		super(cause);
	}

}
