package org.spartan.net.message.game;

import java.lang.reflect.Field;
import java.util.Queue;

import org.spartan.cdi.inject.InjectionException;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageSerializer;
import org.spartan.net.message.OutgoingMessageDefinition;
import org.spartan.net.message.attribute.Attribute;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ReflectionMessageSerializer implements MessageSerializer {

	/**
	 * The collection of attributes
	 */
	private final Queue<Attribute> attributes;

	/**
	 * @param container
	 * @param attributes
	 */
	public ReflectionMessageSerializer(Queue<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Message serialize(Object msg, OutgoingMessageDefinition definition) {
		try {
			ByteBuf payload = ByteBufAllocator.DEFAULT.buffer();
			for (Attribute attribute : attributes) {
				Field field = definition.getModel().getDeclaredField(attribute.getField());
				field.setAccessible(true);
				attribute.getType().insert(payload, field.get(msg));
			}
			return new GameMessage(definition.getOpcode(), payload.writerIndex(), payload);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException exception) {
			throw new InjectionException("Reflection decoder could not parse message " + msg.getClass());
		}
	}

}
