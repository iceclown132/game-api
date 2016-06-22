package org.spartan.model.entity.sync.waypoint;

import org.spartan.model.locale.Location;

/**
 * 
 * @author brock
 *
 */
public interface WaypointVector {
	
	/**
	 * 
	 * @param destination
	 */
//	WaypointVector route(Location destination) throws RouteNotFoundException;
	
	/**
	 * Adds a new waypoint to the vector
	 * 
	 * @param waypoint
	 * @return
	 */
	WaypointVector add(int x, int y);
	
	/**
	 * Processes the next waypoint
	 * 
	 * @return
	 */
	WaypointMetaData update() throws Exception;
	
	/**
	 * Clears the vector so the entity has no more waypoints
	 */
	WaypointVector clear();
	
	/**
	 * dunno, was in hyperion
	 */
	WaypointVector finish();
	
	/**
	 * @param location
	 * @return
	 */
	default WaypointVector add(Location location) {
		return add(location.getX(), location.getY());
	}

}
