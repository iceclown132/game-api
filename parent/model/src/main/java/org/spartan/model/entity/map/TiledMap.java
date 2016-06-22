package org.spartan.model.entity.map;

public interface TiledMap extends GameMap {

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	Tile get(int x, int y, int z);

	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	Tile[] get(int x, int y);

}
