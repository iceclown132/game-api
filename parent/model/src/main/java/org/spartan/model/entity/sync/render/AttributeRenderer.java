package org.spartan.model.entity.sync.render;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.spartan.model.entity.DynamicEntity;

/**
 * @author brock
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeRenderer {
	
	/**
	 * Attribute the class will render out
	 * 
	 * @return
	 */
	int identifier();
	
	/**
	 * Attribute the class will render out
	 * 
	 * @return
	 */
	Class<? extends DynamicEntity> entity();
	
}
