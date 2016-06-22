package org.spartan.model.entity.sync.contextual;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.sync.Synchronizer;

public interface ContextualSynchronizer<C extends SynchronizationContext<E>, E extends DynamicEntity> extends Synchronizer {

	/**
	 * Destroys the synchronization context for the player
	 * 
	 * @param entity
	 */
	void destroy(C context);

}
