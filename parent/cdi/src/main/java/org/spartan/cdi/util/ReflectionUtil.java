package org.spartan.cdi.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReflectionUtil {

	/**
	 * 
	 * @param type
	 * @return
	 */
	public static Set<Class<?>> getTypeClosure(Class<?> type) {
		Set<Class<?>> closure = new HashSet<>();
		closure.add(type);
		return getTypeClosure(type, closure);
	}

	/**
	 * 
	 * @param type
	 * @param set
	 * @return
	 */
	private static Set<Class<?>> getTypeClosure(Class<?> type, Set<Class<?>> set) {
		if (type != null && type != Object.class) {
			if (type.getSuperclass() != Object.class && type.getSuperclass() != null) {
				set.add(type.getSuperclass());
			}
			set.addAll(Arrays.asList(type.getInterfaces()));
			return getTypeClosure(type.getSuperclass(), set);
		} else return set;
	}

	/**
	 * Shortcut to cast objects
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object object) {
		return (T) object;
	}

}
