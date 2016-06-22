package org.spartan.cdi.bean;

import java.util.Set;

import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Scope;

public abstract class AbstractBean<T> implements Bean<T> {

	/**
	 * The bean class
	 */
	private final Class<T> beanClass;

	/**
	 * The bean's scope
	 */
	private final Scope scope;

	/**
	 * The collection of injection points
	 */
	private final Set<InjectionPoint> injectionPoints;

	/**
	 * @param beanClass
	 * @param scope
	 */
	public AbstractBean(Class<T> beanClass, Scope scope, Set<InjectionPoint> injectionPoints) {
		this.beanClass = beanClass;
		this.scope = scope;
		this.injectionPoints = injectionPoints;
	}

	@Override
	public Class<T> getBeanClass() {
		return beanClass;
	}

	@Override
	public Scope scope() {
		return scope;
	}

	@Override
	public Set<InjectionPoint> getInjectionPoints() {
		return injectionPoints;
	}

}
