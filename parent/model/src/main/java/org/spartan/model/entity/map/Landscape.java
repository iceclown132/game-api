package org.spartan.model.entity.map;

public class Landscape implements TiledMap {
	
	/**
	 * This map's identifier
	 */
	private final int identifier;

	/**
	 * The width of the map (in tiles)
	 */
	private final int width;
	
	/**
	 * The height of the map (in tiles)
	 */
	private final int height;
	
	/**
	 * The collection of tiles
	 */
	private final Tile[][][] tiles;

	/**
	 * @param identifier
	 * @param width
	 * @param height
	 * @param tiles
	 */
	public Landscape(int identifier, int width, int height, Tile[][][] tiles) {
		this.identifier = identifier;
		this.width = width;
		this.height = height;
		this.tiles = tiles;
	}

	@Override
	public Tile get(int x, int y, int z) {
		return tiles[x][y][z];
	}
	
	@Override
	public Tile[] get(int x, int y) {
		return tiles[x][y];
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
	
	public int getIdentifier() {
		return identifier;
	}

}
