package org.spartan.clock;

import java.util.function.Consumer;

public interface Clock {

	/**
	 * The default delay if one is not supplied
	 */
	int DEFAULT_DELAY = 1;

	/**
	 * Schedules a consumer for execution after the specified delay.
	 * 
	 * @param runnable
	 * @return
	 */
	Future schedule(Consumer<Future> consumer, int delay);

	/**
	 * Schedules a consumer for execution after the specified delay.
	 * 
	 * @param runnable
	 * @return
	 */
	Future repeat(Consumer<Future> consumer, int delay);
	
	/**
	 * Shuts down this container
	 */
	void shutdown();
	
	/**
	 * Gets the clock's current time
	 * 
	 * @return
	 */
	long currentTime();
	
	/**
	 * Schedules a task with the default delay
	 * 
	 * @param consumer
	 * @return
	 */
	default Future schedule(Consumer<Future> consumer) {
		return schedule(consumer, DEFAULT_DELAY);
	}
	
	/**
	 * Schedules a task with the default delay
	 * 
	 * @param consumer
	 * @return
	 */
	default Future repeat(Consumer<Future> consumer) {
		return repeat(consumer, DEFAULT_DELAY);
	}

}
