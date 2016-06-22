package org.spartan.cdi.bean;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.spartan.cdi.inject.InjectionPoint;
import org.spartan.cdi.scope.Scope;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.cdi.util.ReflectionsInitializer;

public class AnnotationBeanScanner implements BeanScanner {

	/**
	 * The reflections object for this scanner
	 */
	private static final Reflections reflections = ReflectionsInitializer.initialize();

	/**
	 * The annotation this scanner is looking for
	 */
	private final Class<? extends Annotation> annotation;

	/**
	 * The bean creator
	 */
	private final BeanCreator creator;

	/**
	 * @param annotation
	 * @param creator
	 */
	public AnnotationBeanScanner(Class<? extends Annotation> annotation, BeanCreator creator) {
		this.annotation = annotation;
		this.creator = creator;
	}

	@Override
	public Map<Class<?>, Bean<?>> scan(BeanManager manager) {
		Map<Class<?>, Bean<?>> beans = new HashMap<>();
		for (Class<?> type : reflections.getTypesAnnotatedWith(annotation)) {
			Bean<?> type_bean = creator.create(type, manager.getScope(annotation), manager.getInjectionPoints(type));

			for (Class<?> sub_class : ReflectionUtil.getTypeClosure(type)) {
				beans.put(sub_class, type_bean);
			}
		}
		return beans;
	}
	
	/**
	 * Creator of beans
	 * 
	 * @author brock
	 *
	 */
	public interface BeanCreator {

		/**
		 * Used to create the right type of bean
		 * 
		 * @param type
		 * @param injectionPoints
		 * @param scope
		 * @return
		 */
		Bean<?> create(Class<?> type, Scope scope, Set<InjectionPoint> injectionPoints);
	}

}
