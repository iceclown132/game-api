package org.spartan.model.entity.map;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.barracuda.cache.Archive;
import org.barracuda.cache.Cache;
import org.spartan.net.netty.util.ByteBufferUtil;

public class TileDefinition {

	/**
	 * The collection of tile definitions
	 */
	private static final Map<Integer, TileDefinition> definitions = new HashMap<>();
	
	/**
	 * The rgb color of the tile
	 */
	private int rgb;
	
	/**
	 * The texture of the tile
	 */
	private int textureId;
	
	/**
	 * Indicates occlusion is enabled or not for the tile
	 */
	private boolean occlude;
	
	/**
	 * The name of the definition
	 */
	public String name;

	/**
	 * Initializes all of the definitions
	 * @param initialize
	 * @throws Exception 
	 */
	public static void initialize(Cache cache) throws IOException {
		ByteBuffer buffer = new Archive(cache.getFile(0, 2)).getFileAsByteBuffer("flo.dat");
		int definition_count = buffer.getShort();
		for (int definition_index = 0; definition_index < definition_count; definition_index++) {
			definitions.put(definition_index, TileDefinition.read(buffer));
		}
	}
	
	/**
	 * Reads a single definition from the buffer
	 * 
	 * @param buffer
	 * @return
	 */
	private static TileDefinition read(ByteBuffer buffer) {
		TileDefinition definition = new TileDefinition();
		for (int attribute = buffer.get() & 0xff; attribute != 0; attribute = buffer.get() & 0xff) {
			switch (attribute) {
			case 1:
				definition.rgb = (buffer.get() << 16) | (buffer.get() << 8) | buffer.get(); //buffer.getUnsignedTriByte();
				break;
			case 2:
				definition.textureId = buffer.get() & 0xff;
				break;
			case 3:
				break;
			case 5:
				definition.occlude = false;
				break;
			case 6:
				definition.name = ByteBufferUtil.readString(buffer);
				break;
			case 7:
				definition.rgb = (buffer.get() << 16) | (buffer.get() << 8) | buffer.get();
				break;
			default:
				throw new NullPointerException("invalid attribute " + attribute);
			}
		}
		return definition;
	}
	
	/**
	 * Gets a single definition for its id
	 * 
	 * @param index
	 * @return
	 */
	public static TileDefinition get(int index) {
		return definitions.get(index);
	}
	
	/**
	 * Gets a single definition for its id
	 * 
	 * @param index
	 * @return
	 */
	public static TileDefinition get(String name) {
		return definitions.values().stream().filter(definition -> name.equals(definition.getName())).findAny().orElseThrow(NullPointerException::new);
	}
	
	/**
	 * Constructor
	 */
	private TileDefinition() {
		textureId = -1;
		occlude = true;
	}

	/**
	 * @return the rgb
	 */
	public int getRgb() {
		return rgb;
	}

	/**
	 * @param rgb the rgb to set
	 */
	public void setRgb(int rgb) {
		this.rgb = rgb;
	}

	/**
	 * @return the textureId
	 */
	public int getTextureId() {
		return textureId;
	}

	/**
	 * @param textureId the textureId to set
	 */
	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}

	/**
	 * @return the occlude
	 */
	public boolean isOcclude() {
		return occlude;
	}

	/**
	 * @param occlude the occlude to set
	 */
	public void setOcclude(boolean occlude) {
		this.occlude = occlude;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
