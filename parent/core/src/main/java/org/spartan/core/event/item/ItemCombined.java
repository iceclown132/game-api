package org.spartan.core.event.item;

public class ItemCombined {

	/**
	 * The interface id
	 */
	private int interfaceId;
	
	/**
	 * The primary id
	 */
	private int primaryId;
	
	/**
	 * The primary slot
	 */
	private int primarySlot;
	
	/**
	 * The secondary id
	 */
	private int secondaryId;
	
	/**
	 * The secondary slot
	 */
	private int secondarySlot;

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
	 * @return the primaryId
	 */
	public int getPrimaryId() {
		return primaryId;
	}

	/**
	 * @param primaryId the primaryId to set
	 */
	public void setPrimaryId(int primaryId) {
		this.primaryId = primaryId;
	}

	/**
	 * @return the primarySlot
	 */
	public int getPrimarySlot() {
		return primarySlot;
	}

	/**
	 * @param primarySlot the primarySlot to set
	 */
	public void setPrimarySlot(int primarySlot) {
		this.primarySlot = primarySlot;
	}

	/**
	 * @return the secondaryId
	 */
	public int getSecondaryId() {
		return secondaryId;
	}

	/**
	 * @param secondaryId the secondaryId to set
	 */
	public void setSecondaryId(int secondaryId) {
		this.secondaryId = secondaryId;
	}

	/**
	 * @return the secondarySlot
	 */
	public int getSecondarySlot() {
		return secondarySlot;
	}

	/**
	 * @param secondarySlot the secondarySlot to set
	 */
	public void setSecondarySlot(int secondarySlot) {
		this.secondarySlot = secondarySlot;
	}

}
