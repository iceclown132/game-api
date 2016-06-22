package org.spartan.cdi.event;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.spartan.cdi.Container;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.cdi.util.ReflectionsInitializer;

public class BeanEvent<T> extends AbstractEvent<T> {

	/**
	 * lol another one
	 */
	private static final Reflections reflections = ReflectionsInitializer.initialize();
	
	/**
	 * The logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(BeanEvent.class);

	/**
	 * The container
	 */
	private final Container container;

	/**
	 * @param observedType
	 * @param observers
	 * @param container
	 * @param bean
	 */
	public BeanEvent(Class<T> observedType, Set<ObserverMethod<T>> observers, Container container) {
		super(observedType, observers);
		this.container = container;
	}

	/**
	 * 
	 * @param class1
	 * @return
	 */
	public static <T> Event<T> create(Class<T> type, Container container) {
		Set<ObserverMethod<T>> methods = new HashSet<>();
		for (Method method : reflections.getMethodsWithAnyParamAnnotated(Observes.class)) {
			Class<T> observedType = getObservedParameterType(method);
			if (Modifier.isStatic(method.getModifiers())) {
				methods.add(new StaticObserverMethod<>(observedType, method, container, null));
			}
			else {
				methods.add(new InstanceObserverMethod<>(observedType, method, container, container.manager().get(method.getDeclaringClass())));
			}
			logger.info("Found observer method - {}.{} listening to {}", method.getDeclaringClass().getName(), method.getName(), observedType.getName());
		}
		return new BeanEvent<>(type, methods, container);
	}
	
	/**
	 * 
	 * @param method
	 * @return
	 */
	private static <T> Class<T> getObservedParameterType(Method method) {
		for (Parameter parameter : method.getParameters()) {
			if (parameter.isAnnotationPresent(Observes.class)) {
				return ReflectionUtil.cast(parameter.getType());
			}
		}
		throw new EventException("observer found but no observed parameter detected (" + method.getDeclaringClass().getName() + "." + method.getName() + ")");
	}

	@Override
	public <E extends T> Event<E> select(Class<E> derived) {
		return new BeanEvent<>(derived, observer_methods.stream().filter(method -> method.getObservedType().isAssignableFrom(derived))
						.map(method -> ReflectionUtil.<ObserverMethod<E>> cast(method)).collect(Collectors.toSet()), container);
	}

}
