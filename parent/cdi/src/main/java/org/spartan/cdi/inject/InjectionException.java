package org.spartan.cdi.inject;

public class InjectionException extends RuntimeException {

	/**
	 * The serial version uid
	 */
	private static final long serialVersionUID = 6622717457659096421L;

	/**
	 * 
	 */
	public InjectionException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InjectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InjectionException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public InjectionException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InjectionException(Throwable cause) {
		super(cause);
	}

}
