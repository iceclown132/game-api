package org.spartan.core.model.authentication;

import org.spartan.model.realm.StatusCode;
import org.spartan.model.user.User;

public class AuthenticationResponse {

	/**
	 * The response code
	 */
	private final StatusCode code;
	
	/**
	 * The user
	 */
	private final User user;

	/**
	 * @param code
	 * @param user
	 */
	public AuthenticationResponse(StatusCode code, User user) {
		this.code = code;
		this.user = user;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the code
	 */
	public StatusCode getCode() {
		return code;
	}

}
