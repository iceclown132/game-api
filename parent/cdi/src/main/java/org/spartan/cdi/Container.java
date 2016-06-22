package org.spartan.cdi;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;

import org.reflections.Reflections;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.bean.BeanManagerImpl;
import org.spartan.cdi.event.BeanEvent;
import org.spartan.cdi.event.ContainerInitialized;
import org.spartan.cdi.event.Event;
import org.spartan.cdi.inject.Instance;
import org.spartan.cdi.inject.Instantiator;
import org.spartan.cdi.inject.annotation.Startup;
import org.spartan.cdi.inject.reflect.SimpleClassInstantiator;
import org.spartan.cdi.inject.reflect.SimpleInstance;
import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.cdi.util.ReflectionsInitializer;
import org.spartan.cdi.util.resource.ClassPathResourceHandler;
import org.spartan.cdi.util.resource.ResourceHandler;

@Service
public class Container {

	/**
	 * The reflections initializer
	 */
	private final Reflections reflections = ReflectionsInitializer.initialize();

	/**
	 * The bean manager implementation for this application
	 */
	private final BeanManager manager = new BeanManagerImpl(this);
	
	/**
	 * The instantiator
	 */
	private final Instantiator instantiator = new SimpleClassInstantiator();
	
	/**
	 * The resource handler
	 */
	private final ResourceHandler resources = new ClassPathResourceHandler();
	
	/**
	 * The instance implementation for this application
	 */
	private final Instance<Object> instance = new SimpleInstance<>(Object.class, ReflectionsInitializer.initialize());

	/**
	 * The event implementation for this application
	 */
	private Event<Object> event;

	/**
	 * Initializes all of the components in this container
	 */
	public void initialize() throws Exception {
		this.manager.scan(reflections);
		this.manager.associate(Container.class, this, null);
		this.event = BeanEvent.create(Object.class, this);
		this.event.select(ContainerInitialized.class).fire(new ContainerInitialized());
		for (Method method : reflections.getMethodsAnnotatedWith(Startup.class)) {
			try {
				method.invoke(Modifier.isStatic(method.getModifiers()) ? null : manager.get(method.getDeclaringClass()).get(null, manager), this);
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("method " + method.getDeclaringClass() + "." + method.getName() + " needs 1 argument of type org.spartan.cdi.Container");
			}
		}
	}

	/**
	 * @return
	 */
	public Instance<Object> instance() {
		return instance;
	}

	/**
	 * @param derived
	 * @return
	 */
	public <E> Instance<E> instance(Class<E> derived) {
		return instance.select(derived);
	}

	/**
	 * @param derived
	 * @return
	 */
	public <E> E instantiate(Class<E> type) {
		return instantiator.instantiate(type);
	}
	
	/**
	 * @return
	 */
	public Event<Object> event() {
		return event;
	}

	/**
	 * @param derived
	 * @return
	 */
	public <E> Event<E> event(Class<E> derived) {
		return event.select(derived);
	}
	
	/**
	 * Short for container.event(type).fire(instance);
	 * @param event
	 */
	public <E> void fire(E event) {
		this.event(event.getClass()).fire(ReflectionUtil.cast(event));
	}
	
	/**
	 * @return
	 */
	public BeanManager manager() {
		return manager;
	}
	
	/**
	 * @return
	 */
	public ResourceHandler resources() {
		return resources;
	}
	
	/**
	 * @return
	 * @throws Exception 
	 */
	public Path resource(String path) throws Exception {
		return resources.get(path);
	}

}
