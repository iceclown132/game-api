package org.spartan.net.message;

public interface MessageSerializer {

	/**
	 * @param msg
	 * @return
	 */
	Message serialize(Object msg, OutgoingMessageDefinition definition);

}
