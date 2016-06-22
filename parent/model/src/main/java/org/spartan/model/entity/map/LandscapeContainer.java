package org.spartan.model.entity.map;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.barracuda.cache.Cache;
import org.barracuda.cache.index.impl.MapIndex;
import org.barracuda.cache.util.BufferUtil;
import org.barracuda.cache.util.ZipUtils;
import org.spartan.model.locale.Location;

/**
 * Helper class to load landscape files.
 * 
 * @author koga
 *
 */
public class LandscapeContainer {
	
	/**
	 * The link to the jagex file store
	 */
	private final Cache cache;
	
	/**
	 * The collection of indices
	 */
	private final Set<MapIndex> indices;
	
	/**
	 * The region cache
	 */
	@SuppressWarnings("unused")
	private final Map<RegionIdentifier, Region> region_cache = new HashMap<>();
	
	/**
	 * Constructor. Initializes the landscape cache
	 */
	public LandscapeContainer(Cache cache) {
		this.cache = cache;
		this.indices = Arrays.stream(cache.getIndexTable().getMapIndices()).collect(Collectors.toSet());
	}
	
	/**
	 * 
	 * @param location
	 * @return
	 * @throws Exception
	 */
	public Region get(Location location) throws Exception {
		return this.get(RegionIdentifier.of(location));
	}
	
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	public Region get(RegionIdentifier identifier) throws Exception {
		return this.get(index(identifier));
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	private Region get(MapIndex index) throws Exception {
		TiledMap tilemap = LandscapeContainer.read_tiles(ZipUtils.unzip(cache.getFile(4, index.getMapFile())), index.getIdentifier());
		Set<GameObject> objects = LandscapeContainer.read_objects(ZipUtils.unzip(cache.getFile(4, index.getLandscapeFile())));
		CollisionMap collision = RegionCollisionMap.of(tilemap, objects, Location.of(index.getIdentifier()));
		return new Region(objects, tilemap, collision, Location.of(index.getIdentifier()));
	}
	
	/**
	 * 
	 * @param identifier
	 * @return
	 */
	private MapIndex index(RegionIdentifier identifier) {
		return indices.stream().filter(index -> index.getIdentifier() == identifier.hashCode()).findAny().orElseThrow(NullPointerException::new);
	}
	
	/**
	 * Parses the objects in the landscape file
	 * 
	 * @param buffer
	 * @return
	 */
	private static Set<GameObject> read_objects(ByteBuffer buffer) {
		Set<GameObject> objects = new HashSet<>();
		
		for (int index_offset = -1; buffer.get(buffer.position()) != 0; buffer.get()) {
			index_offset += BufferUtil.readUnsignedMedium(buffer);
			
			for (int marker = 0; buffer.get(buffer.position()) != 0; ) {
				GameObject object = new GameObject();
				
				/*
				 * The object id gets increased with the difference between the last one and the next one
				 */
				object.setId(index_offset);
				
				/*
				 * Each increase in the marker will actually define the offset in location of the next object
				 */
				marker += BufferUtil.readUnsignedMedium(buffer) - 1;
				object.getLocation().setY(marker & 0x3f);
				object.getLocation().setX(marker >> 6 & 0x3f);
				object.getLocation().setZ(marker >> 12);
				
				/*
				 * The meta data
				 */
				int metadata = buffer.get() & 0xff;
				object.setType(metadata >> 2);
				object.setOrientation(metadata & 3);
				
				/*
				 * Set object to correct location
				 */
				objects.add(object);
			}
		}
		return objects;
	}
	
	/**
	 * 
	 * @param buffer
	 * @return
	 */
	private static TiledMap read_tiles(ByteBuffer buffer, int identifier) {
		Tile[][][] tiles = new Tile[Region.WIDTH][Region.HEIGHT][Region.DEPTH];
		for (int z = 0; z < Region.DEPTH; z++)
			for (int x = 0; x < Region.WIDTH; x++)
				for (int y = 0; y < Region.HEIGHT; y++)
					tiles[x][y][z] = read_tile(buffer, tiles, new Tile(x, y, z));
		return new Landscape(identifier, Region.WIDTH, Region.HEIGHT, tiles);
	}
	
	/**
	 * Parses the tiles in the landscape file
	 * 
	 * @param buffer
	 * @param tiles
	 * @return
	 */
	private static Tile read_tile(ByteBuffer buffer, Tile[][][] tiles, Tile tile) {
		for (int value = buffer.get() & 0xff; buffer.hasRemaining(); value = buffer.get() & 0xff) {
			/*
			 * Calculates the height of the tile according to its other layer's values
			 */
			if (value == 0) {
				if (tile.getLocation().getZ() == 0) {
//					tile.setHeight(-method165(0xe3b7b + i2 + j, 0x87cce + j2 + i) * 8);
				} else {
					tile.setHeight(tiles[tile.getLocation().getX()][tile.getLocation().getY()][tile.getLocation().getZ() - 1].getHeight() - 240);
				}
				break;
			}
			
			/*
			 * Sets the height of the tile directly
			 */
			if (value == 1) {
				int vertex_height = buffer.get() & 0xFF;
				if (vertex_height == 1) {
					vertex_height = 0;
				}
				if (tile.getLocation().getZ() == 0) {
					tile.setHeight(-vertex_height * 8);
				} else {
					tile.setHeight(tiles[tile.getLocation().getX()][tile.getLocation().getY()][tile.getLocation().getZ() - 1].getHeight() - vertex_height * 8);
				}
				break;
			}
			
			/*
			 * Sets the texture of the tile and in turn derives the clipping flags and orientation
			 */
			if (value <= 49) {
				tile.setTexture(buffer.get());
				tile.setCollisionHints((byte) ((value - 2) / 4));
				tile.setOrientation((byte) (value - 2 & 3));
			}
			
			/*
			 * Gets the rendering hints
			 */
			else if (value <= 81) {
				tile.setRenderingHints((byte) (value - 49));
			}
			
			/*
			 * Sets he underlay texture id (this is the id of the texture that is also present apart from the main one)
			 */
			else {
				tile.setUnderlayTextureId((byte) (value - 81));
			}
		}
		return tile;
	}

}
