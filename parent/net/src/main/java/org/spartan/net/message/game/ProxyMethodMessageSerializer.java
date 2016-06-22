package org.spartan.net.message.game;

import java.lang.reflect.Method;

import org.spartan.cdi.Container;
import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageSerializer;
import org.spartan.net.message.OutgoingMessageDefinition;

public class ProxyMethodMessageSerializer implements MessageSerializer  {

	/**
	 * The method
	 */
	private final Method method;
	
	/**
	 * The bean
	 */
	private final Bean<?> bean;
	
	/**
	 * The container
	 */
	private final Container container;

	/**
	 * @param method
	 * @param bean
	 */
	public ProxyMethodMessageSerializer(Method method, Bean<?> bean, Container container) {
		this.method = method;
		this.bean = bean;
		this.container = container;
	}

	@Override
	public Message serialize(Object msg, OutgoingMessageDefinition definition) {
		try {
			return (Message) method.invoke(bean.get(null, container.manager()), msg);
		} catch (Exception e) {
			throw new InjectionException(method.getDeclaringClass() + "." + method.getName() + " could not serialize message", e);
		}
	}

}
