package org.spartan.model.entity.map;

import java.util.Set;

import org.spartan.model.locale.Location;

public class RegionCollisionMap implements CollisionMap {

	/**
	 * The width of the collision map
	 */
	private final int width;
	
	/**
	 * The height of the collision map
	 */
	private final int height;

	/**
	 * The clipping data
	 */
	private final int[][] clippingData;
	
	/**
	 * The location
	 */
	private final Location location;
	
	/**
	 * Creates a collision map out of a region
	 * @param region
	 * @return
	 */
	public static RegionCollisionMap of(TiledMap tiles, Set<GameObject> objects, Location location) {
		RegionCollisionMap map = new RegionCollisionMap(tiles.getWidth(), tiles.getHeight(), location);
		
		/*
		 * Clip each of the tiles
		 */
		for (int x = 0; x < tiles.getWidth(); x++) {
			for (int y = 0; y < tiles.getHeight(); y++)  {
				map.mark(tiles.get(x, y));
			}
		}
		
		/*
		 * Clip each of the objects
		 */
		objects.stream().filter(object -> object.getLocation().getZ() == 0).forEach(map::mark);
		return map;
	}

	/**
	 * @param width
	 * @param height
	 * @param clippingData
	 */
	public RegionCollisionMap(int width, int height, int[][] clippingData, Location location) {
		this.width = width;
		this.height = height;
		this.clippingData = clippingData;
		this.location = location;
	}

	/**
	 * @param width
	 * @param height
	 * @param clippingData
	 */
	public RegionCollisionMap(int width, int height, Location location) {
		this (width, height, new int[width][height], location);
	}

