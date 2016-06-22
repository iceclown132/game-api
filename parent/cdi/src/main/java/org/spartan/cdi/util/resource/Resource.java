package org.spartan.cdi.util.resource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Components are classes that are instantiated once per session and can contain
 * session-bound injection points
 * 
 * @author brock
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Resource {

	/**
	 * The key
	 * 
	 * @return
	 */
	String value();

}
