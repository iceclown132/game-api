package org.spartan.cdi.inject.reflect;

import java.lang.reflect.Constructor;

import org.spartan.cdi.inject.Instantiator;

public class SimpleClassInstantiator implements Instantiator {

	@Override
	public <T> T instantiate(Class<T> type) {
		try {
			Constructor<T> constructor = type.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException("could not instantiate class " + type.getName(), e);
		}
	}

}
