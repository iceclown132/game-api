 package org.spartan.model.entity.sync.waypoint;

import org.spartan.model.entity.player.Player;
import org.spartan.model.locale.Location;
import org.spartan.model.locale.Region;

public class PlayerWaypointVector extends AbstractWaypointVector<Player> {

//	/**
//	 * The route finder for this class (A* default)
//	 */
//	private static final RouteFinder ROUTE_FINDER = new AStarRouteFinder();

	/**
	 * @param entity
	 */
	public PlayerWaypointVector(Player entity) {
		super(entity);
	}

//	@Override
//	public WaypointVector route(Location destination) throws RouteNotFoundException {
//		// Load region landscape
//		
//		// Create the matrix
//		
//		// Find route
////		Route route = ROUTE_FINDER.find(null, super.entity.location(), destination);
////		route.forEach(node -> super.add(node.getX(), node.getY()));
//		return this;
//	}
	
	@Override
	public WaypointMetaData update() throws Exception {
		WaypointMetaData data = new WaypointMetaData();
		
		/*
		 * The points which we are walking to.
		 */
		Region old = entity.region(); //new Region(entity.location());
		
		/*
		 * Check to see if the player is teleporting or not
		 */
		if(entity.teleport() != null && !entity.teleport().equals(Location.NULL)) {
			/*
			 * After a teleport, the walking queue resets
			 */
			clear();
			
			/*
			 * change the player's location to that of the teleport target
			 */
			entity.location().transform(entity.teleport());
			entity.teleport().transform(Location.NULL);
			data.setTeleported(true);
		} else {
			/*
			 * If the player isn't teleporting, they are walking (or standing
			 * still). We get the next direction of movement here.
			 */
			data.setPrimaryWaypoint(super.getNextPoint());

			/*
			 * Technically we should check for ctrl+click running here. But we don't.
			 */
//			if (runToggled || runQueue) {
			if (super.entity.getUserInterface().getSettings().isRunning()) {
				data.setSecondaryWaypoint(super.getNextPoint());
			}
		}

		/*
		 * Calculate the distance between the two tiles
		 */
		int diff_x = old.offset(entity.location()).getX();
		int diff_y = old.offset(entity.location()).getY();
		
		/*
		 * Set the map region changed flag
		 */
		if (diff_x < -32 || diff_x >= 40 || diff_y < -32 || diff_y >= 40) {
			super.entity.region(Region.of(entity.location()));
			super.entity.notify(entity.region());
		}
		return data;
	}

}
