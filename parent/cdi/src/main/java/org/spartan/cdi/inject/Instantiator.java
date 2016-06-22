package org.spartan.cdi.inject;

/**
 * 
 * @author brock
 *
 * @param <T>
 */
public interface Instantiator {

	/**
	 * 
	 * @param bean
	 * @param container
	 * @return
	 */
	<T> T instantiate(Class<T> type);

}
