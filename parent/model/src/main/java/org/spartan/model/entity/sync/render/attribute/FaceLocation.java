package org.spartan.model.entity.sync.render.attribute;

import org.spartan.model.entity.sync.render.Attribute;
import org.spartan.model.locale.Location;

public class FaceLocation implements Attribute {

	/**
	 * The location the entity is facing
	 */
	private final Location location;

	/**
	 * Constructor
	 * 
	 * @param location
	 */
	public FaceLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

}
