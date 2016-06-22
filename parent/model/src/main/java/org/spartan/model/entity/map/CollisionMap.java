package org.spartan.model.entity.map;

import org.spartan.model.locale.Location;

/**
 * 
 * @author brock
 *
 */
public interface CollisionMap extends GameMap {

	/**
	 * Indicates the entity cannot walk to the west
	 */
	public static final int WEST = 0x80;

	/**
	 * Indicates the entity cannot walk to the north
	 */
	public static final int NORTH = 0x2;

	/**
	 * Indicates the entity cannot walk to the east
	 */
	public static final int EAST = 0x8;

	/**
	 * Indicates the entity cannot walk to the south
	 */
	public static final int SOUTH = 0x20;

	/**
	 * Indicates the entity cannot walk to the west
	 */
	public static final int NORTH_WEST = 0x1;

	/**
	 * Indicates the entity cannot walk to the east
	 */
	public static final int NORTH_EAST = 0x4;

	/**
	 * Indicates the entity cannot walk to the north
	 */
	public static final int SOUTH_EAST = 0x10;

	/**
	 * Indicates the entity cannot walk to the south
	 */
	public static final int SOUTH_WEST = 0x40;

	/**
	 * Indicates the entity cannot walk to the west
	 */
	public static final int WEST_SOLID = 0x10000;

	/**
	 * Indicates the entity cannot walk to the north
	 */
	public static final int NORTH_SOLID = 0x400;
	
	/**
	 * Indicates the entity cannot walk to the east
	 */
	public static final int EAST_SOLID = 0x1000;

	/**
	 * Indicates the entity cannot walk to the south
	 */
	public static final int SOUTH_SOLID = 0x4000;

	/**
	 * Indicates the entity cannot walk to the west
	 */
	public static final int NORTH_WEST_SOLID = 0x200;

	/**
	 * Indicates the entity cannot walk to the east
	 */
	public static final int NORTH_EAST_SOLID = 0x800;

	/**
	 * Indicates the entity cannot walk to the north
	 */
	public static final int SOUTH_EAST_SOLID = 0x2000;

	/**
	 * Indicates the entity cannot walk to the south
	 */
	public static final int SOUTH_WEST_SOLID = 0x8000;
	
	/**
	 * This flag indicates the entity traverse this tile
	 */
	public static final int IMPASSABLE_TERRAIN = 0x100;
	
	/**
	 * This flag indicates projectiles cannot traverse over the tile
	 */
	public static final int PROJECTILE_BLOCK = 0x20000;

	/**
	 * 
	 * @return
	 */
	static CollisionMap merge(Location source, Location destination) {
//		Region region = 
		
		return new RegionMergedCollisionMap(null);
	}

	/**
	 * 
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param flag
	 * @return
	 */
	boolean collides(int x, int y, int flag);

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param flag
	 * @return
	 */
	default boolean collides(Location location, int flag) {
		return this.collides(location.getX(), location.getY(), flag);
	}

}
