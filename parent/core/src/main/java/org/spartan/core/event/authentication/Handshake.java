package org.spartan.core.event.authentication;

/**
 * The handshake
 * 
 * @author brock
 */
public class Handshake {
	
	/**
	 * Hash of the user's username, used to make sure the data in the login
	 * sequence is correct.
	 */
	private final int usernameHash;
	
	/**
	 * Opcode of the requested packet
	 */
	private final RequestType requestType;

	/**
	 * Constructor
	 * 
	 * @param data
	 * @param requestType
	 */
	public Handshake(int data, RequestType requestType) {
		this.usernameHash = data;
		this.requestType = requestType;
	}

	/**
	 * @return the data
	 */
	public int getData() {
		return usernameHash;
	}

	/**
	 * @return the requestType
	 */
	public RequestType getRequestType() {
		return requestType;
	}

	@Override
	public String toString() {
		return "Handshake [data=" + usernameHash + ", requestType=" + requestType + "]";
	}

	/**
	 * 
	 * @author brock
	 *
	 */
	public static enum RequestType {
		
		/**
		 * Indicates the player wants to authenticate onto the server
		 */
		AUTHENTICATION,
		
		/**
		 * Indicates the player wants to update his client through the JAGGRAB protocol
		 */
		CLIENT_UPDATE,
		
		/**
		 * Invalid request type
		 */
		INVALID;
	}
	
}