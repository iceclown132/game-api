package org.spartan.core.event.item;

public class ItemDragged {

	/**
	 * The id of the interface
	 */
	private int interfaceId;

	/**
	 * The slot where the item is being dragged from
	 */
	private int slotFrom;

	/**
	 * The item which is being dragged
	 */
	private int itemFrom;

	/**
	 * The slot to where the item is being dragged
	 */
	private int slotTo;
	
	/**
	 * The item id of the item it's being dragged to
	 */
	private int itemTo;

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
	 * @return the slotFrom
	 */
	public int getSlotFrom() {
		return slotFrom;
	}

	/**
	 * @param slotFrom the slotFrom to set
	 */
	public void setSlotFrom(int slotFrom) {
		this.slotFrom = slotFrom;
	}

	/**
	 * @return the itemFrom
	 */
	public int getItemFrom() {
		return itemFrom;
	}

	/**
	 * @param itemFrom the itemFrom to set
	 */
	public void setItemFrom(int itemFrom) {
		this.itemFrom = itemFrom;
	}

	/**
	 * @return the slotTo
	 */
	public int getSlotTo() {
		return slotTo;
	}

	/**
	 * @param slotTo the slotTo to set
	 */
	public void setSlotTo(int slotTo) {
		this.slotTo = slotTo;
	}

	/**
	 * @return the itemTo
	 */
	public int getItemTo() {
		return itemTo;
	}

	/**
	 * @param itemTo the itemTo to set
	 */
	public void setItemTo(int itemTo) {
		this.itemTo = itemTo;
	}

}
