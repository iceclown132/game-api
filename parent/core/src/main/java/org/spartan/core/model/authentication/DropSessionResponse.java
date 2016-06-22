package org.spartan.core.model.authentication;

import org.spartan.model.realm.StatusCode;

public class DropSessionResponse {

	/**
	 * The response code
	 */
	private final StatusCode code;

	/**
	 * @param code
	 */
	public DropSessionResponse(StatusCode code) {
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public StatusCode getCode() {
		return code;
	}
	
}
