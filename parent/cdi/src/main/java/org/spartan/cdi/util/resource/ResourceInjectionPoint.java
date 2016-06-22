package org.spartan.cdi.util.resource;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;

import com.google.common.primitives.Primitives;

public class ResourceInjectionPoint implements InjectionPoint {

	/**
	 * The collection of converters
	 */
	private static final Map<Class<?>, Converter> converters = new HashMap<>();

	/**
	 * Initializes the converters (FIXME: bad way of loading, needs maybe be configured from resource?)
	 */
	static {
		Arrays.stream(Type.values()).forEach(type -> converters.put(type.type, type.converter));
	}

	/**
	 * The field that needs to be injected
	 */
	private final Field field;
	
	/**
	 * The value of the resource that is being requested
	 */
	private final String value;

	/**
	 * @param field
	 * @param value
	 */
	public ResourceInjectionPoint(Field field, String value) {
		this.field = field;
		this.value = value;
	}

	@Override
	public void inject(Bean<?> bean, Object partial, Context context, BeanManager manager) {
		try {
			field.setAccessible(true);
			field.set(partial, converters.get(Primitives.wrap(field.getType())).convert(value));
		} catch (Exception ex) {
			throw new InjectionException("could not inject " + bean.getClass().getName() + "." + field.getName(), ex);
		}
	}

	/**
	 * The supported field types
	 * @author brock
	 *
	 */
	private static enum Type {
		STRING(String.class, string -> string),
		INTEGER(Integer.class, string -> Integer.parseInt(string)),
		BIGINTEGER(BigInteger.class, string -> new BigInteger(string, 16)),
		DOUBLE(Double.class, string -> Double.parseDouble(string)),
		LONG(Long.class, string -> Long.parseLong(string)),
		BOOLEAN(Boolean.class, string -> Boolean.parseBoolean(string));
		
		/**
		 * The type it represents
		 */
		private final Class<?> type;
		
		/**
		 * The converter that converts into the given type
		 */
		private final Converter converter;

		/**
		 * @param type
		 * @param converter
		 */
		private Type(Class<?> type, Converter converter) {
			this.type = type;
			this.converter = converter;
		}
		
	}
	
	/**
	 * 
	 * @author brock
	 *
	 */
	private static interface Converter {
		Object convert(String resource);
	}

}
