package org.spartan.clock.timer;

import org.spartan.clock.Clock;

public class Timer {
	
	/**
	 * The delay
	 */
	private final long delay;
	
	/**
	 * The clock
	 */
	private final Clock clock;

	/**
	 * Indicates the timer has stopped
	 */
	private boolean stopped;
	
	/**
	 * Indicates the timer has been interrupted
	 */
	private boolean interrupted;

	/**
	 * Start time of the timer
	 */
	private long start;
	
	/**
	 * Time at which the thing interrupted
	 */
	private long interruptedTime;
	
	/**
	 * @param delay
	 * @param clock
	 */
	public Timer(long delay, Clock clock) {
		this.delay = delay;
		this.clock = clock;
	}
	
	/**
	 * Indicates the timer has finished
	 */
	public boolean finished() {
		return !stopped && !interrupted && (clock.currentTime() - start) >= delay;
	}

	/**
	 * Interrupts the timer
	 */
	public void interrupt() {
		if (interrupted) {
			throw new IllegalStateException("timer is already interrupted");
		}
		this.interrupted = true;
		this.interruptedTime = clock.currentTime();
	}

	/**
	 * Indicates the timer has been interrupted
	 * @return
	 */
	public boolean interrupted() {
		return interrupted;
	}
	
	/**
	 * Resumes the timer
	 */
	public void resume() {
		if (!interrupted) {
			throw new IllegalStateException("trying to resume a running timer");
		}
		if (stopped) {
			throw new IllegalStateException("cannot resume a canceled timer");
		}
		this.interrupted = false;
		this.start += clock.currentTime() - interruptedTime;
	}
	
	/**
	 * Stops the timer
	 */
	public void stop() {
		if (!stopped) {
			throw new IllegalStateException("trying to resume a running timer");
		}
		this.stopped = true;
	}
	
	/**
	 * Starts the timer
	 */
	public void start() {
		this.stopped = false;
		this.start = clock.currentTime();
	}

	/**
	 * Rewinds this timer
	 */
	public void rewind() {
		start();
	}

}
