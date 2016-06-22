package org.spartan.net.message.attribute;

public class Attribute {

	/**
	 * The name of the field to be serialized
	 */
	private final String field;
	
	/**
	 * The type of the attribute
	 */
	private final AttributeType type;

	/**
	 * @param defaultValue
	 * @param field
	 * @param type
	 */
	public Attribute(String field, AttributeType type) {
		this.field = field;
		this.type = type;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return the type
	 */
	public AttributeType getType() {
		return type;
	}

}
