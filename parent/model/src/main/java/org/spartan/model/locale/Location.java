package org.spartan.model.locale;

/**
 * The location of an entity on the map
 * 
 * @author brock
 */
public class Location {
	
	/**
	 * Landscape width in tiles
	 */
	private static final int LANDSCAPE_WIDTH = 64;
	
	/**
	 * The height of a region
	 */
	private static final int LANDSCAPE_HEIGHT = 64;


	/**
	 * The null location
	 */
	public static final Location NULL = new Location(0, 0, 0);
	
	/**
	 * The coordinate values
	 */
	private int x, y, z;
	
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public static Location of(int identifier, int range) {
		if (range != 8 && range != 16)
			throw new IllegalArgumentException("range must be 8 or 16");
		return new Location(identifier >> range, identifier & (range == 8 ? 0xff : 0xffff));
	}
	
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public static Location of(int identifier) {
		return Location.of(identifier, 8);
	}

	/**
	 * Calculates the manhattan distance between 2 points
	 * 
	 * @param location
	 * @return
	 */
	public static int manhattan_distance(Location location1, Location location2) {
		return Math.abs(location2.x - location1.x) + Math.abs(location2.y - location1.y);
	}

	/**
	 * Calculates the manhattan distance between 2 points
	 * 
	 * @param location
	 * @return
	 */
	public static int euclidean_distance(Location location1, Location location2) {
		return (int) Math.sqrt(Math.pow(location2.y - location1.y, 2) + Math.pow(location2.x - location1.x, 2));
	}

	/**
	 * 
	 */
	public Location() {
	}

	/**
	 * @param location
	 */
	public Location(Location location) {
		this(location.x, location.y, location.z);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 */
	public Location(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Translates the location
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Location translate(int x, int y, int z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Location translate(Location location) {
		return this.translate(location.x, location.y, location.z);
	}

	/**
	 * Translates the location
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Location subtract(int x, int y, int z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * 
	 * @param location
	 * @return
	 */
	public Location subtract(Location location) {
		return subtract(location.x, location.y, location.z);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Location transform(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * 
	 * @param other
	 */
	public Location transform(Location other) {
		return this.transform(other.x, other.y, other.z);
	}

	/**
	 * @param other
	 */
	public Location difference(Location other) {
		return new Location(other).subtract(this);
	}

	/**
	 * @return
	 */
	public Location region() {
		return new Location(x / 8, y / 8);
	}
	
	/**
	 * Clones the location
	 */
	public Location clone() {
		return new Location(x, y, z);
	}

	/**
	 * Calculates the manhattan distance between 2 points
	 * 
	 * @param location
	 * @return
	 */
	public int distance(Location location) {
		return Location.manhattan_distance(this, location);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 */
	public boolean within(int x, int y, int width, int height) {
		return this.x >= x && this.y >= y && this.x < x + width && this.y < y + height;
	}

	/**
	 * Gets a condensed view of the location
	 * 
	 * @return
	 */
	public int condensed() {
		return (x << 8) | y;
	}

	/**
	 * @return
	 */
	public Location inflate() {
		return inflate(LANDSCAPE_WIDTH, LANDSCAPE_HEIGHT, 1);
	}

	/**
	 * @return
	 */
	public Location inflate(int x, int y, int z) {
		return new Location(this.x * x, this.y * y, this.z * z);
	}

	/**
	 * @return
	 */
	public Location deflate() {
		return this.deflate(LANDSCAPE_WIDTH, LANDSCAPE_HEIGHT, 1);
	}

	/**
	 * @return
	 */
	public Location deflate(int x, int y, int z) {
		return new Location(this.x / x, this.y / y, this.z / z);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @param z the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean equals(int x, int y, int z) {
		return this.equals(new Location(x, y, z));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Location [" + x + "," + y + "," + z + "]";
	}

}
