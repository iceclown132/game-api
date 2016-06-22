package org.spartan.model.entity.sync.waypoint;

import java.util.Deque;
import java.util.LinkedList;

import org.spartan.model.entity.DynamicEntity;

public abstract class AbstractWaypointVector<E extends DynamicEntity> implements WaypointVector {

	/**
	 * The maximum size of the queue. If there are more points than this size,
	 * they are discarded.
	 */
	public static final int MAXIMUM_SIZE = 50;

	/**
	 * dunno
	 */
	static final byte[] DIRECTION_DELTA_X = new byte[] { -1, 0, 1, -1,
			1, -1, 0, 1 };

	/**
	 * duno
	 */
	static final byte[] DIRECTION_DELTA_Y = new byte[] { 1, 1, 1, 0, 0,
			-1, -1, -1 };

	/**
	 * The entity-
	 */
	protected final E entity;

	/**
	 * The queue of waypoints.
	 */
	private final Deque<Waypoint> waypoints = new LinkedList<>();

	/**
	 * Run toggle (button in client).
	 */
	private boolean runToggled = false;

	/**
	 * Run for this queue (CTRL-CLICK) toggle.
	 */
	private boolean runQueue = false;

	/**
	 * @param entity
	 */
	public AbstractWaypointVector(E entity) {
		this.entity = entity;
	}

	@Override
	public WaypointVector add(int x, int y) {
		/*
		 * The RuneScape client will not send all the points in the queue. It
		 * just sends places where the direction changes.
		 * 
		 * For instance, walking from a route like this:
		 * 
		 * <code> ***** * * ***** </code>
		 * 
		 * Only the places marked with X will be sent:
		 * 
		 * <code> X***X * * X***X </code>
		 * 
		 * This code will 'fill in' these points and then add them to the queue.
		 */

		/*
		 * We need to know the previous point to fill in the path.
		 */
		if (waypoints.size() == 0) {
			/*
			 * There is no last point, reset the queue to add the player's
			 * current location.
			 */
			clear();
		}

		/*
		 * We retrieve the previous point here.
		 */
		Waypoint last = waypoints.peekLast();

		/*
		 * We now work out the difference between the points.
		 */
		int diffX = x - last.getX();
		int diffY = y - last.getY();

		/*
		 * And calculate the number of steps there is between the points.
		 */
		int max = Math.max(Math.abs(diffX), Math.abs(diffY));
		for (int i = 0; i < max; i++) {
			/*
			 * Keep lowering the differences until they reach 0 - when our route
			 * will be complete.
			 */
			if (diffX < 0) {
				diffX++;
			} else if (diffX > 0) {
				diffX--;
			}
			if (diffY < 0) {
				diffY++;
			} else if (diffY > 0) {
				diffY--;
			}

			/*
			 * Add this next step to the queue.
			 */
			addStepInternal(x - diffX, y - diffY);
		}
		return this;
	}
	
	/**
	 * Adds a single step to the queue internally without counting gaps. This
	 * method is unsafe if used incorrectly so it is private to protect the
	 * queue.
	 * 
	 * @param x
	 *            The x coordinate of the step.
	 * @param y
	 *            The y coordinate of the step.
	 */
	private void addStepInternal(int x, int y) {
		/*
		 * Check if we are going to violate capacity restrictions.
		 */
		if (waypoints.size() >= MAXIMUM_SIZE) {
			/*
			 * If we are we'll just skip the point. The player won't get a
			 * complete route by large routes are not probable and are more
			 * likely sent by bots to crash servers.
			 */
			return;
		}

		/*
		 * We retrieve the previous point (this is to calculate the direction to
		 * move in).
		 */
		Waypoint last = waypoints.peekLast();

		/*
		 * Now we work out the difference between these steps.
		 */
		int diffX = x - last.getX();
		int diffY = y - last.getY();

		/*
		 * And calculate the direction between them.
		 */
		int dir = DirectionUtil.direction(diffX, diffY);

		/*
		 * Check if we actually move anywhere.
		 */
		if (dir > -1) {
			/*
			 * We now have the information to add a point to the queue! We
			 * create the actual point object and add it.
			 */
			waypoints.add(new Waypoint(x, y, Direction.values()[dir]));

		}
	}
	
	/**
	 * Gets the next point of movement.
	 * 
	 * @return The next point.
	 */
	protected Waypoint getNextPoint() {
		/*
		 * Take the next point from the queue.
		 */
		Waypoint p = waypoints.poll();

		/*
		 * Checks if there are no more points.
		 */
		if (p == null || p.getDirection() == null) {
			/*
			 * Return <code>null</code> indicating no movement happened.
			 */
			return null;
		} else {
			/*
			 * Set the player's new location.
			 */
			int diffX = DIRECTION_DELTA_X[p.getDirection().ordinal()];
			int diffY = DIRECTION_DELTA_Y[p.getDirection().ordinal()];
			
			/*
			 * Transform the player's location
			 */
			entity.location().translate(diffX, diffY, 0);

			/*
			 * And return the direction.
			 */
			return p;
		}
	}

	@Override
	public WaypointVector clear() {
		runQueue = false;
		waypoints.clear();
		waypoints.add(new Waypoint(entity.location().getX(), entity.location().getY(), null));
		return this;
	}
	
	@Override
	public WaypointVector finish() {
		waypoints.removeFirst();
		return this;
	}

	/**
	 * Checks if the queue is empty.
	 * 
	 * @return <code>true</code> if so, <code>false</code> if not.
	 */
	public boolean isEmpty() {
		return waypoints.isEmpty();
	}

	/**
	 * Sets the run toggled flag.
	 * 
	 * @param runToggled
	 *            The run toggled flag.
	 */
	public void setRunningToggled(boolean runToggled) {
		this.runToggled = runToggled;
	}

	/**
	 * Sets the run queue flag.
	 * 
	 * @param runQueue
	 *            The run queue flag.
	 */
	public void setRunningQueue(boolean runQueue) {
		this.runQueue = runQueue;
	}

	/**
	 * Gets the run toggled flag.
	 * 
	 * @return The run toggled flag.
	 */
	public boolean isRunningToggled() {
		return runToggled;
	}

	/**
	 * Gets the running queue flag.
	 * 
	 * @return The running queue flag.
	 */
	public boolean isRunningQueue() {
		return runQueue;
	}

	/**
	 * Checks if any running flag is set.
	 * 
	 * @return <code>true</code. if so, <code>false</code> if not.
	 */
	public boolean isRunning() {
		return runToggled || runQueue;
	}

}
