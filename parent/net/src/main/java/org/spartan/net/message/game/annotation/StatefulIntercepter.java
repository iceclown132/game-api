package org.spartan.net.message.game.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

/**
 * Components are classes that are instantiated once per session and can contain
 * session-bound injection points
 * 
 * @author brock
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatefulIntercepter {

	/**
	 * The connection state the interceptor will be listening at.
	 * 
	 * @return
	 */
	ConnectionState value();
	
	/**
	 * The size of the packet
	 * @return
	 */
	int size() default -1;

}
