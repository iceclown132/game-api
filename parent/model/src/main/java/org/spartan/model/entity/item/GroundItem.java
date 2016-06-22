package org.spartan.model.entity.item;

import org.spartan.model.entity.Entity;
import org.spartan.model.locale.Location;

public class GroundItem implements Entity {

	/**
	 * The id of the item
	 */
	private final int id;
	
	/**
	 * The item itself
	 */
	private final Item item;
	
	/**
	 * The location of the item
	 */
	private final Location location;

	/**
	 * @param id
	 * @param item
	 * @param location
	 */
	public GroundItem(int id, Item item, Location location) {
		this.id = id;
		this.item = item;
		this.location = location;
	}

	@Override
	public Location location() {
		return location;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int index) {
		throw new UnsupportedOperationException("cannot change the id of a ground item");
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

}
