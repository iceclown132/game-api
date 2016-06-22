package org.spartan.model.entity.sync.contextual;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.spartan.model.entity.player.IndividualPlayerSynchronizationContext;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.player.PlayerModel;
import org.spartan.model.entity.player.RenderedPlayer;
import org.spartan.model.entity.sync.render.RenderedEntity;

/**
 * Clean up this class?
 * 
 * @author brock
 *
 */
public abstract class AbstractIndividualPlayerContextualSynchronizer implements IndividualContextualSynchronizer<IndividualPlayerSynchronizationContext, Player> {
	
	/**
	 * Place where renders are stored per individual
	 */
	private final Map<Player, RenderedEntity<Player>> cachedRenders = new HashMap<>();
	
	/**
	 * Destroys all of the renders
	 * 
	 * @param player
	 * @return
	 */
	private RenderedEntity<Player> render(Player player) {
		try {
			if (cachedRenders.containsKey(player))
				return cachedRenders.get(player);
			RenderedEntity<Player> render = new RenderedPlayer(player, player.model().render(), ((PlayerModel) player.model()).alternateRender(), player.waypoints().update());
			cachedRenders.put(player, render);
			return render;
		} catch (Exception ex) {
			throw new RuntimeException("exception during player rendering", ex);
		}
	}

	@Override
	public IndividualPlayerSynchronizationContext prepare(Player player) {
		return new IndividualPlayerSynchronizationContext(player.getRealm().get(Player.class).stream()
				.filter(other -> other.location().distance(player.location()) < 16 && other != player)
				.map(this::render).collect(Collectors.toSet()), render(player));
	}

	@Override
	public void destroy(IndividualPlayerSynchronizationContext context) {
		context.entities().forEach(cachedRenders::remove);
	}

}
