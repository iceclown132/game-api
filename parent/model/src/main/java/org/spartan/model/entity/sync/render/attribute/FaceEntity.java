package org.spartan.model.entity.sync.render.attribute;

import org.spartan.model.entity.DynamicEntity;
import org.spartan.model.entity.sync.render.Attribute;

public class FaceEntity implements Attribute {

	/**
	 * The entity being faced
	 */
	private final DynamicEntity entity;

	/**
	 * Constructor
	 * 
	 * @param entity
	 */
	public FaceEntity(DynamicEntity entity) {
		this.entity = entity;
	}

	/**
	 * @return the entity
	 */
	public DynamicEntity getEntity() {
		return entity;
	}

}
