package org.spartan.route;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import org.spartan.model.entity.map.CollisionMap;
import org.spartan.model.entity.map.Region;
import org.spartan.model.locale.Location;

public interface RouteFinder {

	/**
	 * 
	 * @param map
	 * @param source
	 * @param destination
	 * @return
	 */
	List<Node> find(Region map, Location source, Location destination);

	/**
	 * Walks the chain of the node
	 * 
	 * @param node
	 * @return
	 */
	default List<Node> extract(Node node) {
		return extract(node, new LinkedList<>());
	}

	/**
	 * Walk a node and add the links to the list
	 * 
	 * @param node
	 * @param chain
	 * @return
	 */
	default List<Node> extract(Node node, LinkedList<Node> chain) {
		/*
		 * Add the node's point
		 */
		chain.addFirst(node);
		
		/*
		 * If there is no link left in the chain, return route
		 */
		if (node.getLink() == null) {
			return chain; //new Route(chain);
		}
		
		/*
		 * If there is a link present, keep walking until we find head of the chain
		 */
		return extract(node.getLink(), chain);
	}


	/**
	 * Maps the region into a collection of nodes
	 * 
	 * @param region
	 * @return
	 */
	default Queue<Node> map(Region region) {
		Queue<Node> nodes = new PriorityQueue<Node>();
		
		/*
		 * Collision map
		 */
		for (int x = 0; x < region.getCollisionMap().getWidth(); x++) {
			for (int y = 0; y < region.getCollisionMap().getHeight(); y++) {
				Location location = region.getLocation().inflate().translate(x, y, 0);
				if (!region.getCollisionMap().collides(location.getX(), location.getY(), CollisionMap.IMPASSABLE_TERRAIN)) {
					nodes.add(new Node(location));
				}
			}
		}
		
		return nodes;
	}
	
	/**
	 * Maps the region into a collection of nodes
	 * 
	 * @param region
	 * @return
	 */
	default Queue<Node> extract(Queue<Node> nodes, Location location) {
		Queue<Node> available = new PriorityQueue<>();
		available.addAll(nodes.stream().filter(node -> node.getLocation().equals(location)).collect(Collectors.toSet()));
		available.forEach(node -> node.setWeight(0));
		return available;
	}

}
