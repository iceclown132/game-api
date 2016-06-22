package org.spartan.cdi.bean;

import java.util.Set;

import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;

public interface Bean<T> {

	/**
	 * 
	 * @param context
	 * @return
	 */
	T get(Context context, BeanManager manager);
	
	/**
	 * 
	 * @param context
	 * @param instance
	 */
	void set(Context context, T instance);

	/**
	 * The class of the type the bean is representing
	 * @return
	 */
	Class<T> getBeanClass();
	
	/**
	 * The collection of injection points
	 * @return
	 */
	Set<InjectionPoint> getInjectionPoints();

	/**
	 * Destroys the bean in the given context
	 * 
	 * @param context
	 */
	void destroy(Context context);

	/**
	 * The scope of the bean
	 * 
	 * @return
	 */
	Object scope();

}
