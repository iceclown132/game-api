package org.spartan.cdi.scope.component;

import java.lang.annotation.Annotation;

import org.spartan.cdi.bean.AnnotationBeanScanner;
import org.spartan.cdi.bean.BeanScanner;
import org.spartan.cdi.scope.Scope;

public class ComponentScope implements Scope {

	@Override
	public Class<? extends Annotation> annotation() {
		return Component.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanScanner scanner() {
		return new AnnotationBeanScanner(annotation(), SessionBean::new);
	}

}
