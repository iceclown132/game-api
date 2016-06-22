package org.spartan.cdi.scope.component;

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
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Component { }
