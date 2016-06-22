package org.spartan.model.entity.sync.render;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Attributes implements Iterable<Attribute>, Supplier<Stream<Attribute>> {

	/**
	 * Collection of entity attributes
	 */
	private final List<Attribute> attributes = new LinkedList<>();

	/**
	 * Adds an attribute if no other attribute with the same class is already present
	 * 
	 * @param attribute
	 */
	public void add(Attribute attribute) {
		if (!contains(attribute.getClass())) {
			attributes.add(attribute);
		}
	}

	/**
	 * Checks to see if an attribute with the given class exists in the collection
	 * 
	 * @param attributeType
	 * @return
	 */
	public boolean contains(Class<? extends Attribute> attributeType) {
		return attributes.stream().anyMatch(other -> attributeType == other.getClass());
	}

	/**
	 * Removes the attribute with the given class
	 * 
	 * @param attribute
	 */
	public void remove(Class<? extends Attribute> attributeType) {
		attributes.removeAll(attributes.stream().filter(attribute -> attribute.getClass() == attributeType).collect(Collectors.toSet()));
	}
	
	/**
	 * Clears all of the attributes
	 */
	public void clear() {
		attributes.clear();
	}

	@Override
	public Iterator<Attribute> iterator() {
		return attributes.iterator();
	}

	@Override
	public Stream<Attribute> get() {
		return attributes.stream();
	}

}
