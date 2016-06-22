package org.spartan.model.entity.sync;

import org.spartan.model.realm.Realm;

/**
 * Synchronizes the realm with all of the players
 * 
 * @author brock
 *
 */
public interface Synchronizer {

	/**
	 * 
	 * 
	 * @param realm
	 */
	void synchronize(Realm realm);

}
