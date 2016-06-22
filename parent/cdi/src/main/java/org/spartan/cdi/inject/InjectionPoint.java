package org.spartan.cdi.inject;

import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.bean.BeanManager;
import org.spartan.cdi.scope.Context;

public interface InjectionPoint {

	/**
	 * 
	 * @param bean
	 * @param partial
	 * @param context
	 */
	void inject(Bean<?> bean, Object partial, Context context, BeanManager manager);

}
