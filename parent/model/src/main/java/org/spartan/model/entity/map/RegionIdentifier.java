package org.spartan.model.entity.map;

import org.spartan.model.locale.Location;

public class RegionIdentifier {

	/**
	 * The x coordinate of the region
	 */
	private final int x;
	
	/**
	 * The y coordinate of the region
	 */
	private final int y;

	/**
	 * @param identifier
	 */
	public static RegionIdentifier of(Location location) {
		return new RegionIdentifier(location.deflate().getX(), location.deflate().getY());
	}

	/**
	 * @param x
	 * @param y
	 */
	public RegionIdentifier(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param identifier
	 */
	public RegionIdentifier(int identifier) {
		this (identifier >> 8, identifier & 0xff);
	}

	/**
	 * Gets the absolute coordinates for the region
	 * @return
	 */
	public Location absolute() {
		return new Location(x * Region.WIDTH, y * Region.HEIGHT, 0);
	}
	
	/**
	 * Converts the given location to a set of local coordinates
	 * 
	 * @param location
	 * @return
	 */
	public Location localize(Location location) {
		return location.subtract(absolute());
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * @return the hashcode
	 */
	public int hashCode() {
		return (x << 8) | y;
	}

}
