package org.spartan.core.controller.authentication;

import java.util.Random;

import org.spartan.cdi.Container;
import org.spartan.cdi.event.Observes;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.scope.component.Session;
import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.resource.Resource;
import org.spartan.core.event.authentication.Authentication;
import org.spartan.core.event.authentication.Handshake;
import org.spartan.core.event.authentication.Handshake.RequestType;
import org.spartan.core.model.authentication.AuthenticationResponse;
import org.spartan.core.model.authentication.DropSessionResponse;
import org.spartan.core.model.authentication.HandshakeResponse;
import org.spartan.model.entity.player.Player;
import org.spartan.model.realm.Realm;
import org.spartan.model.realm.StatusCode;
import org.spartan.model.realm.event.UserRegistered;
import org.spartan.model.user.AuthenticationAttempt;
import org.spartan.model.user.User;
import org.spartan.model.user.UserService;
import org.spartan.net.OutputStream;
import org.spartan.net.event.Disconnect;
import org.spartan.net.netty.game.handler.GameChannel;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

@Service
public class AuthenticationController {

	/**
	 * The random number generator
	 */
	private static final Random random = new Random();

	/**
	 * Indicates RSA is enabled or not
	 */
	@Resource("security.isaac.enabled")
	private boolean isaac_enabled;

	/**
	 * The realm
	 */
	@Inject
	private Realm realm;
	
	/**
	 * The container
	 */
	@Inject
	private Container container;
	
	/**
	 * The user service
	 */
	@Inject
	private UserService service;

	/**
	 * Called when a handshake is received
	 * 
	 * @param handshake
	 * @param out
	 * @param channel
	 */
	public void on_handshake(@Observes Handshake handshake, OutputStream out, GameChannel channel) {
		if (RequestType.AUTHENTICATION.equals(handshake.getRequestType())) {
			/*
			 * Write the handshake response
			 */
			out.writeAndFlush(new HandshakeResponse(random.nextLong(), StatusCode.HANDSHAKE_OK));

			/*
			 * Set the connection to accept authentication requests from this
			 * user
			 */
			channel.setConnectionState(ConnectionState.AUTHENTICATION);
		}

		/*
		 * If request type is not that of authentication, close the connection
		 */
		else {
			out.writeAndFlush(new DropSessionResponse(StatusCode.BAD_SESSION_ID));
			out.close();
		}
	}

	/**
	 * Called when the authentication object is received
	 * 
	 * @param authentication
	 * @param out
	 * @param channel
	 */
	public void on_authentication(@Observes Authentication authentication, OutputStream out, GameChannel channel, Session session) throws Exception {
		AuthenticationAttempt attempt = service.authenticate(authentication.getCredentials());

		/*
		 * If the realm has no more remaining slots, don't allow the player to connect
		 */
		if (!realm.available()) {
			disconnect(channel, out, StatusCode.WORLD_FULL);
		}
		
		/*
		 * Request to login
		 */
		if (StatusCode.AUTHENTICATION_OK.equals(attempt.getResponseCode())) {
			User user = new User(attempt.getSessionId(), authentication.getCredentials());
			out.writeAndFlush(new AuthenticationResponse(StatusCode.AUTHENTICATION_OK, user));
			if (isaac_enabled && authentication.getIsaacPair() != null) {
				channel.setIsaac(authentication.getIsaacPair());
			}
			channel.setConnectionState(ConnectionState.GAME);
			container.manager().associate(User.class, user, session);
			container.event(UserRegistered.class).fire(new UserRegistered(user, realm), session);
		}
		
		/*
		 * Write the error code to the client and disconnect the user
		 */
		else if (attempt.getResponseCode() != null) {
			disconnect(channel, out, attempt.getResponseCode());
		}
		
		/*
		 * If no specified error, reject the attempt
		 */
		else {
			disconnect(channel, out, StatusCode.SESSION_REJECTED);
		}
	}
	
	public void on_register(@Observes UserRegistered event, Player player) throws Exception {
		this.service.load(player);
		this.realm.register(player);
	}
	
	/**
	 * Called when a user disconnects
	 * 
	 * @param disconnect
	 * @param channel
	 * @throws Exception 
	 */
	public void on_disconnect(@Observes Disconnect disconnect, GameChannel channel, Session session) throws Exception {
		if (container.manager().contains(User.class, session) && container.manager().contains(Player.class, session)) {
			this.realm.unregister(container.manager().getInjectedReference(Player.class, session));
		}
	}

	/**
	 * Drops the user's session with the given response code
	 * 
	 * @param channel
	 * @param out
	 * @param code
	 */
	private final void disconnect(GameChannel channel, OutputStream out, StatusCode code) {
		channel.setConnectionState(ConnectionState.DISCONNECTED);
		out.writeAndFlush(new DropSessionResponse(code));
		out.close();
	}

}
