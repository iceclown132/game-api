package org.spartan.model.entity.map;

import java.util.Set;

import org.spartan.model.locale.Location;

public class Region {

	/**
	 * The width of the regoin in tiles
	 */
	public static final int WIDTH = 64;
	
	/**
	 * The height of the region in tiles
	 */
	public static final int HEIGHT = 64;

	/**
	 * Amount of layers
	 */
	public static final int DEPTH = 4;

	/**
	 * The collection of game objects
	 */
	private final Set<GameObject> objects;
	
	/**
	 * The tiled map
	 */
	private final TiledMap map;
	
	/**
	 * The collision map
	 */
	private final CollisionMap collisionMap;
	
	/**
	 * The region's coordinate
	 */
	private final Location location;

	/**
	 * @param objects
	 * @param map
	 * @param collisionMap
	 */
	public Region(Set<GameObject> objects, TiledMap map, CollisionMap collisionMap, Location location) {
		this.objects = objects;
		this.map = map;
		this.collisionMap = collisionMap;
		this.location = location;
	}

	/**
	 * @return the objects
	 */
	public Set<GameObject> getObjects() {
		return objects;
	}

	/**
	 * @return the map
	 */
	public TiledMap getTiles() {
		return map;
	}

	/**
	 * @return the collisionMap
	 */
	public CollisionMap getCollisionMap() {
		return collisionMap;
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
		return this.location.clone().subtract(other);//new Location(x * WIDTH - location.getX(), y * HEIGHT - location.getY());
	}

	/**
	 * 
	 * @param translate
	 * @return
	 */
	public boolean contains(Location translate) {
		return translate.within(location.getX() * WIDTH, location.getY() * HEIGHT, WIDTH, HEIGHT);
	}

}
