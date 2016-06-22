package org.spartan.model.entity.map;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.barracuda.cache.Archive;
import org.barracuda.cache.Cache;
import org.spartan.net.netty.util.ByteBufferUtil;

public class GameObjectDefinition {

	/**
	 * The static logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(GameObjectDefinition.class);

	/**
	 * The collection of definitions
	 */
	private static GameObjectDefinition[] definitions;
	
	/**
	 * The id of the object
	 */
	private final int id;
	
	/**
	 * The width of the object in tiles
	 */
	private int width = 1;
	
	/**
	 * The length of the object in tiles
	 */
	private int height = 1;

	/**
	 * The object's name
	 */
	private String name;
	
	/**
	 * The object's description
	 */
	private String description;
	
	/**
	 * The object's options
	 */
	private String[] options;
	
	/**
	 * Indicates you can interact with the given object (through clicking)
	 */
	private boolean interactable;
	
	/**
	 * Indicates the 
	 */
	private boolean solid = true;
	
	/**
	 * 
	 */
	private boolean traversable = true;
	
	/**
	 * wall ???
	 */
	private boolean wall;
	
	/**
	 * The ids of the models used to create the object
	 */
	private int[] modelIds;
	
	/**
	 * The type of models
	 */
	private int[] modelTypes;
	
	/**
	 * Something to do with agility obstacles that makes it so when the player
	 * is doing something, the clipping flags disappear. I don't really know
	 * how or why
	 */
	private boolean interactableObstacle;

	/**
	 * @param id
	 */
	public GameObjectDefinition(int id) {
		this.id = id;
	}

	/**
	 * Initializes all of the definitions
	 * @param initialize
	 * @throws Exception 
	 */
	public static void initialize(Cache cache) throws IOException {
		Archive archive = new Archive(cache.getFile(0, 2));
		ByteBuffer data_file = archive.getFileAsByteBuffer("loc.dat");
		ByteBuffer index_file = archive.getFileAsByteBuffer("loc.idx");
		GameObjectDefinition.definitions = new GameObjectDefinition[index_file.getShort() & 0xffff];
		int buffer_offset = 2;
		for (int index = 0; index < GameObjectDefinition.definitions.length; index++) {
			GameObjectDefinition.definitions[index] = from_buffer((ByteBuffer) data_file.position(buffer_offset), index);
			buffer_offset += index_file.getShort();
		}
		logger.info("{} object definitions loaded", GameObjectDefinition.definitions.length);
	}

	/**
	 * Gets the definition for the given id
	 * @param id
	 * @return
	 */
	public static GameObjectDefinition get(int id) {
		return definitions[id];
	}
	
	/**
	 * Reads the next objectdefinition from the given buffer
	 * 
	 * @param buffer
	 * @return
	 */
	private static GameObjectDefinition from_buffer(ByteBuffer buffer, int id) {
		GameObjectDefinition definition = new GameObjectDefinition(id);
		int _actions = -1;
		for (int opcode = buffer.get() & 0xff; opcode != 0 && opcode != 77; opcode = buffer.get() & 0xff) {
			if (opcode == 1) {
				buffer.position(buffer.position() + buffer.get() * 3 + 1);
			}
			else if (opcode == 2) {
				definition.setName(ByteBufferUtil.readString(buffer));
			}
			else if (opcode == 3) {
				definition.setDescription(ByteBufferUtil.readString(buffer));
			}
			else if (opcode == 5) {
				int modelCount = buffer.get() & 0xff;
				if (modelCount > 0)
					if (definition.modelIds == null) {
						definition.modelTypes = null;
						definition.modelIds = new int[modelCount];
						for (int m = 0; m < modelCount; m++)
							definition.modelIds[m] = buffer.getShort();

					} else {
						buffer.position(buffer.position() + modelCount * 2);
					}
			}
			else if (opcode == 14) {
				definition.setWidth(buffer.get());
			}
			else if (opcode == 15) {
				definition.setHeight(buffer.get());
			}
			else if (opcode == 17) {
				definition.setSolid(false);
			}
			else if (opcode == 18) {
				definition.setTraversable(false);
			}
			else if (opcode == 19) {
				_actions = buffer.get() & 0xff;
				if (_actions == 1) {
					definition.interactable = true;
				}
			}
			else if (opcode == 23) {
				definition.setWall(true);
			}
			else if (opcode == 28 || opcode == 29 || opcode == 39 || opcode == 69 || opcode == 75) {
				buffer.position(buffer.position() + 1);
			}
			else if (opcode >= 30 && opcode < 39) {
				if (definition.options == null) {
					definition.options = new String[5];
				}
				definition.options[opcode - 30] = ByteBufferUtil.readString(buffer);
			}
			else if(opcode == 40){
				buffer.position(buffer.position() + buffer.get() * 4 + 1);
			}
			else if (opcode == 24 || opcode == 60 || (opcode >= 65 && opcode <= 68) || (opcode >= 70 && opcode <= 72)) {
				buffer.position(buffer.position() + 2);
			}
			else if (opcode == 73) {
				definition.setInteractableObstacle(true);
			}
			else if (opcode == 74) {
				definition.setSolid(false);
				definition.setTraversable(false);
			}
			else {
				if (opcode != 75)
					continue;
				buffer.get();
			}
		}
		if (_actions == -1) {
			definition.interactable = definition.modelIds != null && (definition.modelTypes == null || definition.modelTypes[0] == 10);
			if (definition.options != null)
				definition.interactable = true;
		}
		
		return definition;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
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

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the options
	 */
	public String[] getOptions() {
		return options;
	}

	/**
	 * @param options the options to set
	 */
	public void setOptions(String[] options) {
		this.options = options;
	}

	/**
	 * @return the interactable
	 */
	public boolean isInteractable() {
		return interactable;
	}

	/**
	 * @param interactable the interactable to set
	 */
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}

	/**
	 * @return the solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * @param solid the solid to set
	 */
	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	/**
	 * @return the traversable
	 */
	public boolean isTraversable() {
		return traversable;
	}

	/**
	 * @param traversable the traversable to set
	 */
	public void setTraversable(boolean traversable) {
		this.traversable = traversable;
	}

	/**
	 * @return the wall
	 */
	public boolean isWall() {
		return wall;
	}

	/**
	 * @param wall the wall to set
	 */
	public void setWall(boolean wall) {
		this.wall = wall;
	}

	/**
	 * @return the modelIds
	 */
	public int[] getModelIds() {
		return modelIds;
	}

	/**
	 * @param modelIds the modelIds to set
	 */
	public void setModelIds(int[] modelIds) {
		this.modelIds = modelIds;
	}

	/**
	 * @return the modelTypes
	 */
	public int[] getModelTypes() {
		return modelTypes;
	}

	/**
	 * @param modelTypes the modelTypes to set
	 */
	public void setModelTypes(int[] modelTypes) {
		this.modelTypes = modelTypes;
	}

	/**
	 * @return the interactableObstacle
	 */
	public boolean isInteractableObstacle() {
		return interactableObstacle;
	}

	/**
	 * @param interactableObstacle the interactableObstacle to set
	 */
	public void setInteractableObstacle(boolean interactableObstacle) {
		this.interactableObstacle = interactableObstacle;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameObjectDefinition [id=" + id + ", width=" + width + ", height=" + height + ", name=" + name
				+ ", description=" + description + ", options=" + Arrays.toString(options) + ", interactable="
				+ interactable + ", solid=" + solid + ", traversable=" + traversable + ", wall=" + wall
				+ ", interactableObstacle=" + interactableObstacle + "]";
	}

}
