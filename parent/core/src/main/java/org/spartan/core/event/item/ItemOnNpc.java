package org.spartan.core.event.item;

public class ItemOnNpc {

	/**
	 * The npc id
	 */
	private int npcId;
	
	/**
	 * The interface id
	 */
	private int interfaceId;
	
	/**
	 * The item id
	 */
	private int itemId;
	
	/**
	 * slot of the item
	 */
	private int slot;

	/**
	 * @return the npcId
	 */
	public int getNpcId() {
		return npcId;
	}

	/**
	 * @param npcId the npcId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
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
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * @param slot the slot to set
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}

}
