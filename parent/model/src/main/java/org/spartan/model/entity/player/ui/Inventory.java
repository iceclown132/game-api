package org.spartan.model.entity.player.ui;

import org.spartan.model.entity.item.AbstractItemContainer;
import org.spartan.model.entity.item.StackingPolicy;
import org.spartan.model.entity.player.Player;

/**
 * 
 * @author brock
 *
 */
public class Inventory extends AbstractItemContainer {

	/**
	 * The size of the inventory
	 */
	public static final int SIZE = 4;

	/**
	 * The interface id
	 */
	public static final int INTERFACE_ID = 3214;
	
	/**
	 * The player
	 */
	private final Player player;

	/**
	 * 
	 * @param size
	 * @param policy
	 */
	public Inventory(Player player) {
		super(SIZE, StackingPolicy.WHEN_REQUIRED);
		this.player = player;
	}

	@Override
	protected void containerAltered() {
		if (player != null && player.getOutputStream() != null)
			player.getOutputStream().write(this);
	}

}
