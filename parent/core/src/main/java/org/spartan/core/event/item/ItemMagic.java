package org.spartan.core.event.item;

public class ItemMagic {

	/**
	 * The spell
	 */
	private int spellId;
	
	/**
	 * The item id
	 */
	private int itemId;
	
	/**
	 * The slot of the item
	 */
	private int slot;

	/**
	 * Id of the interface of the spellbook
	 */
	private int interfaceIdSpellbook;
	
	/**
	 * Id of the interface of the item
	 */
	private int interfaceIdItem;

	/**
	 * @return the spellId
	 */
	public int getSpellId() {
		return spellId;
	}

	/**
	 * @param spellId the spellId to set
	 */
	public void setSpellId(int spellId) {
		this.spellId = spellId;
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

	/**
	 * @return the interfaceIdSpellbook
	 */
	public int getInterfaceIdSpellbook() {
		return interfaceIdSpellbook;
	}

	/**
	 * @param interfaceIdSpellbook the interfaceIdSpellbook to set
	 */
	public void setInterfaceIdSpellbook(int interfaceIdSpellbook) {
		this.interfaceIdSpellbook = interfaceIdSpellbook;
	}

	/**
	 * @return the interfaceIdItem
	 */
	public int getInterfaceIdItem() {
		return interfaceIdItem;
	}

	/**
	 * @param interfaceIdItem the interfaceIdItem to set
	 */
	public void setInterfaceIdItem(int interfaceIdItem) {
		this.interfaceIdItem = interfaceIdItem;
	}

}
