package org.spartan.net.message.game;

import org.spartan.net.message.Message;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

import io.netty.buffer.ByteBuf;

/**
 * 
 * @author brock
 *
 */
public class HeadlessMessage implements Message {
	
	/**
	 * The connection state
	 */
	private final ConnectionState connectionState;
	
	/**
	 * The payload of the message
	 */
	private final ByteBuf payload;

	/**
	 * @param connectionState
	 * @param payload
	 */
	public HeadlessMessage(ConnectionState connectionState, ByteBuf payload) {
		this.connectionState = connectionState;
		this.payload = payload;
	}

	/**
	 * @return Always throws UnsupportedOperationException
	 */
	public int getOpcode() {
		throw new UnsupportedOperationException("login message does not have an opcode");
	}

	/**
	 * @return the connectionState
	 */
	public ConnectionState getConnectionState() {
		return connectionState;
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
		return "LoginMessage [connectionState=" + connectionState + ", payload=" + payload + "]";
	}

}
