package org.spartan.model.entity.item;

public class Item {

	/**
	 * The empty item
	 */
	public static final Item NULL = new Item(0, 0);

	/**
	 * The id of the item
	 */
	private final int id;
	
	/**
	 * The amount in the stack
	 */
	private final int amount;

	/**
	 * @param id
	 * @param amount
	 */
	public Item(int id, int amount) {
		this.id = id;
		this.amount = amount;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * 
	 * @return
	 */
	public ItemDefinition getDefinition() {
		return ItemDefinition.forId(id);
	}
	
	@Override
	public String toString() {
		return new StringBuilder("Item {\n").append("\titemId: ").append(id).append("\n").append("\tamount: ").append(amount).append("\n}").toString();
	}

}
