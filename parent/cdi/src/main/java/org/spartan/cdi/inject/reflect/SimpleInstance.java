package org.spartan.cdi.inject.reflect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.spartan.cdi.inject.Instance;
import org.spartan.cdi.inject.Instantiator;
import org.spartan.cdi.util.ReflectionUtil;

import javassist.Modifier;

/**
 * 
 * @author brock
 *
 * @param <T>
 */
public class SimpleInstance<T> implements Instance<T> {
	
	/**
	 * The instantiator for this class
	 */
	private static final Instantiator instantiator = new SimpleClassInstantiator();
	
	/**
	 * The collection of all object instances
	 */
	private final Map<Class<?>, Object> repository;

	/**
	 * The collection of all child classes
	 */
	private final Set<Class<? extends T>> children;
	
	/**
	 * The parent type of this instance
	 */
	private final Class<T> type;

	/**
	 * @param types
	 * @param container
	 */
	public SimpleInstance(Class<T> type, Reflections reflections) {
		this.children = reflections.getSubTypesOf(type);
		this.repository = new HashMap<>();
		this.type = type;
	}
	
	/**
	 * @param type
	 * @param children
	 * @param repository
	 */
	private SimpleInstance(Class<T> type, Set<Class<? extends T>> children, Map<Class<?>, Object> repository) {
		this.children = children;
		this.repository = repository;
		this.type = type;
	}

	@Override
	public <E extends T> Instance<E> select(Class<E> child) {
		Set<Class<? extends E>> set = new HashSet<>();
		for (Class<? extends T> type : children.stream().filter(type -> child.isAssignableFrom(type)).collect(Collectors.toSet())) {
			set.add(ReflectionUtil.cast(type));
		}
		return new SimpleInstance<E>(child, set, repository);
	}

	@Override
	public T get() {
		if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
			throw new NullPointerException("cannot instantiate an abstract class or an interface");
		}
		return get(type);
	}
	
	@Override
	public Iterator<T> iterator() {
		return ReflectionUtil.cast(children.stream().filter(type -> !type.isInterface() && !Modifier.isAbstract(type.getModifiers()))
				.map(child -> get(child)).collect(Collectors.toSet()).iterator());
	}

	@Override
	public Set<Class<? extends T>> getTypeClosure() {
		return children;
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private <K> K get(Class<K> type) {
		if (!repository.containsKey(type)) {
			repository.put(type, instantiator.instantiate(type));
		}
		return ReflectionUtil.cast(repository.get(type));
	}

}
