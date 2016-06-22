package org.spartan.net.message;

public interface MessageDecorator {

	/**
	 * Decorates a message into an event
	 * 
	 * @param msg
	 * @return
	 */
	Object decorate(Message msg, IncomingMessageDefinition definition);

}
