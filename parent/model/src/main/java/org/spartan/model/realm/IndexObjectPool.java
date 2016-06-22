package org.spartan.model.realm;

import java.util.Set;
import java.util.TreeSet;

public class IndexObjectPool {

	/**
	 * The available indexes
	 */
	private final Set<Integer> available = new TreeSet<>();

	/**
	 * 
	 */
	public IndexObjectPool(int length) {
		if (length <= 0 || length > 2046) {
			throw new IllegalArgumentException("length of index pool must be between 1 and 2046");
		}
		for (int i = 1; i <= length; i++) {
			available.add(i);
		}
	}

	/**
	 * 
	 * @return
	 */
	public int next() {
		if (!available()) {
			throw new NullPointerException("no more available indexes");
		}
		int index = available.iterator().next();
		available.remove(index);
		return index;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean available() {
		return available.size() > 0;
	}

	/**
	 * 
	 * @param index
	 */
	public void push(int index) {
		if (available.contains(index)) {
			throw new IllegalStateException("index exists: " + index);
		}
		available.add(index);
	}

}
