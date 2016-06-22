package org.spartan.model.entity.player;

import org.spartan.model.entity.sync.render.AbstractRenderedEntity;
import org.spartan.model.entity.sync.render.Render;
import org.spartan.model.entity.sync.waypoint.WaypointMetaData;

public class RenderedPlayer extends AbstractRenderedEntity<Player> {

	private final Render alternateRender;

	/**
	 * @param owner
	 * @param location
	 * @param render
	 */
	public RenderedPlayer(Player owner, Render render, Render alternateRender, WaypointMetaData location) {
		super(owner, render, location);
		
		this.alternateRender = alternateRender;
	}
	
	/**
	 * The default render with the appearance block forcefully added
	 * @return
	 */
	public Render getAlternateRender() {
		return alternateRender;
	}

}
