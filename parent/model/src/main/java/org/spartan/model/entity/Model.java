package org.spartan.model.entity;

import org.spartan.model.entity.sync.render.Attributes;
import org.spartan.model.entity.sync.render.Render;
import org.spartan.model.entity.sync.waypoint.WaypointMetaData;

public interface Model {

	/**
	 * 
	 * @return
	 */
	Attributes attributes();
	/**
	 * 
	 * @return
	 */
	WaypointMetaData location();
	
	/**
	 * Renders the model
	 * @return
	 */
	Render render();
	
	/**
	 * 
	 */
	void reset();

}
