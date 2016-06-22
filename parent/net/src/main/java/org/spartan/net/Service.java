package org.spartan.net;

import java.net.SocketAddress;

/**
 * Represents a networking service
 * 
 * @author brock
 *
 */
public interface Service {

	/**
	 * 
	 * @param container
	 * @throws Exception
	 */
	void startup() throws Exception;

	/**
	 * 
	 * @param address
	 * @throws Exception
	 */
	void bind(SocketAddress address) throws Exception;

}
