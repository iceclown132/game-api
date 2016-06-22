package org.spartan.model.user;

import org.spartan.model.realm.StatusCode;

public class AuthenticationAttempt {

	/**
	 * The status of the authentication
	 */
	private StatusCode responseCode;
	
	/**
	 * The user being fetched
	 */
	private String sessionId;
	
	/**
	 * 
	 */
	public AuthenticationAttempt() {
	}

	/**
	 * @param responseCode
	 */
	public AuthenticationAttempt(StatusCode responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseCode
	 */
	public StatusCode getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(StatusCode responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
