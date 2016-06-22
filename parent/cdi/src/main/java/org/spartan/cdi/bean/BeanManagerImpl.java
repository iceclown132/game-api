package org.spartan.cdi.bean;

import java.lang.annotation.Annotation;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.spartan.cdi.Container;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.inject.Instantiator;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.inject.reflect.FieldInjectionPoint;
import org.spartan.cdi.inject.reflect.SimpleClassInstantiator;
import org.spartan.cdi.inject.reflect.TypeInjector;
import org.spartan.cdi.scope.Context;
import org.spartan.cdi.scope.Scope;
import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.cdi.util.resource.Resource;
import org.spartan.cdi.util.resource.ResourceInjectionPoint;

@Service
public class BeanManagerImpl implements BeanManager {
	
	/**
	 * The static logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(BeanManager.class);

	/**
	 * The simple class instantiator
	 */
	private final Instantiator instantiator = new SimpleClassInstantiator();

	/**
	 * Collection of scopes
	 */
	private final Map<Class<? extends Annotation>, Scope> scopes = new HashMap<>();

	/**
	 * The collection of beans
	 */
	private final Map<Class<?>, Bean<?>> beans = new HashMap<>();
	
	/**
	 * The container for this class
	 */
	private final Container container;
	
	/**
	 * The properties file
	 */
	private final Properties properties = new Properties();
	
	/**
	 * @param container
	 */
	public BeanManagerImpl(Container container) {
		this.container = container;
	}

	@Override
	public void scan(Reflections reflections) throws Exception {
		/*
		 * Load this from somewhere else
		 */
		properties.load(Files.newInputStream(container.resource("application.properties")));
		
		/*
		 * Initialize all of the scopes
		 */
		reflections.getSubTypesOf(Scope.class).forEach(scopeType -> {
			Scope scope = instantiator.instantiate(scopeType);
			scopes.put(scope.annotation(), scope);
			logger.info("Registering scope {} for annotation {}", scopeType.getName(), scope.annotation().getName());
		});
		
		/*
		 * Find all the beans for their corresponding scope
		 */
		scopes.values().stream().forEach(scope -> scope.scanner().scan(this).forEach((type, bean) -> {
			if (!beans.containsKey(type)) {
				beans.put(type, bean);
			} else {
				throw new IllegalStateException("ambiguous bean for " + type + " - " + bean.getBeanClass() + "," + get(type).getBeanClass());
			}
			logger.info("Bean discovered - {} -> {}", type, bean.getBeanClass());
		}));
		
		/*
		 * Make this manager injectable
		 */
		associate(BeanManager.class, this, null);
	}

	@Override
	public <T> Bean<T> get(Class<T> beanClass) {
		if (!beans.containsKey(beanClass) || beans.get(beanClass) == null) {
			throw new NullPointerException("no bean found for " + beanClass.getName());
		}
		return ReflectionUtil.cast(beans.get(beanClass));
	}
	
	@Override
	public boolean beanExists(Class<?> beanClass) {
		return beans.containsKey(beanClass);
	}

	@Override
	public Scope getScope(Class<? extends Annotation> annotation) {
		if (!scopes.containsKey(annotation) || scopes.get(annotation) == null) {
			throw new NullPointerException("no scope found for " + annotation.getName());
		}
		return scopes.get(annotation);
	}
	
	@Override
	public <T> T getInjectedReference(Bean<T> bean, Context context) {
		return bean.get(context, this);
	}

	@Override
	public <T> T inject(Bean<T> bean, T partial, Context context) {
		return new TypeInjector<>(bean.getBeanClass(), this).inject(bean, partial, context);
	}

	/**
	 * TODO: Create annotation loaders
	 */
	@Override
	public Set<InjectionPoint> getInjectionPoints(Class<?> type) {
		Set<InjectionPoint> injectionPoints = new HashSet<>();
		injectionPoints.addAll(Arrays.stream(type.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Inject.class))
				.map(FieldInjectionPoint::new).collect(Collectors.toSet()));
		injectionPoints.addAll(Arrays.stream(type.getDeclaredFields()).filter(field -> field.isAnnotationPresent(Resource.class))
				.map(field -> new ResourceInjectionPoint(field, (String) properties.get(field.getAnnotation(Resource.class).value()))).collect(Collectors.toSet()));
		return injectionPoints;
	}
	
	@Override
	public <T> void associate(Class<T> type, T instance, Context context) {
		get(type).set(context, instance);
	}
	
	@Override
	public <T> T instance(Class<T> beanClass) {
		return instantiator.instantiate(beanClass);
	}
	
	@Override
	public boolean contains(Class<?> type, Context context) {
		try {
			return get(type).get(context, this) != null;
		} catch (Exception ex) {
			return false;
		}
	}

}
