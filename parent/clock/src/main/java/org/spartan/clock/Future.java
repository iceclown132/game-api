package org.spartan.clock;

import java.util.function.Consumer;

public interface Future {

	/**
	 * 
	 * @param runnable
	 * @return
	 */
	Future onCompletion(Runnable consumer);

	/**
	 * 
	 * @param consumer
	 * @return
	 */
	Future onError(Consumer<Exception> consumer);
	
	/**
	 * Attempts to cancel the task
	 */
	void cancel();
	
	/**
	 * Attempts to interrupt the task
	 */
	void interrupt();
	
	/**
	 * Indicates whether or not the task is canceled
	 * @return
	 */
	boolean canceled();
	
	/**
	 * Indicates whether or not the task is interrupted
	 */
	boolean interrupted();

}