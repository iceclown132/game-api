package org.spartan.model.entity.item;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractItemContainer implements ItemContainer {

	/**
	 * A collection of items
	 */
	private final Item[] items;
	
	/**
	 * Indicates this container always stacks
	 */
	private final StackingPolicy policy;

	/**
	 * @param items
	 */
	public AbstractItemContainer(int size, StackingPolicy policy) {
		this.items = new Item[size];
		this.policy = policy;
	}
	
	/**
	 * 
	 */
	protected abstract void containerAltered();

	/**
	 * 
	 * @param item
	 */
	private void set(Item search, Item item) {
		int index = indexOf(search);
		set(new Item(item.getId(), get(index).getAmount() + item.getAmount()), index);
	}

	@Override
	public int add(Item item) {
		try {
			if (item.getDefinition().isStackable() || policy == StackingPolicy.ALWAYS) {
				if (!contains(item) && !hasRemaining()) {
					return 0;
				}
				set(contains(item) ? item : Item.NULL, item);
				return item.getAmount();
			}
			else {
				int amount = count(item.getId());
				for (int i = 0; i < item.getAmount() && hasRemaining(); i++) {
					set(Item.NULL, new Item(item.getId(), 1));
				}
				return amount - count(item.getId());
			}
		} finally {
			containerAltered();
		}
	}

	@Override
	public int remove(Item item) {
		try {
			if (item.getDefinition().isStackable() || policy == StackingPolicy.ALWAYS) {
				if (!contains(item)) {
					return 0;
				}
				int index = indexOf(item);
				if (get(index).getAmount() - item.getAmount() <= 0) {
					int amount = get(index).getAmount();
					set(Item.NULL, index);
					return amount;
				}
				else {
					int amount = get(index).getAmount() - item.getAmount();
					set(new Item(item.getId(), get(index).getAmount() - item.getAmount()), index);
					return amount;
				}
			}
			else {
				int free = remaining();
				for (int i = 0; i < item.getAmount() && hasRemaining(); i++) {
					set(Item.NULL, indexOf(item));
				}
				return free - remaining();
			}
		} finally {
			containerAltered();
		}
	}
	
	@Override
	public void clear() {
		for (int i = 0; i < items.length; i++) {
			items[i] = null;
		}
		containerAltered();
	}

	@Override
	public boolean set(Item item, int slot) {
		if (item == null || item.getAmount() < 1) {
			items[slot] = Item.NULL;
		}
		else {
			items[slot] = item;
		}
		return true;
	}

	@Override
	public Item get(int index) {
		return items[index] == null ? Item.NULL : items[index];
	}

	@Override
	public int size() {
		return items.length;
	}

	@Override
	public Iterator<Item> iterator() {
		return get().iterator();
	}

	@Override
	public Stream<Item> get() {
		return Arrays.stream(items);
	}
	
	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append(" {\n")
				.append(Arrays.stream(items).map(item -> (item == null ? "null" : item.toString())).collect(Collectors.joining(",\n")))
				.append("\n}").toString();
	}
	
	public List<Item> items() {
		return Arrays.asList(items);
	}

}
