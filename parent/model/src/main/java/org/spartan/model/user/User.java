package org.spartan.model.user;

import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.scope.component.Component;
import org.spartan.cdi.scope.component.Session;
import org.spartan.model.user.attribute.Credentials;
import org.spartan.model.user.attribute.UserPrivilege;

/**
 * A user is <i>NOT</i> a player
 * 
 * This contains all of the information gathered during the login and
 * authentication stage such as credentials and if the player is monitored or
 * not.
 * 
 * @author brock
 */
@Component
public class User {

	/**
	 * The session's id
	 */
	private final String sessionId;

	/**
	 * The user's credentials
	 */
	private final Credentials credentials;
	
	/**
	 * The user's session
	 */
	@Inject
	private Session session;
	
	/**
	 * The user's privilege
	 */
	private UserPrivilege privelege = UserPrivilege.NORMAL;
	
	/**
	 * The user id
	 */
	private int userId;
	
	/**
	 * Indicates the player's action are monitored more closely (the account
	 * has been flagged for botting)
	 */
	private boolean monitored;
	
	/**
	 * Indicates the user's account has been disabled.
	 */
	private boolean disabled;

	/**
	 * @param sessionId
	 * @param credentials
	 */
	public User(String sessionId, Credentials credentials) {
		this.sessionId = sessionId;
		this.credentials = credentials;
	}

	/**
	 * @return the monitored
	 */
	public boolean isMonitored() {
		return monitored;
	}

	/**
	 * @param monitored the monitored to set
	 */
	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}

	/**
	 * @return the privelege
	 */
	public UserPrivilege getPrivelege() {
		return privelege;
	}

	/**
	 * @param privelege the privelege to set
	 */
	public void setPrivelege(UserPrivilege privelege) {
		this.privelege = privelege;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return the credentials
	 */
	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @return the disabled
	 */
	public boolean isDisabled() {
		return disabled;
	}

	/**
	 * @param disabled the disabled to set
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

}
