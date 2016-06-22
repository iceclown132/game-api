package org.spartan.model.entity.player;

import java.util.Set;

import org.spartan.model.entity.sync.contextual.IndividualSynchronizationContext;
import org.spartan.model.entity.sync.render.RenderedEntity;

public class IndividualPlayerSynchronizationContext extends IndividualSynchronizationContext<Player> {
	
	/**
	 * The owner of this context
	 */
	private final RenderedEntity<Player> player;

	/**
	 * 
	 * @param entities
	 * @param player
	 * @param owner
	 */
	public IndividualPlayerSynchronizationContext(Set<RenderedEntity<Player>> entities, RenderedEntity<Player> player) {
		super(entities, player.getEntity());
		this.player = player;
	}

	/**
	 * @return the owner
	 */
	public RenderedEntity<Player> getPlayer() {
		return player;
	}

}
