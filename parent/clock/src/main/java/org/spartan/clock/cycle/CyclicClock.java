package org.spartan.clock.cycle;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

import org.reflections.Reflections;
import org.spartan.cdi.Container;
import org.spartan.cdi.inject.annotation.Startup;
import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.ReflectionsInitializer;
import org.spartan.cdi.util.resource.Resource;
import org.spartan.clock.Clock;
import org.spartan.clock.Future;
import org.spartan.clock.cycle.cdi.ReflectionMethodTask;
import org.spartan.clock.cycle.cdi.Scheduled;
import org.spartan.clock.timer.Timer;

@Service
public class CyclicClock implements Clock, Runnable {

	/**
	 * The executor service
	 */
	private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

	/**
	 * The collection of active tasks
	 */
	private final Queue<CyclicFuture> active = new LinkedList<>();

	/**
	 * The collection of queued tasks
	 */
	private final Queue<CyclicFuture> queued = new LinkedList<>();

	/**
	 * The atomic long holding the current time
	 */
	private final AtomicLong time = new AtomicLong();

	/**
	 * The rate at which the server cycles (lol)
	 */
	@Resource("spartan.clock.delay")
	private long cycleRate;

	@Startup
	public void startup(Container container) {
		service.scheduleAtFixedRate(this, cycleRate, cycleRate, TimeUnit.MILLISECONDS);
		
		Reflections reflections = ReflectionsInitializer.initialize();
		reflections.getMethodsAnnotatedWith(Scheduled.class).forEach(method -> {
			repeat(new ReflectionMethodTask(method, container.manager().get(method.getDeclaringClass()).get(null, container.manager())));
		});
	}

	@Override
	public long currentTime() {
		return time.get();
	}

	@Override
	public void run() {
		time.getAndIncrement();
		
		/*
		 * Add all of the queued tasks to the active tasks
		 */
		queued.forEach(active::add);
		queued.clear();
		
		/*
		 * 
		 */
		for (Iterator<CyclicFuture> iterator = active.iterator(); iterator.hasNext(); ) {
			CyclicFuture future = iterator.next();
			
			if (future.canceled()) {
				iterator.remove();
			}
			else if (future.getTimer().finished()) {
				try {
					future.getConsumer().accept(future);
				} catch (Exception ex) {
					if (future.getExceptionListener() != null) {
						future.getExceptionListener().accept(ex);
					}
					future.cancel();
				} finally {
					if (future.getCompletionListener() != null)
						future.getCompletionListener().run();
				}

				if (future.repeats() && !future.canceled()) {
					future.getTimer().rewind();
				}
				else {
					iterator.remove();
				}
			}
		}
	}

	@Override
	public Future schedule(Consumer<Future> consumer, int delay) {
		CyclicFuture future = new CyclicFuture(new Timer(delay, this), consumer, false);
		future.getTimer().start();
		queued.add(future);
		return future;
	}

	@Override
	public Future repeat(Consumer<Future> consumer, int delay) {
		CyclicFuture future = new CyclicFuture(new Timer(delay, this), consumer, true);
		future.getTimer().start();
		queued.add(future);
		return future;
	}

	@Override
	public void shutdown() {

	}

}
