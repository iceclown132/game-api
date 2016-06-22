package org.spartan.net.message;

import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

public class IncomingMessageDefinition extends AbstractDefinition {

	/**
	 * The message decorator
	 */
	private final MessageDecorator decorator;

	/**
	 * @param opcode
	 * @param size
	 * @param model
	 * @param connectionState
	 * @param decorator
	 */
	public IncomingMessageDefinition(int opcode, int size, Class<?> model, ConnectionState connectionState, MessageDecorator decorator) {
		super(opcode, size, model, connectionState);
		this.decorator = decorator;
	}

	/**
	 * @return the decorator
	 */
	public MessageDecorator getDecorator() {
		return decorator;
	}

}
