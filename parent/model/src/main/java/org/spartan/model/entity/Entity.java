package org.spartan.model.entity;

import org.spartan.model.locale.Location;

/**
 * Entities are things that are represented graphically and have a location.
 * 
 * e.g. Ground items, objects, graphical effects, ...
 * 
 * @author brock
 *
 */
public interface Entity {

	/**
	 * The location of the entity on the worldmap
	 * 
	 * @return
	 */
	Location location();

	/**
	 * Gets the entity's unique id
	 * 
	 * @return
	 */
	int getId();
	
	/**
	 * Sets the id
	 * @param index
	 */
	void setId(int index);

}
