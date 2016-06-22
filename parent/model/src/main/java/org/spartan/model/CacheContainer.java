package org.spartan.model;

import java.io.File;

import org.barracuda.cache.Cache;
import org.spartan.cdi.scope.service.Service;
import org.spartan.model.entity.map.LandscapeContainer;
import org.spartan.model.entity.map.Region;
import org.spartan.model.locale.Location;

@Service
public class CacheContainer {

	/**
	 * The cache
	 */
	private final Cache cache;

	/**
	 * LandscapeContainer
	 */
	private final LandscapeContainer container;

	/**
	 * 
	 */
	public CacheContainer() throws Exception {
		this.cache = new Cache(new File("/home/brock/.workspace_spartan/parent/route/src/test/resources/cache_289"));
		this.container = new LandscapeContainer(cache);
	}

	/**
	 * 
	 * @param location
	 * @return
	 * @throws Exception 
	 */
	public Region region(Location location) throws Exception {
		return container.get(location.deflate());
	}

}
