package org.spartan.model.entity.player;

import org.spartan.cdi.Container;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.scope.component.Component;
import org.spartan.cdi.scope.component.Session;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.model.CacheContainer;
import org.spartan.model.entity.AbstractDynamicEntity;
import org.spartan.model.entity.Model;
import org.spartan.model.entity.player.ui.UserInterface;
import org.spartan.model.entity.sync.render.Attribute;
import org.spartan.model.entity.sync.render.attribute.Appearance;
import org.spartan.model.entity.sync.waypoint.PlayerWaypointVector;
import org.spartan.model.entity.sync.waypoint.WaypointVector;
import org.spartan.model.realm.Realm;
import org.spartan.model.realm.ViewScope;
import org.spartan.model.user.User;
import org.spartan.net.OutputStream;

@Component
public class Player extends AbstractDynamicEntity {
	
	/**
	 * The player model
	 */
	private final Model model = new PlayerModel();
	
	/**
	 * The view scope containing all the visible entities
	 */
	private final ViewScope viewScope = new ViewScope();

	/**
	 * The walking queue
	 */
	private final WaypointVector waypointVector = new PlayerWaypointVector(this);
	
	/**
	 * The cache container
	 */
	private CacheContainer cache;
	
	/**
	 * The user connected to this player
	 */
	@Inject
	private User user;
	
	/**
	 * The session
	 */
	@Inject
	private Session session;
	
	/**
	 * The realm the player is in
	 */
	@Inject
	private Realm realm;
	
	/**
	 * The user's outputstream.
	 */
	@Inject
	private OutputStream outputStream;
	
	/**
	 * The container lol
	 */
	@Inject
	private Container container;
	
	/**
	 * The player's user interface
	 */
	private final UserInterface userInterface = new UserInterface(this);

	
	/**
	 * The player's appearance
	 */
	private final Appearance appearance = new Appearance();

	/**
	 * @return the appearance
	 */
	public Attribute appearance() {
		return appearance;
	}
	
	/**
	 * 
	 * @param object
	 */
	public void notify(Object object) {
		container.event(object.getClass()).fire(ReflectionUtil.cast(object), session);
	}

	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @return the viewScope
	 */
	public ViewScope getViewScope() {
		return viewScope;
	}

	/**
	 * @return the realm
	 */
	public Realm getRealm() {
		return realm;
	}
	
	/**
	 * @return the session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * @return the user interface
	 */
	public UserInterface getUserInterface() {
		return userInterface;
	}

	@Override
	public Model model() {
		return model;
	}

	@Override
	public WaypointVector waypoints() {
		return waypointVector;
	}

	/**
	 * @return the cache
	 */
	public CacheContainer getCache() {
		return cache;
	}

}
