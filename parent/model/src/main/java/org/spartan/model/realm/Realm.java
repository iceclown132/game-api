package org.spartan.model.realm;

import java.util.Set;

import org.spartan.model.entity.DynamicEntity;

public interface Realm {
	
	/**
	 * Gets the collection of entities for the given type
	 * 
	 * @param type
	 * @return
	 */
	<E extends DynamicEntity> Set<E> get(Class<E> type);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	<E extends DynamicEntity> void register(E entity);

	/**
	 * 
	 * @param entity
	 * @return
	 */
	<E extends DynamicEntity> void unregister(E entity);

	/**
	 * 
	 * @return
	 */
	boolean available();

}
