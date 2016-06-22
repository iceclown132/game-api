package org.spartan.cdi.scope;

import java.lang.annotation.Annotation;

import org.spartan.cdi.bean.BeanScanner;

public interface Scope {

	/**
	 * Gets the scope's identifying annotation
	 * 
	 * @return
	 */
	Class<? extends Annotation> annotation();
	
	/**
	 * Gets the bean scanner
	 * 
	 * @return
	 */
	BeanScanner scanner();

}
