package org.spartan.route;

import org.spartan.model.entity.map.CollisionMap;
import org.spartan.model.locale.Location;

public enum DirectionStraight {

	/**
	 * 
	 */
	NORTH(new Location(0, 1), CollisionMap.NORTH),
	
	/**
	 *  
	 */
	EAST(new Location(1, 0), CollisionMap.EAST),
	
	/**
	 * 
	 */
	SOUTH(new Location(0, -1), CollisionMap.SOUTH),
	
	/**
	 * 
	 */
	WEST(new Location(-1, 0), CollisionMap.WEST);
	
	/**
	 * 
	 */
	private final Location offset;
	
	/**
	 * 
	 */
	private final int collisionFlag;

	/**
	 * @param offset
	 * @param collisionFlag
	 */
	private DirectionStraight(Location offset, int collisionFlag) {
		this.offset = offset;
		this.collisionFlag = collisionFlag;
	}

	/**
	 * @return the offset
	 */
	public Location getOffset() {
		return offset;
	}

	/**
	 * @return the collisionFlag
	 */
	public int getCollisionFlag() {
		return collisionFlag;
	}

	/**
	 * 
	 * @return
	 */
	public DirectionStraight getOpposite() {
		return values()[(this.ordinal() + 2) % DirectionStraight.values().length];
	}

}
