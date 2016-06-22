package org.spartan.net.message;

import java.util.Set;

import org.spartan.cdi.Container;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

public interface MessageRepository {
	
	/**
	 * 
	 * @param connectionState
	 * @return
	 */
	Set<IncomingMessageDefinition> incomingStatefulDefinition(ConnectionState connectionState);
	
	/**
	 * 
	 * @param connectionState
	 * @return
	 */
	Set<OutgoingMessageDefinition> outgoingStatefulDefinition(ConnectionState connectionState);

	/**
	 * 
	 * @param connectionState
	 * @return
	 */
	Set<IncomingMessageDefinition> incomingNumberedDefinition(int opcode);
	/**
	 * 
	 * @param connectionState
	 * @return
	 */
	Set<OutgoingMessageDefinition> outgoingTypedDefinition(Class<?> type);

	/**
	 * Initializes the repository
	 * 
	 * @param container
	 */
	void initialize(Container container) throws Exception;
	
}
