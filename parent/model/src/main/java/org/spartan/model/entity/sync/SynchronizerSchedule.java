package org.spartan.model.entity.sync;

import java.util.HashSet;
import java.util.Set;

import org.spartan.cdi.Container;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.inject.annotation.Startup;
import org.spartan.cdi.scope.service.Service;
import org.spartan.clock.cycle.cdi.Scheduled;
import org.spartan.model.realm.Realm;

@Service
public class SynchronizerSchedule {

	/**
	 * The realm that needs to be synchronized
	 */
	@Inject
	private Realm realm;

	/**
	 * The collection of synchronizers that are running
	 */
	private final Set<Synchronizer> synchronizers = new HashSet<>();

	/**
	 * Detects and instantiates all of the synchronizer implementations
	 * 
	 * @param container
	 * @throws Exception
	 */
	@Startup
	public void initialize(Container container) throws Exception {
		container.instance(Synchronizer.class).forEach(synchronizers::add);
	}

	/**
	 * Calls all of the synchronizers periodically (every game tick)
	 */
	@Scheduled
	public void synchronize() {
		synchronizers.forEach(synchronizer -> synchronizer.synchronize(realm));
	}

}
