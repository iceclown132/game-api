package org.spartan.model.entity.sync.contextual;

import java.util.Set;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.sync.render.RenderedEntity;

public interface SynchronizationContext<O extends DynamicEntity> {

	/**
	 * The entities that need to be updated
	 * 
	 * @return
	 */
	Set<RenderedEntity<O>> entities();

	/**
	 * The owner of the context
	 * @return
	 */
	Player getOwner();

}
