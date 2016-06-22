package org.spartan.model.user.attribute;

public class Credentials {

	/**
	 * The user's username
	 */
	private final String username;
	
	/**
	 * The user's password
	 */
	private final String password;

	/**
	 * @param username
	 * @param password
	 */
	public Credentials(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Credentials=" + username + ":" + password;
	}

}
