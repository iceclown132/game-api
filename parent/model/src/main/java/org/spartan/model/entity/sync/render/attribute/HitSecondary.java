package org.spartan.model.entity.sync.render.attribute;

/**
 * Should be hidden from the user a bit
 * 
 * @author brock
 *
 */
public class HitSecondary extends AbstractHit {

	public HitSecondary(int amount) {
		super(amount);
	}

	public HitSecondary(int amount, DamageMarker sprite) {
		super(amount, sprite);
	}

}
