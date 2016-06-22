package org.spartan.clock.cycle;

import java.util.function.Consumer;

import org.spartan.clock.Future;
import org.spartan.clock.timer.Timer;

public class CyclicFuture implements Future {

	/**
	 * The timer
	 */
	private final Timer timer;

	/**
	 * The consumer
	 */
	private final Consumer<Future> consumer;

	/**
	 * The consumer
	 */
	private Runnable completionListener;

	/**
	 * The consumer
	 */
	private Consumer<Exception> exceptionListener;
	
	/**
	 * Indicates the task is canceled
	 */
	private boolean canceled;
	
	/**
	 * 
	 */
	private final boolean repeat;

	/**
	 * @param timer
	 * @param consumer
	 * @param canceled
	 */
	public CyclicFuture(Timer timer, Consumer<Future> consumer, boolean repeat) {
		this.timer = timer;
		this.consumer = consumer;
		this.repeat = repeat;
	}

	@Override
	public Future onCompletion(Runnable consumer) {
		this.completionListener = consumer;
		return this;
	}

	@Override
	public Future onError(Consumer<Exception> consumer) {
		this.exceptionListener = consumer;
		return this;
	}

	@Override
	public void cancel() {
		this.canceled = true;
	}

	@Override
	public void interrupt() {
		this.timer.interrupt();
	}

	@Override
	public boolean canceled() {
		return canceled;
	}

	@Override
	public boolean interrupted() {
		return timer.interrupted();
	}

	boolean repeats() {
		return repeat;
	}

	Timer getTimer() {
		return timer;
	}

	Consumer<Future> getConsumer() {
		return consumer;
	}

	Runnable getCompletionListener() {
		return completionListener;
	}

	Consumer<Exception> getExceptionListener() {
		return exceptionListener;
	}

}
