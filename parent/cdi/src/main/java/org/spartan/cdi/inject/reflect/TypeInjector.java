package org.spartan.cdi.inject.reflect;

import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.inject.Injector;
import org.spartan.cdi.scope.Context;

public class TypeInjector<T> implements Injector<T> {

	/**
	 * The injector's type
	 */
	@SuppressWarnings("unused")
	private final Class<T> type;
	
	/**
	 * The bean manager
	 */
	private final BeanManager manager;

	/**
	 * @param type
	 * @param manager
	 */
	public TypeInjector(Class<T> type, BeanManager manager) {
		this.type = type;
		this.manager = manager;
	}

	@Override
	public T inject(Bean<T> bean, T partial, Context context) {
		bean.getInjectionPoints().forEach(injectionPoint -> injectionPoint.inject(bean, partial, context, manager));
		return partial;
	}

}
