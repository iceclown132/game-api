package org.spartan.model.entity.sync.render;

import java.lang.reflect.Method;

import org.spartan.model.entity.sync.SynchronizationException;

import io.netty.buffer.ByteBuf;

public class AttributeRendererMethod {

	/**
	 * The instance of the class that contains the render method
	 */
	private final Object instance;

	/**
	 * The method being rendered
	 */
	private final Method method;
	
	/**
	 * The mask lool
	 */
	private final int mask;

	/**
	 * @param attributeType
	 * @param instance
	 * @param method
	 */
	public AttributeRendererMethod(Object instance, Method method, int mask) {
		this.instance = instance;
		this.method = method;
		this.mask = mask;
	}

	/**
	 * 
	 * @param attribute
	 * @throws Exception
	 */
	public ByteBuf render(Attribute attribute) {
		try {
			return (ByteBuf) method.invoke(instance, attribute);
		} catch (Exception e) {
			throw new SynchronizationException("", e);
		}
	}

	/**
	 * @return the mask
	 */
	public int getMask() {
		return mask;
	}

	/**
	 * @return the instance
	 */
	public Object getInstance() {
		return instance;
	}

	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}

}
