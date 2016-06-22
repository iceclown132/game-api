package org.spartan.model.user.serialization;

public class Item {

	/**
	 * Id of item
	 */
	private int item;
	
	/**
	 * Slot in container
	 */
	private int slot;
	
	/**
	 * Item stack amount
	 */
	private int amount;

	/**
	 * @return the item
	 */
	public int getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(int item) {
		this.item = item;
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
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
