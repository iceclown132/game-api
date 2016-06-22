package org.spartan.cdi.scope.service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.spartan.cdi.bean.AbstractBean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;
import org.spartan.cdi.scope.Scope;

public class ServiceBean<T> extends AbstractBean<T> {

	/**
	 * The instance of this service bean
	 */
	private AtomicReference<T> instance = new AtomicReference<>();

	/**
	 * @param beanClass
	 * @param scope
	 * @param injectionPoints
	 */
	public ServiceBean(Class<T> beanClass, Scope scope, Set<InjectionPoint> injectionPoints) {
		super(beanClass, scope, injectionPoints);
	}

	@Override
	public T get(Context context, BeanManager manager) {
		if (instance.get() == null) {
			T object = manager.instance(super.getBeanClass());
			instance.set(manager.inject(this, object, context));
		}
		return instance.get();
	}
	
	@Override
	public void set(Context context, T instance) {
		this.instance.set(instance);
	}

	@Override
	public void destroy(Context context) {
		
	}

}
