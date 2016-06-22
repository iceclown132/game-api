package org.spartan.model.entity.player.ui;

import org.spartan.model.entity.item.AbstractItemContainer;
import org.spartan.model.entity.item.Item;
import org.spartan.model.entity.item.StackingPolicy;
import org.spartan.model.entity.player.Player;

public class Equipment extends AbstractItemContainer {

	/**
	 * The size of the equipment interface
	 */
	public static final int SIZE = 14;

	/**
	 * The interface id
	 */
	public static final int INTERFACE_ID = 3214;

	/**
	 * The player for this equipment container
	 */
	private final Player player;

	/**
	 * @param size
	 * @param policy
	 */
	public Equipment(Player player) {
		super(SIZE, StackingPolicy.ALWAYS);
		this.player = player;
	}
	
	@Override
	protected void containerAltered() {
		player.model().attributes().add(player.appearance());
	}

	@Override
	public int add(Item item) {
		if (super.contains(item) && item.getDefinition().isStackable()) {
			super.add(item);
		}
		throw new UnsupportedOperationException("cannot add to container unless item is already present and stackable");
	}

	@Override
	public int remove(Item item) {
		throw new UnsupportedOperationException("cannot directly remove from container");
	}

}
