package org.spartan.cdi.scope.service;

import java.lang.annotation.Annotation;

import org.spartan.cdi.bean.AnnotationBeanScanner;
import org.spartan.cdi.bean.BeanScanner;
import org.spartan.cdi.scope.Scope;

public class ServiceScope implements Scope {

	@Override
	public Class<? extends Annotation> annotation() {
		return Service.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BeanScanner scanner() {
		return new AnnotationBeanScanner(annotation(), ServiceBean::new);
	}

}
