package org.spartan.cdi.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.spartan.cdi.Container;
import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.scope.Context;

public class InstanceObserverMethod<T> extends AbstractObserverMethod<T> {

	/**
	 * @param observedType
	 * @param method
	 * @param container
	 * @param bean
	 */
	public InstanceObserverMethod(Class<T> observedType, Method method, Container container, Bean<?> bean) {
		super(observedType, method, container, bean);
	}

	@Override
	public void notify(T event, Context context) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object bean_instance = container.manager().getInjectedReference(bean, context);
		method.invoke(bean_instance, super.getParameters(event, context).toArray(new Object[0]));
	}

}
