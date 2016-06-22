package org.spartan.model.entity.map;

import org.spartan.model.locale.Location;

public class GameObject {
	
	/**
	 * The location
	 */
	private final Location location = Location.NULL.clone();
	
	/**
	 * Id of the object
	 */
	private int id;

	/**
	 * The type of object
	 */
	private int type;
	
	/**
	 * The object's orientation
	 */
	private int orientation;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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
	 * @return
	 */
	public GameObjectDefinition getDefinition() {
		return GameObjectDefinition.get(id);
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the x
	 */
	public Location getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "RSObject [x=" + location.getX() + ", y=" + location.getY() + ", orientation=" + orientation + ", type=" + type + "]";
	}

}
