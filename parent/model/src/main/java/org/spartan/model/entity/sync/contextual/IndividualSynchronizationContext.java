package org.spartan.model.entity.sync.contextual;

import java.util.Set;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.sync.render.RenderedEntity;

public class IndividualSynchronizationContext<E extends DynamicEntity> implements SynchronizationContext<E> {

	/**
	 * The entities that are currently visible to the player
	 */
	private final Set<RenderedEntity<E>> entities;

	/**
	 * 
	 */
	private final Player owner;

	/**
	 * @param entities
	 * @param owner
	 */
	public IndividualSynchronizationContext(Set<RenderedEntity<E>> entities, Player owner) {
		this.entities = entities;
		this.owner = owner;
	}

	@Override
	public Set<RenderedEntity<E>> entities() {
		return entities;
	}

	@Override
	public Player getOwner() {
		return owner;
	}
}
