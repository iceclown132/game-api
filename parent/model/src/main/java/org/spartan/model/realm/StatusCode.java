package org.spartan.model.realm;

public enum StatusCode {
	
	/**
	 * Indicates the handshake went ok
	 */
	HANDSHAKE_OK,
	
	/**
	 * Retry after 3 seconds
	 */
	RETRY,
	
	/**
	 * Authentication is ok
	 */
	AUTHENTICATION_OK,
	
	/**
	 * Credentials not correct (username/password)
	 */
	INVALID_CREDENTIALS,
	
	/**
	 * Account is disabled
	 */
	ACCOUNT_DISABLED,
	
	/**
	 * Account is still logged in
	 */
	ACCOUNT_LOGGED_IN,
	
	/**
	 * Server has been updated
	 */
	SERVER_UPDATED,
	
	/**
	 * World is full
	 */
	WORLD_FULL,
	
	/**
	 * Login server is offline
	 */
	LOGIN_SERVER_OFFLINE,
	
	/**
	 * Too many connections from the same ip
	 */
	TOO_MANY_CONNECTIONS,
	
	/**
	 * Bad session id (will likely not be used as this is web browser session)
	 */
	BAD_SESSION_ID,
	
	/**
	 * Session rejected (used as code for unspecified error)
	 */
	SESSION_REJECTED,
	
	/**
	 * Needs premium subscription for this realm
	 */
	PREMIUM_REQUIRED,
	
	/**
	 * Could not complete login
	 */
	INCOMPLETE_LOGIN,
	
	/**
	 * Indicates the player cannot log in as a server update is currently running
	 */
	SERVER_UPDATE_RUNNING,
	
	/**
	 * Does not log the player in but resets some variables in the client
	 */
	RESET,
	
	/**
	 * Too many attempts, wait 5 minutes before trying again
	 */
	TOO_MANY_ATTEMPTS,
	
	/**
	 * Player is standing in a location unaccessible to premium members
	 */
	BAD_LOCATION,
	
	/**
	 * Does nothing
	 */
	_18,
	
	/**
	 * Does nothing
	 */
	_19,
	
	/**
	 * Login server is invalid
	 */
	INVALID_LOGIN_SERVER,
	
	/**
	 * Retries connection after x amount of seconds
	 */
	TRANSFERRING_CHARACTER;
}