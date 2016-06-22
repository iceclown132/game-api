package org.spartan.net.message;

import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

public class AbstractDefinition {
	
	/**
	 * Opcode of the message
	 */
	private final int opcode;
	
	/**
	 * Size of the message
	 */
	private final int size;
	
	/**
	 * The class representing the model
	 */
	private final Class<?> model;
	
	/**
	 * The connection state
	 */
	private final ConnectionState connectionState;

	/**
	 * @param opcode
	 * @param size
	 * @param model
	 * @param connectionState
	 */
	public AbstractDefinition(int opcode, int size, Class<?> model, ConnectionState connectionState) {
		this.opcode = opcode;
		this.size = size;
		this.model = model;
		this.connectionState = connectionState;
	}

	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the connectionState
	 */
	public ConnectionState getConnectionState() {
		return connectionState;
	}

	/**
	 * @return the model
	 */
	public Class<?> getModel() {
		return model;
	}

}
