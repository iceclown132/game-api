package org.spartan.model.locale;

public class Region {

	/**
	 * 
	 */
	private static final int WIDTH = 8;
	
	/**
	 * 
	 */
	private static final int HEIGHT = 8;

	/**
	 * The x coordinate
	 */
	private final Location location;

	/**
	 * Gets a region off of the location
	 * 
	 * @param location2
	 * @return
	 */
	public static Region of(Location location) {
		return new Region(location.deflate(WIDTH, HEIGHT, 1));
	}

	/**
	 * @param x
	 * @param y
	 */
	public Region(int x, int y) {
		this.location = new Location(x, y);
	}

	/**
	 * @param x
	 * @param y
	 */
	private Region(Location location) {
		this.location = location;
	}

	/**
	 * Gets the 
	 * @param location2
	 * @return
	 */
	public Location local(Location location) {
		return location.subtract(this.location.inflate(WIDTH, HEIGHT, 1));
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * 
	 * @param other
	 * @return
	 */
	public Location offset(Location other) {
		return this.location.deflate(WIDTH, HEIGHT, 1).clone().subtract(other);
	}

}
