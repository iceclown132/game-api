package org.spartan.net.message.game;

import org.spartan.net.message.Message;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author brock
 *
 */
public class GameMessage implements Message {

	/**
	 * Size of the message
	 */
	private final int size;
	
	/**
	 * Opcode of the message
	 */
	private final int opcode;
	
	/**
	 * The payload of the message
	 */
	private final ByteBuf payload;

	/**
	 * @param size
	 * @param opcode
	 * @param payload
	 * @param connectionState
	 */
	public GameMessage(int opcode, int size, ByteBuf payload) {
		this.size = size;
		this.opcode = opcode;
		this.payload = payload;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the opcode
	 */
	public int getOpcode() {
		return opcode;
	}

	/**
	 * @return the payload
	 */
	public ByteBuf getPayload() {
		return payload;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GameMessage [size=" + size + ", opcode=" + opcode + ", payload=" + payload + "]";
	}

}
