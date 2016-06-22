package org.spartan.route;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

import org.spartan.model.entity.map.Region;
import org.spartan.model.locale.Location;

public class Node implements Comparable<Node> {

	/**
	 * The location of the node
	 */
	private final Location location;
	
	/**
	 * The weight of this node
	 */
	private int weight;
	
	/**
	 * The next node in the route chain
	 */
	private Node link;
	
	/**
	 * 
	 */
	private boolean closed;

	/**
	 * @param location
	 */
	public Node(Location location) {
		this.location = location;
		this.weight = Integer.MAX_VALUE;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Node(int x, int y) {
		this (new Location(x, y));
	}
	
	/**
	 * 
	 * @param node
	 * @param nodes
	 * @return
	 */
	public Set<Node> adjacent(Queue<Node> nodes, Region region) {
		Set<Node> neighbours = new HashSet<>();
		Node[][] nodemap = map(region, nodes);
		
		/*
		 * Get the straight directions
		 */
		for (DirectionStraight direction : DirectionStraight.values()) {
			if (region.contains(location.clone().translate(direction.getOffset()))) {
				Node node = get(nodemap, location.clone().translate(direction.getOffset()), region);
				
				if (node != null
						&& !region.getCollisionMap().collides(location, direction.getCollisionFlag())
						&& !region.getCollisionMap().collides(node.getLocation(), direction.getOpposite().getCollisionFlag())) {
					neighbours.add(node);
				}
			}
		}
		
		/*
		 * Get the straight directions
		 */
		for (DirectionDiagonal direction : DirectionDiagonal.values()) {
			if (region.contains(location.clone().translate(direction.getOffset()))) {
				Node node_x = get(nodemap, location.clone().translate(new Location(direction.getOffset().getX(), 0, 0)), region);
				Node node_y = get(nodemap, location.clone().translate(new Location(0, direction.getOffset().getY(), 0)), region);
				Node node_destination = get(nodemap, location.clone().translate(direction.getOffset()), region);
				
				if (node_x != null && node_y != null && node_destination != null
						&& !region.getCollisionMap().collides(node_x.getLocation(), direction.getCollisionFlagX())
						&& !region.getCollisionMap().collides(node_y.getLocation(), direction.getCollisionFlagY())) {
					neighbours.add(node_destination);
				}
			}
		}
		return neighbours;
	}

	/**
	 * Gets node at location
	 * 
	 * @param nodes
	 * @param location
	 * @return
	 */
	private final Node[][] map(Region region, Queue<Node> nodes) {
		Node[][] map = new Node[region.getCollisionMap().getWidth()][region.getCollisionMap().getHeight()];
		nodes.forEach(node -> {
			Location location = node.getLocation().clone().subtract(region.getLocation().inflate());
			map[location.getX()][location.getY()] = node;
		});
		return map;
	}

	/**
	 * 
	 * @param array
	 * @param location
	 * @return
	 */
	private final <T> T get(T[][] array, Location location, Region region) {
		return array[location.getX() - region.getLocation().inflate().getX()][location.getY() - region.getLocation().inflate().getY()];
	}

	/**
	 * @return the weight
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * @return the link
	 */
	public Node getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(Node link) {
		this.link = link;
	}

	/**
	 * 
	 */
	public Node close() {
		this.closed = true;
		return this;
	}
	
	/**
	 * @return
	 */
	public boolean closed() {
		return closed;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable<T>#compareTo(T)
	 */
	@Override
	public int compareTo(Node o) {
		return Integer.compare(weight, o.weight);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Node [location=" + location + ", weight=" + weight + "]";
	}

}
