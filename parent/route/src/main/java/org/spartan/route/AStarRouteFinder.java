package org.spartan.route;

import java.util.Collections;
import java.util.List;
import java.util.Queue;

import org.spartan.model.entity.map.Region;
import org.spartan.model.locale.Location;

/**
 * function A*(start,goal)
 *     closedset := the empty set 	// The set of nodes already evaluated.
 *     openset := {start}    		// The set of tentative nodes to be evaluated, initially containing the start node
 *     came_from := the empty map	// The map of navigated nodes.
 * 
 *    g_score[start] := 0    		// Cost from start along best known path.
 *    								// Estimated total cost from start to goal through y.
 *    f_score[start] := g_score[start] + heuristic_cost_estimate(start, goal)
 *     
 *    while openset is not empty
 *        current := the node in openset having the lowest f_score[] value
 *        if current = goal
 *            return reconstruct_path(came_from, goal)
 *         
 *        remove current from openset
 *        add current to closedset
 *        for each neighbor in neighbor_nodes(current)
 *            if neighbor in closedset
 *                continue
 *            tentative_g_score := g_score[current] + dist_between(current,neighbor)
 * 
 *            if neighbor not in openset or tentative_g_score < g_score[neighbor] 
 *                came_from[neighbor] := current
 *                g_score[neighbor] := tentative_g_score
 *                f_score[neighbor] := g_score[neighbor] + heuristic_cost_estimate(neighbor, goal)
 *                if neighbor not in openset
 *                    add neighbor to openset
 * 
 *    return failure
 *
 * function reconstruct_path(came_from,current)
 *    total_path := [current]
 *    while current in came_from:
 *        current := came_from[current]
 *         total_path.append(current)
 *     return total_path
 * @author user104
 *
 */
public class AStarRouteFinder implements RouteFinder {

	@Override
	public List<Node> find(Region region, Location source, Location destination) {
		Queue<Node> available_nodes = this.map(region);
		Queue<Node> open_nodes = this.extract(available_nodes, source);
		
		while (!open_nodes.isEmpty()) {
			Node head = open_nodes.poll();
			
			/*
			 * If the node's location and the destination are equal, the destination has been reached
			 */
			if (head.getLocation().equals(destination)) {
				return this.extract(head);
			}
			
			/*
			 * Close the node so it does not get processed twice
			 */
			available_nodes.remove(head.close());
			
			/*
			 * Check for surrounding nodes
			 */
			for (Node neighbour : head.adjacent(available_nodes, region)) {
				/*
				 * Don't process closed nodes
				 */
				if (neighbour.closed()) {
					continue;
				}
				
				/*
				 * Calculate the tentative weight
				 */
				int weight = head.getWeight() + neighbour.getLocation().distance(destination) + neighbour.getLocation().distance(head.getLocation());
				
				/*
				 * If the neighbour is walkable or the neighbour's weight is larger than the tentative weight
				 * add the neighbour to the available nodes and set its link/weight
				 */
				if (!open_nodes.contains(neighbour) || weight < neighbour.getWeight()) {
					neighbour.setLink(head);
					neighbour.setWeight(weight);
				
					/*
					 * Add the neighbour to available nodes
					 */
					if (!open_nodes.contains(neighbour)) {
						open_nodes.offer(neighbour);
					}
				}
			}
		}
		return Collections.singletonList(new Node(source));
	}

}