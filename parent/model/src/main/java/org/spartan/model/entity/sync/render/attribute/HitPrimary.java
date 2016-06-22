package org.spartan.model.entity.sync.render.attribute;

/**
 * Should be hidden from the user a bit
 * 
 * @author brock
 *
 */
public class HitPrimary extends AbstractHit {

	public HitPrimary(int amount) {
		super(amount);
	}

	public HitPrimary(int amount, DamageMarker sprite) {
		super(amount, sprite);
	}

}
