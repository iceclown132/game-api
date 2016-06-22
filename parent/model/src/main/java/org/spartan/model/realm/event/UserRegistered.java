package org.spartan.model.realm.event;

import org.spartan.model.realm.Realm;
import org.spartan.model.user.User;

public class UserRegistered {

	/**
	 * The user that is being registered
	 */
	private final User user;
	
	/**
	 * The realm
	 */
	private final Realm realm;

	/**
	 * @param user
	 * @param realm
	 */
	public UserRegistered(User user, Realm realm) {
		this.user = user;
		this.realm = realm;
	}

	/**
	 * @return the realm
	 */
	public Realm getRealm() {
		return realm;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

}
