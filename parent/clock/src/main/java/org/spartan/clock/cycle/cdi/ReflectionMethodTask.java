package org.spartan.clock.cycle.cdi;

import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.clock.Future;

public class ReflectionMethodTask implements Consumer<Future> {

	/**
	 * Ello
	 */
	private static final Logger logger = LogManager.getLogger(ReflectionMethodTask.class);

	/**
	 * The method lol
	 */
	private final Method method;

	/**
	 * The instance of the class that the method will be called on
	 */
	private final Object instance;

	/**
	 * @param method
	 * @param instance
	 */
	public ReflectionMethodTask(Method method, Object instance) {
		this.method = method;
		this.instance = instance;
	}

	@Override
	public void accept(Future future) {
		try {
			method.invoke(instance);
		} catch (Exception ex) {
			logger.warn("error during {}.{}, prevented from running again", method.getDeclaringClass().getName(), method.getName());
			throw new RuntimeException();
		}
	}

}
