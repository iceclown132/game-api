package org.spartan.model.entity.sync.contextual;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.realm.Realm;

public interface CollectiveContextualSynchronizer<C extends SynchronizationContext<E>, E extends DynamicEntity> extends ContextualSynchronizer<C, E> {

	/**
	 * Creates the context
	 * 
	 * @param realm
	 * @return
	 */
	C prepare(Realm realm);

	/**
	 * Synchronizes a single player for the given context
	 * 
	 * @param payer
	 */
	void synchronize(C context);
	
	/**
	 * Breaks down the synchronization process
	 * 
	 * @param realm
	 */
	default void synchronize(Realm realm) {
		C context = prepare(realm);
		synchronize(context);
		destroy(context);
	}

}
