package org.spartan.net.message;

import io.netty.buffer.ByteBuf;

public interface Message {

	/**
	 * Gets the payload
	 * 
	 * @return
	 */
	ByteBuf getPayload();

}