package org.spartan.model.entity;

import org.spartan.model.entity.sync.render.attribute.Animation;
import org.spartan.model.entity.sync.render.attribute.ChatForced;
import org.spartan.model.entity.sync.render.attribute.FaceEntity;
import org.spartan.model.entity.sync.render.attribute.FaceLocation;
import org.spartan.model.entity.sync.render.attribute.Graphic;
import org.spartan.model.entity.sync.render.attribute.HitPrimary;
import org.spartan.model.entity.sync.render.attribute.HitSecondary;
import org.spartan.model.entity.sync.render.attribute.Transformation;
import org.spartan.model.entity.sync.render.attribute.AbstractHit.DamageMarker;
import org.spartan.model.entity.sync.waypoint.WaypointVector;
import org.spartan.model.locale.Location;

/**
 * Dynamic entities are entities that have their own behavior and location
 * based stuffs like walking and what have you (Players and NPCs)
 * 
 * @author brock
 *
 */
public interface DynamicEntity extends Entity {

	/**
	 * The walking queue
	 * 
	 * @return
	 */
	WaypointVector waypoints();
	
	/**
	 * 
	 * @return
	 */
	Model model();
	
	/**
	 * Plays an animation
	 * 
	 * @param animation
	 */
	default void play(Animation animation) {
		model().attributes().add(animation);
	}
	
	/**
	 * Plays a graphic
	 * 
	 * @param graphic
	 */
	default void play(Graphic graphic) {
		model().attributes().add(graphic);
	}
	
	/**
	 * Makes this entity face the other entity
	 * 
	 * @param other
	 */
	default void face(DynamicEntity other) {
		model().attributes().add(new FaceEntity(other));
	}
	
	/**
	 * Makes this entity face the given target location
	 * 
	 * @param location
	 */
	default void face(Location target) {
		model().attributes().add(new FaceLocation(target));
	}
	
	/**
	 * Transforms this entity into one with the given id
	 * 
	 * @param other
	 */
	default void transform(int other) {
		model().attributes().add(new Transformation(other));
	}
	
	/**
	 * Renders a string above the player's head
	 * 
	 * @param text
	 */
	default void chat(String text) {
		model().attributes().add(new ChatForced(text));
	}
	
	/**
	 * 
	 * @param amount
	 * @param sprite
	 */
	default void displayHitmarker(int amount) {
		this.displayHitmarker(amount, amount == 0 ? DamageMarker.BLUE : DamageMarker.RED);
	}
	
	/**
	 * 
	 * @param amount
	 * @param sprite
	 */
	default void displayHitmarker(int amount, DamageMarker sprite) {
		if (model().attributes().contains(HitPrimary.class)) {
			model().attributes().add(new HitSecondary(amount, sprite));
		}
		else {
			model().attributes().add(new HitPrimary(amount, sprite));
		}
	}

}
