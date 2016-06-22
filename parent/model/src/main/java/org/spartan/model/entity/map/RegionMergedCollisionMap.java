package org.spartan.model.entity.map;

import java.util.Set;

public class RegionMergedCollisionMap implements CollisionMap {

	/**
	 * The collection of collision maps to choose from
	 */
	private final Set<CollisionMap> maps;

	/**
	 * @param maps
	 */
	public RegionMergedCollisionMap(Set<CollisionMap> maps) {
		this.maps = maps;
	}

	@Override
	public int getWidth() {
		return maps.size() * Region.WIDTH;
	}

	@Override
	public int getHeight() {
		return maps.size() * Region.HEIGHT;
	}

	@Override
	public boolean collides(int x, int y, int flag) {
		return false;
	}

}
