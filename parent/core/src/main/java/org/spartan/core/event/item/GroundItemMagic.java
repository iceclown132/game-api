package org.spartan.core.event.item;

public class GroundItemMagic {

	/**
	 * The interface id
	 */
	private int interfaceId;

	/**
	 * The spell being used
	 */
	private int spell;
	
	/**
	 * The x of the item
	 */
	private int x;
	
	/**
	 * The y of the item
	 */
	private int y;
	
	/**
	 * The id
	 */
	private int id;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}

	/**
	 * @return the spell
	 */
	public int getSpell() {
		return spell;
	}

	/**
	 * @param spell the spell to set
	 */
	public void setSpell(int spell) {
		this.spell = spell;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

}
