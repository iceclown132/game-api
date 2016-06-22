package org.spartan.model.entity.sync;

@SuppressWarnings("serial")
public class SynchronizationException extends RuntimeException {

	/**
	 * 
	 */
	public SynchronizationException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SynchronizationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SynchronizationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public SynchronizationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SynchronizationException(Throwable cause) {
		super(cause);
	}

}
