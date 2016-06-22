package org.spartan.route.test;

import java.io.File;
import java.util.List;

import org.barracuda.cache.Cache;
import org.junit.Test;
import org.spartan.model.entity.map.GameObjectDefinition;
import org.spartan.model.entity.map.LandscapeContainer;
import org.spartan.model.entity.map.Region;
import org.spartan.model.entity.map.TileDefinition;
import org.spartan.model.locale.Location;
import org.spartan.route.AStarRouteFinder;
import org.spartan.route.Node;
import org.spartan.route.RouteFinder;

/**
 * Karamja Dungeon : X: 2859 Y: 9572;
 * Barbarian Village : 3082 3420
 * Kalphite lair : 3226, 3207
 * Crafting Guild : 2933, 3285
 * KBD lair : 2717, 9816
 * Varrock : 3214, 3424
 * 
 * @author brock
 */
public class PathingTest {

	/**
	 * Randomly selected coordinates
	 */
	private static final Location[] TEST_LOCATIONS = {
			new Location(2859, 9572), // Karamja
			new Location(3082, 3420), // barb
			new Location(3038, 9800), // kalphite
			new Location(2933, 3285), // craft guild
			new Location(2717, 9816), // kbd lair
			new Location(3222, 3222), // lummy
			new Location(3214, 3424), // Varrock
	};

	/**
	 * 
	 * @throws Exception
	 */
	@Test public void test_path() throws Exception {
		try {
			/*
			 * Load the cache and initialize the object definitions and the texture definitions
			 */
			Cache cache = new Cache(new File("/home/brock/.workspace_spartan/parent/route/src/test/resources/cache_317"));
			GameObjectDefinition.initialize(cache);
			TileDefinition.initialize(cache);
			
			RouteFinder finder = new AStarRouteFinder();
			
			/*
			 * Draw each of the test coordinates
			 */
			for (Location test_location : TEST_LOCATIONS) {
				LandscapeContainer container = new LandscapeContainer(cache);
				Region region = container.get(test_location);
				
				long delta = System.currentTimeMillis();
				List<Node> path = finder.find(region, test_location.deflate().inflate().translate(13, 63, 0), test_location.deflate().inflate().translate(52, 28, 0));
				
				if (path == null) {
					System.out.println("not found in " + (System.currentTimeMillis() - delta));
					continue;
				}
				System.out.println("found in " + (System.currentTimeMillis() - delta));
				
				/*
				 * 
				 */
				Renderer renderer = new Renderer();
				renderer.render(path, region);
//				renderer.render(region.getTiles());
				renderer.render(region.getObjects());
				renderer.render(region.getCollisionMap(), region);
				renderer.save("/home/brock/Documents/route project/" + test_location.getX() + "_" + test_location.getY() + ".png");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
