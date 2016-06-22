package org.spartan.core.model.authentication;

import org.spartan.model.realm.StatusCode;

public class HandshakeResponse {

	/**
	 * The server ISAAC key
	 */
	private final long serverKey;

	/**
	 * The response code
	 */
	private final StatusCode code;
	
	/**
	 * @param serverKey
	 * @param code
	 */
	public HandshakeResponse(long serverKey, StatusCode code) {
		this.serverKey = serverKey;
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public StatusCode getCode() {
		return code;
	}

	/**
	 * @return the serverKey
	 */
	public long getServerKey() {
		return serverKey;
	}

}
