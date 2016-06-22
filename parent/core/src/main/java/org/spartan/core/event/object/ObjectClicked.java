package org.spartan.core.event.object;

public class ObjectClicked {

	/**
	 * The first option
	 */
	public static final int PRIMARY_INTERACTION = 0;
	
	/**
	 * The second option
	 */
	public static final int SECONDARY_INTERACTION = 1;
	
	/**
	 * index of the option
	 */
	private int option;

	/**
	 * The id of the object
	 */
	private int id;
	
	/**
	 * The x of the object
	 */
	private int x;
	
	/**
	 * The y of the object
	 */
	private int y;

	/**
	 * @return the option
	 */
	public int getOption() {
		return option;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(int option) {
		this.option = option;
	}

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
