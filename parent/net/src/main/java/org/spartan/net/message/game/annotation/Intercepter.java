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
public @interface Intercepter {

	/**
	 * The opcode of the message the interceptor method will look for. If the
	 * connection state is not GAME, this value will be ignored
	 * 
	 * @return
	 */
	int value();
	
	/**
	 * The size of the packet
	 * @return
	 */
	int size() default -1;

}
