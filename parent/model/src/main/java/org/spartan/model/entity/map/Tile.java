package org.spartan.model.entity.map;

import org.spartan.model.locale.Location;

public class Tile {
	
	/**
	 * The location of the tile
	 */
	private final Location location = Location.NULL.clone();
	
	/**
	 * The height of the tile
	 */
	private int height;
	
	/**
	 * The texture used on the tile
	 */
	private int texture;
	
	/**
	 * The color of the tile
	 */
	private int color;
	
	/**
	 * The collision
	 */
	private int collisionHints;
	
	/**
	 * The orientation of the tile
	 */
	private int orientation;
	
	/**
	 * The rendering hints
	 */
	private int renderingHints;
	
	/**
	 * The underlaying texture id
	 */
	private int underlayTextureId;

	/** 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Tile(int x, int y, int z) {
		this(new Location(x, y, z));
	}

	/**
	 * @param location
	 */
	public Tile(Location location) {
		this.location.transform(location);
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return the texture
	 */
	public int getTexture() {
		return texture;
	}

	/**
	 * @param texture the texture to set
	 */
	public void setTexture(int texture) {
		this.texture = texture;
	}

	/**
	 * @return the orientation
	 */
	public int getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the renderingHints
	 */
	public int getRenderingHints() {
		return renderingHints;
	}

	/**
	 * @param renderingHints the renderingHints to set
	 */
	public void setRenderingHints(int renderingHints) {
		this.renderingHints = renderingHints;
	}

	/**
	 * @return the underlayTextureId
	 */
	public int getUnderlayTextureId() {
		return underlayTextureId;
	}

	/**
	 * @param underlayTextureId the underlayTextureId to set
	 */
	public void setUnderlayTextureId(int underlayTextureId) {
		this.underlayTextureId = underlayTextureId;
	}

	/**
	 * @return the collisionHints
	 */
	public int getCollisionHints() {
		return collisionHints;
	}

	/**
	 * @param collisionHints the collisionHints to set
	 */
	public void setCollisionHints(int collisionHints) {
		this.collisionHints = collisionHints;
	}

	/**
	 * @return the x
	 */
	public Location getLocation() {
		return location;
	}

}
