package org.spartan.net.message.game.annotation;

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
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {
	
	/**
	 * The size of the packet
	 * @return
	 */
	boolean variableSize() default false;

}
