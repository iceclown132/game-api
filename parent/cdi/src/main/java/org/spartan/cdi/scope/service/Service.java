package org.spartan.cdi.scope.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Services are classes that are instantiated once per container and cannot
 * contain session-bound elements
 * 
 * @author brock
 *
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
}
