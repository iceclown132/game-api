package org.spartan.core.event.item;

public class ItemClicked {

	/**
	 * The first item option
	 */
	public static final int ITEM_OPTION_1 = 0;
	
	/**
	 * The second item option
	 */
	public static final int ITEM_OPTION_2 = 1;
	
	/**
	 * The third item option
	 */
	public static final int ITEM_OPTION_3 = 2;
	
	/**
	 * The option that drops/destroys the item
	 */
	public static final int ITEM_DROPPED = 3;
	
	/**
	 * The option that wields the item
	 */
	public static final int ITEM_EQUIPPED = 4;
	
	/**
	 * The option that wields the item
	 */
	public static final int ITEM_UNEQUIPPED = 4;

	/**
	 * Option index
	 */
	private int option;
	
	/**
	 * Id of the interface
	 */
	private int interfaceId;

	/**
	 * Slot of the item that has been interacted with
	 */
	private int slot;
	
	/**
	 * The item id
	 */
	private int itemId;

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

}
