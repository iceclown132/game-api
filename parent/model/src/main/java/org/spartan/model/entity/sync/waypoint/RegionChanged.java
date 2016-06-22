package org.spartan.model.entity.sync.waypoint;

import org.spartan.model.locale.Region;

public class RegionChanged {

	/**
	 * The old region
	 */
	private final Region oldRegion;

	/**
	 * The new region
	 */
	private final Region newRegion;

	/**
	 * @param oldRegion
	 * @param newRegion
	 */
	public RegionChanged(Region oldRegion, Region newRegion) {
		this.oldRegion = oldRegion;
		this.newRegion = newRegion;
	}

	/**
	 * @return the oldRegion
	 */
	public Region getOldRegion() {
		return oldRegion;
	}

	/**
	 * @return the newRegion
	 */
	public Region getNewRegion() {
		return newRegion;
	}

}
