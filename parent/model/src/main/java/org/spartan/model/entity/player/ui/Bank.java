package org.spartan.model.entity.player.ui;

import org.spartan.model.entity.item.AbstractItemContainer;
import org.spartan.model.entity.item.StackingPolicy;
import org.spartan.model.entity.player.Player;

public class Bank extends AbstractItemContainer {

	/**
	 * Size
	 */
	public static final int SIZE = 314;

	/**
	 * Size
	 */
	public static final int INTERFACE_ID = 321654987;

	/**
	 * The player
	 */
	private final Player player;
	
	/**
	 * 
	 */
	private final Inventory inventory;

	/**
	 * @param size
	 * @param policy
	 * @param player
	 */
	public Bank(Player player, Inventory inventory) {
		super(SIZE, StackingPolicy.ALWAYS);
		this.player = player;
		this.inventory = inventory;
	}

	@Override
	protected void containerAltered() {
		if (player != null && player.getOutputStream() != null) {
			player.getOutputStream().write(this);
			player.getOutputStream().write(inventory);
		}
	}

}
