package org.spartan.model.entity.sync.render;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.sync.waypoint.WaypointMetaData;

public abstract class AbstractRenderedEntity<E extends DynamicEntity> implements RenderedEntity<E> {

	/**
	 * The entity that is rendered
	 */
	private final E owner;
	
	/**
	 * The render of the entity
	 */
	private final Render render;
	
	/**
	 * Location information
	 */
	private final WaypointMetaData location;

	/**
	 * @param owner
	 * @param location
	 * @param render
	 */
	public AbstractRenderedEntity(E owner, Render render, WaypointMetaData location) {
		this.owner = owner;
		this.render = render;
		this.location = location;
	}

	@Override
	public E getEntity() {
		return owner;
	}

	@Override
	public Render getRender() {
		return render;
	}
	
	@Override
	public WaypointMetaData getLocation() {
		return location;
	}
}
