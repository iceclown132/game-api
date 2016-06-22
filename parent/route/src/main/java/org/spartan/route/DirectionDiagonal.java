package org.spartan.route;

import org.spartan.model.entity.map.CollisionMap;
import org.spartan.model.locale.Location;

public enum DirectionDiagonal {

	/**
	 * 
	 */
	NORTH_WEST(new Location(-1, 1), CollisionMap.EAST | CollisionMap.NORTH | CollisionMap.NORTH_WEST, CollisionMap.EAST | CollisionMap.NORTH | CollisionMap.NORTH_WEST),
	
	/**
	 * 
	 */
	NORTH_EAST(new Location(1, 1), CollisionMap.WEST | CollisionMap.NORTH | CollisionMap.NORTH_EAST, CollisionMap.SOUTH | CollisionMap.EAST | CollisionMap.NORTH_EAST),
	
	/**
	 * 
	 */
	SOUTH_WEST(new Location(-1, -1), CollisionMap.SOUTH | CollisionMap.EAST | CollisionMap.SOUTH_WEST, CollisionMap.NORTH | CollisionMap.EAST | CollisionMap.SOUTH_WEST),
	
	/**
	 * 
	 */
	SOUTH_EAST(new Location(1, -1), CollisionMap.WEST | CollisionMap.SOUTH | CollisionMap.SOUTH_EAST, CollisionMap.NORTH | CollisionMap.EAST | CollisionMap.SOUTH_EAST);

	/**
	 * The offset location
	 */
	private final Location offset;

	/**
	 * 
	 */
	private final int collisionFlagX;
	
	/**
	 * 
	 */
	private final int collisionFlagY;

	/**
	 * @param offset
	 * @param collisionFlagX
	 * @param collisionFlagY
	 * @param collisionFlagDestination
	 */
	private DirectionDiagonal(Location offset, int collisionFlagX, int collisionFlagY) {
		this.offset = offset;
		this.collisionFlagX = collisionFlagX;
		this.collisionFlagY = collisionFlagY;
	}

	/**
	 * @return the offset
	 */
	public Location getOffset() {
		return offset;
	}

	/**
	 * @return the collisionFlagX
	 */
	public int getCollisionFlagX() {
		return collisionFlagX;
	}

	/**
	 * @return the collisionFlagY
	 */
	public int getCollisionFlagY() {
		return collisionFlagY;
	}

}
