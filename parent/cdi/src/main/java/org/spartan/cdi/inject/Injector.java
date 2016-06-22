package org.spartan.cdi.inject;

import org.spartan.cdi.bean.Bean;
import org.spartan.cdi.scope.Context;

/**
 * 
 * @author brock
 *
 * @param <T>
 */
public interface Injector<T> {

	/**
	 * 
	 * @param bean
	 * @param container
	 * @return
	 */
	T inject(Bean<T> bean, T partial, Context context);

}
