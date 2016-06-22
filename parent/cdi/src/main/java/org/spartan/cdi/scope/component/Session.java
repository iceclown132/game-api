package org.spartan.cdi.scope.component;

import java.util.UUID;

import org.spartan.cdi.scope.Context;

@Component
public class Session implements Context {

	/**
	 * The session's uid
	 */
	private final String uid;
	
	/**
	 * Indicates the session is inactive
	 */
	private boolean inactive;

	/**
	 * @param uid
	 */
	public Session() {
		this.uid = UUID.randomUUID().toString();
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @return the inactive
	 */
	public boolean inactive() {
		return inactive;
	}

	/**
	 * @param inactive the inactive to set
	 */
	public void setInactive(boolean inactive) {
		this.inactive = inactive;
	}
	
}
