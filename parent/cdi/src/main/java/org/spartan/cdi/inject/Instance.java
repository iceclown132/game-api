package org.spartan.cdi.inject;

import java.util.Set;

public interface Instance<T> extends Iterable<T> {

	/**
	 * Gets a new instance of the instance
	 * 
	 * @return
	 */
	T get();
	
	/**
	 * 
	 * @return
	 */
	Set<Class<? extends T>> getTypeClosure();
	
	/**
	 * 
	 * @param child
	 * @return
	 */
	<E extends T> Instance<E> select(Class<E> child);

}
