package org.spartan.model.entity.sync.render;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.sync.waypoint.WaypointMetaData;

public interface RenderedEntity<E extends DynamicEntity> {

	/**
	 * 
	 * @return
	 */
	E getEntity();
	
	/**
	 * The location meta data
	 * 
	 * @return
	 */
	WaypointMetaData getLocation();
	
	/**
	 * 
	 * @return
	 */
	Render getRender();

}
