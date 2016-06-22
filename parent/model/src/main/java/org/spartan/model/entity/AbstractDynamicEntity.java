package org.spartan.model.entity;

import org.spartan.model.locale.Location;
import org.spartan.model.locale.Region;

public abstract class AbstractDynamicEntity implements DynamicEntity {
	
	/**
	 * The location of the entity
	 */
	private final Location location = new Location();
	
	/**
	 * The location of the entity
	 */
	private final Location teleport = new Location(3222, 3222, 0);
	
	/**
	 * The region the entity is currently in
	 */
	private Region region;
	
	/**
	 * The entity's id
	 */
	private int id;

	/**
	 * @return the teleport
	 */
	public Location teleport() {
		return teleport;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the location
	 */
	public Location location() {
		return location;
	}

	/**
	 * @return the region
	 */
	public Region region() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void region(Region region) {
		this.region = region;
	}
	
}
