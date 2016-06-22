package org.spartan.cdi.event;

import org.spartan.cdi.scope.Context;

public interface ObserverMethod<T> {

	/**
	 * 
	 * @param event
	 * @param contextual
	 */
	void notify(T event, Context context) throws Exception;
	
	/**
	 * Gets the observed type
	 * 
	 * @return
	 */
	Class<T> getObservedType();

}
