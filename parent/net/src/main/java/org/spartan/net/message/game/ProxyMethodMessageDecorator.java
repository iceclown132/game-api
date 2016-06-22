package org.spartan.net.message.game;

import java.lang.reflect.Method;

import org.spartan.cdi.Container;
import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageDecorator;

public class ProxyMethodMessageDecorator implements MessageDecorator {

	/**
	 * The method that will be invoked
	 */
	private final Method method;

	/**
	 * The bean that the interceptor method is located in. This must be service
	 * and cannot be component
	 */
	private final Bean<?> bean;
	
	/**
	 * The container of the bean
	 */
	private final Container container;

	/**
	 * @param method
	 * @param bean
	 * @param container
	 */
	public ProxyMethodMessageDecorator(Method method, Bean<?> bean, Container container) {
		this.method = method;
		this.bean = bean;
		this.container = container;
	}

	@Override
	public Object decorate(Message message, IncomingMessageDefinition definition) {
		try {
			return method.invoke(bean.get(null, container.manager()), message);
		} catch (Exception ex) {
			throw new InjectionException("exception occurred in " + method.getDeclaringClass().getName() + "." + method.getName(), ex.getCause());
		}
	}

}
