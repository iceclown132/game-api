package org.spartan.cdi.inject.reflect;

import java.lang.reflect.Field;

import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.inject.InjectionException;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Context;

public class FieldInjectionPoint implements InjectionPoint {

	/**
	 * The field that needs to be injected
	 */
	private final Field field;

	/**
	 * @param field
	 */
	public FieldInjectionPoint(Field field) {
		this.field = field;
		this.field.setAccessible(true);
	}

	@Override
	public void inject(Bean<?> bean, Object partial, Context context, BeanManager manager) {
		try {
			field.set(partial, manager.getInjectedReference(manager.get(field.getType()), context));
		} catch (Exception ex) {
			throw new InjectionException("could not inject " + bean.getClass().getName() + "." + field.getName(), ex);
		}
	}

}