	/**
	 * Marks an object on the map
	 * @param object
	 */
	public void mark(GameObject object) {
		GameObjectDefinition objectDefinition = GameObjectDefinition.get(object.getId());
		if (object.getType() == 22) {
			if (objectDefinition.isSolid() && objectDefinition.isInteractable())
				set(object.getLocation().getX(), object.getLocation().getY(), CollisionMap.IMPASSABLE_TERRAIN);
			return;
		}
		if (object.getType() == 10 || object.getType() == 11) {
			if (objectDefinition.isSolid() && objectDefinition.getName() != null && objectDefinition.getDescription() != null)
				markSolidOccupant(object.getLocation(), objectDefinition.getWidth(), objectDefinition.getHeight(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() >= 12) {
			if (objectDefinition.isSolid() && objectDefinition.isInteractable())
				markSolidOccupant(object.getLocation(), objectDefinition.getWidth(), objectDefinition.getHeight(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() == 0) {
			if (objectDefinition.isSolid())
				markWall(object.getLocation(), object.getType(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() == 1) {
			if (objectDefinition.isSolid())
				markWall(object.getLocation(), object.getType(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() == 2) {
			if (objectDefinition.isSolid())
				markWall(object.getLocation(), object.getType(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() == 3) {
			if (objectDefinition.isSolid())
				markWall(object.getLocation(), object.getType(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
		if (object.getType() == 9) {
			if (objectDefinition.isSolid())
				markSolidOccupant(object.getLocation(), objectDefinition.getWidth(), objectDefinition.getHeight(), object.getOrientation(), objectDefinition.isTraversable());
			return;
		}
	}

	/**
	 * Marks a tile on the map
	 * 
	 * @param tile
	 */
	public void mark(Tile[] tiles) {
		if ((tiles[0].getRenderingHints() & 1) == 1) {
			int markingPlane = tiles[0].getLocation().getZ();
			if ((tiles[1].getRenderingHints() & 2) == 2)
				markingPlane--;
			if (markingPlane >= 0)
				set(tiles[0].getLocation().getX(), tiles[0].getLocation().getY(), CollisionMap.IMPASSABLE_TERRAIN);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param object_type
	 * @param orientation
	 * @param impenetrable
	 */
	private void markWall(Location location, int object_type, int orientation, boolean impenetrable) {
		this.markWall(location.getX(), location.getY(), object_type, orientation, impenetrable);
	}
	
	/**
	 * Marks a wall on the collision map
	 * @param x
	 * @param y
	 * @param object_type
	 * @param orientation
	 * @param impenetrable
	 */
	private void markWall(int x, int y, int object_type, int orientation, boolean impenetrable) {
		/*
		 * 
		 */
		if (object_type == 0) {
			if (orientation == 0) {
				set(x, y, CollisionMap.WEST);
				set(x - 1, y, CollisionMap.EAST);
			}
			if (orientation == 1) {
				set(x, y, CollisionMap.NORTH);
				set(x, y + 1, CollisionMap.SOUTH);
			}
			if (orientation == 2) {
				set(x, y, CollisionMap.EAST);
				set(x + 1, y, CollisionMap.WEST);
			}
			if (orientation == 3) {
				set(x, y, CollisionMap.SOUTH);
				set(x, y - 1, CollisionMap.NORTH);
			}
		}
		
		/*
		 * These are the corners of walls. They make it so you can't go through
		 * corners diagonally
		 */
		if (object_type == 1 || object_type == 3) {
			if (orientation == 0) {
				set(x, y, CollisionMap.NORTH_WEST);
				set(x - 1, y + 1, CollisionMap.SOUTH_EAST);
			}
			if (orientation == 1) {
				set(x, y, CollisionMap.NORTH_EAST);
				set(x + 1, y + 1, CollisionMap.SOUTH_WEST);
			}
			if (orientation == 2) {
				set(x, y, CollisionMap.SOUTH_EAST);
				set(x + 1, y - 1, CollisionMap.NORTH_WEST);
			}
			if (orientation == 3) {
				set(x, y, CollisionMap.SOUTH_WEST);
				set(x - 1, y - 1, CollisionMap.NORTH_EAST);
			}
		}
		
		/*
		 * Straight corners, meaning 2 sides of the tile are walled off
		 */
		if (object_type == 2) {
			if (orientation == 0) {
				set(x, y, CollisionMap.WEST | CollisionMap.NORTH);
				set(x - 1, y, CollisionMap.EAST);
				set(x, y + 1, CollisionMap.SOUTH);
			}
			if (orientation == 1) {
				set(x, y, CollisionMap.EAST | CollisionMap.NORTH);
				set(x + 1, y, CollisionMap.WEST);
				set(x, y + 1, CollisionMap.SOUTH);
			}
			if (orientation == 2) {
				set(x, y, CollisionMap.EAST | CollisionMap.SOUTH);
				set(x + 1, y, CollisionMap.WEST);
				set(x, y - 1, CollisionMap.NORTH);
			}
			if (orientation == 3) {
				set(x, y, CollisionMap.WEST | CollisionMap.SOUTH);
				set(x, y - 1, CollisionMap.NORTH);
				set(x - 1, y, CollisionMap.EAST);
			}
		}
		
		/*
		 * Projectiles cannot move throught this wall
		 */
		if (impenetrable) {
			if (object_type == 0) {
				if (orientation == 0) {
					set(x, y, CollisionMap.WEST_SOLID);
					set(x - 1, y, CollisionMap.EAST_SOLID);
				}
				if (orientation == 1) {
					set(x, y, CollisionMap.NORTH_SOLID);
					set(x, y + 1, CollisionMap.SOUTH_SOLID);
				}
				if (orientation == 2) {
					set(x, y, CollisionMap.EAST_SOLID);
					set(x + 1, y, CollisionMap.WEST_SOLID);
				}
				if (orientation == 3) {
					set(x, y, CollisionMap.SOUTH_SOLID);
					set(x, y - 1, CollisionMap.NORTH_SOLID);
				}
			}
			if (object_type == 1 || object_type == 3) {
				if (orientation == 0) {
					set(x, y, CollisionMap.NORTH_WEST_SOLID);
					set(x - 1, y + 1, CollisionMap.SOUTH_EAST_SOLID);
				}
				if (orientation == 1) {
					set(x, y, CollisionMap.NORTH_EAST_SOLID);
					set(x + 1, y + 1, CollisionMap.SOUTH_WEST_SOLID);
				}
				if (orientation == 2) {
					set(x, y, CollisionMap.SOUTH_EAST_SOLID);
					set(x + 1, y - 1, CollisionMap.NORTH_WEST_SOLID);
				}
				if (orientation == 3) {
					set(x, y, CollisionMap.SOUTH_WEST_SOLID);
					set(x - 1, y - 1, CollisionMap.NORTH_EAST_SOLID);
				}
			}
			if (object_type == 2) {
				if (orientation == 0) {
					set(x, y, CollisionMap.WEST_SOLID | CollisionMap.NORTH_SOLID);
					set(x - 1, y, CollisionMap.EAST_SOLID);
					set(x, y + 1, CollisionMap.SOUTH_SOLID);
				}
				if (orientation == 1) {
					set(x, y, CollisionMap.EAST_SOLID | CollisionMap.NORTH_SOLID);
					set(x + 1, y, CollisionMap.WEST_SOLID);
					set(x, y + 1, CollisionMap.SOUTH_SOLID);
				}
				if (orientation == 2) {
					set(x, y, CollisionMap.EAST_SOLID | CollisionMap.SOUTH_SOLID);
					set(x + 1, y, CollisionMap.WEST_SOLID);
					set(x, y - 1, CollisionMap.NORTH_SOLID);
				}
				if (orientation == 3) {
					set(x, y, CollisionMap.WEST_SOLID | CollisionMap.SOUTH_SOLID);
					set(x, y - 1, CollisionMap.NORTH_SOLID);
					set(x - 1, y, CollisionMap.EAST_SOLID);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param object_width
	 * @param object_height
	 * @param orientation
	 * @param impenetrable
	 */
	private void markSolidOccupant(Location location, int object_width, int object_height, int orientation, boolean impenetrable) {
		this.markSolidOccupant(location.getX(), location.getY(), object_width, object_height, orientation, impenetrable);
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param object_width
	 * @param object_height
	 * @param orientation
	 * @param impenetrable
	 */
	private void markSolidOccupant(int x, int y, int object_width, int object_height, int orientation, boolean impenetrable) {
		int occupied = CollisionMap.IMPASSABLE_TERRAIN;
		if (impenetrable)
			occupied += CollisionMap.PROJECTILE_BLOCK;
		for (int _x = x; _x < x + object_width; _x++) {
			if (_x >= 0 && _x < width) {
				for (int _y = y; _y < y + object_height; _y++) {
					if (_y >= 0 && _y < height) {
						set(_x, _y, occupied);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param flag
	 */
	private void set(int x, int y, int flag) {
		if (x >= 0 && x < width && y >= 0 && y < height)
			clippingData[x][y] |= flag;
	}

	@Override
	public boolean collides(int x, int y, int flag) {
		Location absolute = new Location(x, y).subtract(location.inflate());
		return (clippingData[absolute.getX()][absolute.getY()] & flag) != 0;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

}
