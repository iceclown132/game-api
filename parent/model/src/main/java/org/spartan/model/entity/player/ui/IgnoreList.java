package org.spartan.model.entity.player.ui;

import java.util.ArrayList;
import java.util.List;

public class IgnoreList {

	/**
	 * The list of players on the ignore list
	 */
	private final List<String> usernames = new ArrayList<>();

	/**
	 * 
	 * @param username
	 */
	public void add(String username) {
		usernames.add(username);
	}
	
	/**
	 * 
	 * @param username
	 */
	public void remove(String username) {
		usernames.remove(username);
	}
	
	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean contains(String username) {
		return usernames.contains(username);
	}

}
