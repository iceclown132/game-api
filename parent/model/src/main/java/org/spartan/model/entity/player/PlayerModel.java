package org.spartan.model.entity.player;

import org.spartan.model.entity.Model;
import org.spartan.model.entity.ModelRenderer;
import org.spartan.model.entity.ReflectionModelRenderer;
import org.spartan.model.entity.sync.render.Attributes;
import org.spartan.model.entity.sync.render.Render;
import org.spartan.model.entity.sync.render.attribute.Appearance;
import org.spartan.model.entity.sync.waypoint.WaypointMetaData;

public class PlayerModel implements Model {

	/**
	 * The renderer for this class
	 */
	private final ModelRenderer renderer = new ReflectionModelRenderer(Player.class);
	
	/**
	 * The attributes
	 */
	private final Attributes attributes = new Attributes();

	/**
	 * The location
	 */
	private final WaypointMetaData location = new WaypointMetaData();
	
	/**
	 * The default render with the appearance block forcefully added
	 * @return
	 */
	public Render alternateRender() {
		if (!attributes.contains(Appearance.class)) {
			attributes.add(new Appearance());
		}
		return render();
	}

	@Override
	public Attributes attributes() {
		return attributes;
	}

	@Override
	public WaypointMetaData location() {
		return location;
	}

	@Override
	public Render render() {
		return renderer.render(this);
	}

	@Override
	public void reset() {
		attributes().clear();
	}
}
