package org.spartan.model.user;

import org.spartan.model.entity.player.Player;
import org.spartan.model.user.attribute.Credentials;

/**
 * The service that communicates with the database/loginserver or whatever
 * that currently controls the user loading/saving etc.
 * @author brock
 *
 */
public interface UserService {

	/**
	 * @param user
	 */
	AuthenticationAttempt authenticate(Credentials credentials) throws Exception;
	
	/**
	 * @param user
	 */
	void disconnect(User user) throws Exception;
	
	/**
	 * @param user
	 */
	void load(Player player) throws Exception;
	
	/**
	 * @param user
	 */
	void save(Player player) throws Exception;

}
