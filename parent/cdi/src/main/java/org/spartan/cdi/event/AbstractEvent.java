package org.spartan.cdi.event;

import java.util.Set;

import org.spartan.cdi.inject.InjectionException;
import org.spartan.cdi.scope.Context;

public abstract class AbstractEvent<T> implements Event<T> {

	/**
	 * The collection of observer methods
	 */
	protected final Set<ObserverMethod<T>> observer_methods;

	/**
	 * 
	 * @param observedType
	 * @param observers
	 */
	public AbstractEvent(Class<T> observedType, Set<ObserverMethod<T>> observers) {
		this.observer_methods = observers;
	}

	/**
	 * Adds an observer to the list
	 * 
	 * @param observer
	 */
	protected void addObserver(ObserverMethod<T> observer) {
		this.observer_methods.add(observer);
	}

	@Override
	public void fire(T event) {
		this.fire(event, null);
	}

	@Override
	public void fire(T event, Context contextual) {
		observer_methods.forEach(consumer -> {
			try {
				consumer.notify(event, contextual);
			} catch (Exception ex) {
				throw new InjectionException(ex);
			}
		});
	}

}
