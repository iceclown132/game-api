package org.spartan.model.entity.sync.contextual;

import java.util.Set;
import java.util.stream.Collectors;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.player.Player;
import org.spartan.model.realm.Realm;


/**
 * 
 * @author brock
 *
 * @param <E>
 */
public interface IndividualContextualSynchronizer<C extends SynchronizationContext<E>, E extends DynamicEntity> extends ContextualSynchronizer<C, E> {

	/**
	 * Prepares the synchronization context
	 * 
	 * @param entity
	 * @return
	 */
	C prepare(Player player);

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
		Set<C> contexts = realm.get(Player.class).stream().map(this::prepare).collect(Collectors.toSet());
		
		contexts.iterator().forEachRemaining(this::synchronize);
		contexts.iterator().forEachRemaining(this::destroy);
	}

}
