package org.spartan.cdi.event;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashSet;
import java.util.Set;

import org.spartan.cdi.Container;
import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.cdi.scope.Context;

public abstract class AbstractObserverMethod<T> implements ObserverMethod<T> {

	/**
	 * The type being observed
	 */
	private final Class<T> observedType;
	
	/**
	 * The method that needs to be invoked
	 */
	protected final Method method;
	
	/**
	 * The container
	 */
	protected final Container container;
	
	/**
	 * The bean that needs to be injected
	 */
	protected final Bean<?> bean;
	
	/**
	 * @param observedType
	 * @param method
	 * @param container
	 * @param bean
	 */
	public AbstractObserverMethod(Class<T> observedType, Method method, Container container, Bean<?> bean) {
		this.observedType = observedType;
		this.method = method;
		this.container = container;
		this.bean = bean;
	}

	/**
	 * 
	 * @param manager
	 * @param event
	 * @param context
	 * @return
	 */
	protected Set<Object> getParameters(T event, Context context) {
		Set<Object> parameters = new LinkedHashSet<>();
		for (Parameter parameter : method.getParameters()) {
			if (parameter.isAnnotationPresent(Observes.class)) {
				parameters.add(event);
			}
			else if (context != null) {
				parameters.add(container.manager().getInjectedReference(container.manager().get(parameter.getType()), context));
			}
			else {
				throw new InjectionException("no context found while trying to invoke observer method '" + method.getDeclaringClass().getName() + "." + method.getName() + "'");
			}
		}
		return parameters;
	}

	@Override
	public Class<T> getObservedType() {
		return observedType;
	}

}
