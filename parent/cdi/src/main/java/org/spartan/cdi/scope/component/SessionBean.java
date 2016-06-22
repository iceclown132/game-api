package org.spartan.cdi.scope.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.spartan.cdi.bean.AbstractBean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;
import org.spartan.cdi.scope.Scope;

public class SessionBean<T> extends AbstractBean<T> {

	/**
	 * The collection of instances of the bean for their corresponding context
	 */
	private final Map<Context, T> instances = new HashMap<>();

	/**
	 * @param beanClass
	 * @param scope
	 * @param injectionPoints
	 */
	public SessionBean(Class<T> beanClass, Scope scope, Set<InjectionPoint> injectionPoints) {
		super(beanClass, scope, injectionPoints);
	}

	/**
	 * Attempts to create the instance if not yet present
	 * @param context
	 * @return
	 */
	private T create(Context context, BeanManager manager) {
		if (context == null || !(context instanceof Session)) { 
			throw new IllegalArgumentException("session context must be provided for sessionbean (" + super.getBeanClass().getName() + ")");
		}
		if(instances.containsKey(context)) {
			throw new IllegalStateException("duplicate key for " + context);
		}
		T instance = manager.instance(super.getBeanClass());
		instances.put(context, manager.inject(this, instance, context));
		return get(context, manager);
	}
	
	/**
	 * 
	 * @param context
	 * @param instance
	 */
	public void set(Context context, T instance) {
		if (context instanceof Session) {
			instances.put(context, instance);
		}
		else throw new IllegalArgumentException("context must be Session");
	}

	@Override
	public T get(Context context, BeanManager manager) {
		if (!instances.containsKey(context) || instances.get(context) == null) {
			return create(context, manager);
		}
		return instances.get(context);
	}

	@Override
	public void destroy(Context context) {
		instances.remove(context);
	}

}
