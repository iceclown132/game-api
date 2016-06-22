package org.spartan.cdi.event;

import org.spartan.cdi.scope.Context;

public interface Event<T> {

	/**
	 * Gets the list of observers for the given child class
	 * 
	 * @param derived
	 * @return
	 */
	<E extends T> Event<E> select(Class<E> derived);
	

	/**
	 * Fires an event with the specified qualifiers and notifies observers.
	 * 
	 * @param event
	 */
	void fire(T event, Context contextual);
	
	/**
	 * Fires an event with the specified qualifiers and notifies observers.
	 * 
	 * @param event
	 */
	void fire(T event);

}
