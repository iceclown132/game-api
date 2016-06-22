package org.spartan.model.realm;

import java.util.LinkedList;
import java.util.List;

import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.sync.render.RenderedEntity;

/**
 * Contains all of the visible entities
 * 
 * @author brock
 *
 */
public class ViewScope {

	/**
	 * The list of visible players
	 */
	private final List<RenderedEntity<Player>> players = new LinkedList<>();

	/**
	 * @return the players
	 */
	public List<RenderedEntity<Player>> getPlayers() {
		return players;
	}

	/**
	 * @param other_player
	 * @return
	 */
	public boolean contains(Player other_player) {
		return players.stream().anyMatch(player -> player.getEntity() == other_player);
	}

	/**
	 * delegate of players.add(Player)
	 * 
	 * @param other_player
	 */
	public void add(RenderedEntity<Player> other_player) {
		players.add(other_player);
	}

}
