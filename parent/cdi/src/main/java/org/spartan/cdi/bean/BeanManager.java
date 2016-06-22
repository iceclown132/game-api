package org.spartan.cdi.bean;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;
import org.spartan.cdi.scope.Scope;

public interface BeanManager {

	/**
	 * Gets the bean object for a given class
	 * 
	 * @param beanClass
	 * @return
	 */
	<T> Bean<T> get(Class<T> beanClass);
	
	/**
	 * 
	 * @param beanClass
	 * @return
	 */
	boolean beanExists(Class<?> beanClass);

	/**
	 * Gets the scope for the given scope identifying annotation
	 * 
	 * @param annotation
	 * @return
	 */
	Scope getScope(Class<? extends Annotation> annotation);
	
	/**
	 * Gets a collection of injection points
	 * @return
	 */
	Set<InjectionPoint> getInjectionPoints(Class<?> type);

	/**
	 * Injects a bean
	 * 
	 * @param type
	 * @param partial
	 * @param context
	 * @return
	 */
	<T> T inject(Bean<T> type, T partial, Context context);
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	<T> T getInjectedReference(Bean<T> bean, Context context);
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	default <T> T getInjectedReference(Class<T> type, Context context) {
		return getInjectedReference(get(type), context);
	}

	/**
	 * Scans for beans
	 */
	void scan(Reflections reflections) throws Exception;

	/**
	 * @param beanClass
	 * @return
	 */
	<T> T instance(Class<T> beanClass);
	
	/**
	 * I don't really want this but I must
	 * 
	 * @param type
	 * @param instance
	 * @param context
	 */
	<T> void associate(Class<T> type, T instance, Context context);

	/**
	 * Checks to see if the given context contains a bean of the given type
	 * 
	 * @param class1
	 * @param session
	 * @return
	 */
	boolean contains(Class<?> type, Context context);
	
}
