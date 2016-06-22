package org.spartan.net.message;

import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

public class OutgoingMessageDefinition extends AbstractDefinition {

	/**
	 * Serializes the message
	 */
	private final MessageSerializer serializer;

	/**
	 * @param opcode
	 * @param size
	 * @param model
	 * @param connectionState
	 * @param serializer
	 */
	public OutgoingMessageDefinition(int opcode, int size, Class<?> model, ConnectionState connectionState, MessageSerializer serializer) {
		super(opcode, size, model, connectionState);
		this.serializer = serializer;
	}

	/**
	 * @return the serializer
	 */
	public MessageSerializer getSerializer() {
		return serializer;
	}

}
