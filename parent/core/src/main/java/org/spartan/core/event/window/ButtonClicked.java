package org.spartan.core.event.window;

public class ButtonClicked {

	/**
	 * Id of the interface
	 */
	private int interfaceId;
	
	/**
	 * Id of the button
	 */
	private int id;

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

}
