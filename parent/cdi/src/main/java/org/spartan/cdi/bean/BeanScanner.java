package org.spartan.cdi.bean;

import java.util.Map;

public interface BeanScanner {

	/**
	 * 
	 * @return
	 */
	Map<Class<?>, Bean<?>> scan(BeanManager manager);

}
