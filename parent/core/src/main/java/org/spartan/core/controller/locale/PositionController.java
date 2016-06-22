package org.spartan.core.controller.locale;

import org.spartan.cdi.event.Observes;
import org.spartan.cdi.scope.service.Service;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.sync.waypoint.RegionChanged;

/**
 * Controls everything to do with regions, movement and any of the sort
 * 
 * @author brock
 *
 */
@Service
public class PositionController {

	/**
	 * Called when the player's region changes
	 * 
	 * @param regions
	 * @param player
	 */
	public void on_region(@Observes RegionChanged regions, Player player) {
		player.getOutputStream().write(regions.getNewRegion());
	}

}
