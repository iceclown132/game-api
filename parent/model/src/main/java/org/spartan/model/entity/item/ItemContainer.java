package org.spartan.model.entity.item;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface ItemContainer extends Iterable<Item>, Supplier<Stream<Item>> {

	/**
	 * Adds an item or multiple items to the container
	 * 
	 * @param item
	 * @return the remaining amount to be added
	 */
	int add(Item item);

	/**
	 * Removes an item or multiple items from the container
	 * 
	 * @param item
	 * @return the amount of items added
	 */
	int remove(Item item);
	
	/**
	 * 
	 * @param item
	 * @param slot
	 * @return
	 */
	boolean set(Item item, int slot);
	
	/**
	 * Gets the item at the specified index
	 * 
	 * @param index
	 * @return
	 */
	Item get(int index);
	
	/**
	 * The size of the container
	 * 
	 * @return
	 */
	int size();
	
	/**
	 * Clears the inventory
	 */
	void clear();
	
	/**
	 * Gets the index of the first item
	 * 
	 * @param item
	 * @return
	 */
	default int indexOf(Item item) {
		return IntStream.range(0, size()).filter(index -> get(index).getId() == item.getId()).findFirst().orElse(-1);
	}
	
	/**
	 * Gets the index of the first item
	 * 
	 * @param item
	 * @return
	 */
	default int remaining() {
		return (int) get().filter(item -> item == null || item == Item.NULL).count();
	}
	
	/**
	 * has remaining
	 * @return
	 */
	default boolean hasRemaining() {
		System.out.println(remaining());
		return remaining() > 0;
	}
	
	/**
	 * Item contains 
	 * @param item
	 * @return
	 */
	default boolean contains(Item item) {
		return indexOf(item) > 0;
	}
	
	/**
	 * 
	 * @param item
	 * @return
	 */
	default int count(int id) {
		return get().filter(item -> item != null && item.getId() == id).mapToInt(item -> item == null ? 0 : item.getAmount()).sum();
	}

}
