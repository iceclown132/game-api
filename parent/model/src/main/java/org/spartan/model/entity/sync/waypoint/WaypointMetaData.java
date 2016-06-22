package org.spartan.model.entity.sync.waypoint;

public class WaypointMetaData {
	
	/**
	 * The direction of the first processed waypoint
	 */
	private Waypoint primaryWaypoint;
	
	/**
	 * The direction of the second processed waypoint
	 */
	private Waypoint secondaryWaypoint;
	
	/**
	 * 
	 */
	private boolean teleported;
	
	/**
	 * map region changed
	 */
	private boolean mapRegionChanged;

	/**
	 * @return the primaryWaypoint
	 */
	public Waypoint getPrimaryWaypoint() {
		return primaryWaypoint;
	}

	/**
	 * @param primaryWaypoint the primaryWaypoint to set
	 */
	public void setPrimaryWaypoint(Waypoint primaryWaypoint) {
		this.primaryWaypoint = primaryWaypoint;
	}

	/**
	 * @return the secondaryWaypoint
	 */
	public Waypoint getSecondaryWaypoint() {
		return secondaryWaypoint;
	}

	/**
	 * @param secondaryWaypoint the secondaryWaypoint to set
	 */
	public void setSecondaryWaypoint(Waypoint secondaryWaypoint) {
		this.secondaryWaypoint = secondaryWaypoint;
	}

	/**
	 * @param mapRegionChanged the mapRegionChanged to set
	 */
	public void setMapRegionChanged(boolean mapRegionChanged) {
		this.mapRegionChanged = mapRegionChanged;
	}

	/**
	 * @return the mapRegionChanged
	 */
	public boolean hasMapRegionChanged() {
		return mapRegionChanged;
	}

	/**
	 * @return the teleported
	 */
	public boolean hasTeleported() {
		return teleported;
	}

	/**
	 * @param teleported the teleported to set
	 */
	public void setTeleported(boolean teleported) {
		this.teleported = teleported;
	}

}
