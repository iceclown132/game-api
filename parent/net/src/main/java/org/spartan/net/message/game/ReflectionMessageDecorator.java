package org.spartan.net.message.game;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Queue;

import org.spartan.cdi.Container;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageDecorator;
import org.spartan.net.message.attribute.Attribute;

public class ReflectionMessageDecorator implements MessageDecorator {

	/**
	 * The container for this decorator
	 */
	private final Container container;
	
	/**
	 * The collection of attributes
	 */
	private final Queue<Attribute> attributes;

	/**
	 * @param container
	 * @param attributes
	 */
	public ReflectionMessageDecorator(Container container, Queue<Attribute> attributes) {
		this.container = container;
		this.attributes = attributes;
	}

	@Override
	public Object decorate(Message msg, IncomingMessageDefinition definition) {
		try {
			Object instance = container.instantiate(definition.getModel());
			for (Iterator<Attribute> iterator = attributes.iterator(); iterator.hasNext(); ) {
				Attribute attribute = iterator.next();
				
				Field field = definition.getModel().getDeclaredField(attribute.getField());
				field.setAccessible(true);
				field.set(instance, attribute.getType().extract(msg.getPayload()));
			}
			return instance;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException exception) {
			throw new InjectionException("error while decorating message " + definition.getOpcode());
		}
	}

}
