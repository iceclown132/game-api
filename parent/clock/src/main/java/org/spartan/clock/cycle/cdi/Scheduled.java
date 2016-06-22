package org.spartan.clock.cycle.cdi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Scheduled {

	/**
	 * The time between each cycle
	 * 
	 * @return
	 */
	long value() default 1L;

}
