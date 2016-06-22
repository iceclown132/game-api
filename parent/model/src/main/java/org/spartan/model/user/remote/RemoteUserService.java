package org.spartan.model.user.remote;

import org.spartan.cdi.Container;
import org.spartan.cdi.inject.annotation.Inject;
import org.spartan.cdi.scope.service.Service;
import org.spartan.cdi.util.resource.Resource;
import org.spartan.model.entity.item.Item;
import org.spartan.model.entity.player.Player;
import org.spartan.model.realm.Realm;
import org.spartan.model.realm.StatusCode;
import org.spartan.model.user.AuthenticationAttempt;
import org.spartan.model.user.User;
import org.spartan.model.user.UserService;
import org.spartan.model.user.attribute.Credentials;
import org.spartan.model.user.serialization.Loadout;
import org.spartan.model.util.UnirestQueryBuilder;

import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class RemoteUserService implements UserService {

	/**
	 * The remote address of the login server
	 */
	@Resource("spartan.realm.remote")
	private String remote;
	
	/**
	 * The api token to access the login server
	 */
	@Resource("spartan.realm.api-token")
	private String token;
	
	/**
	 * The container
	 */
	@Inject
	private Container container;
	
	/**
	 * The container
	 */
	@Inject
	private Realm realm;

	@Override
	public AuthenticationAttempt authenticate(Credentials credentials) throws Exception {
		try {
			return UnirestQueryBuilder.post(remote + "/authenticate", new Request(credentials, token), AuthenticationAttempt.class);
		} catch (UnirestException e) {
			return new AuthenticationAttempt(StatusCode.LOGIN_SERVER_OFFLINE);
		}
	}

	@Override
	public void disconnect(User user) throws Exception {
		UnirestQueryBuilder.post(remote + "/disconnect", new Request(user.getCredentials(), token));
	}

	@Override
	public void load(Player player) throws Exception {
		try {
			Loadout loadout = UnirestQueryBuilder.post(remote + "/loadout", new Request(player.getUser().getCredentials(), token), Loadout.class);
			
			/*
			 * The settings tab
			 */
			if (loadout.getSettings() != null) {
				player.getUserInterface().getSettings().setAcceptAid(loadout.getSettings().isAcceptAid());
				player.getUserInterface().getSettings().setBrightness(loadout.getSettings().getBrightness());
				player.getUserInterface().getSettings().setChatEffects(loadout.getSettings().isChatEffects());
				player.getUserInterface().getSettings().setOneButton(loadout.getSettings().isOneButton());
				player.getUserInterface().getSettings().setRunning(loadout.getSettings().isRunning());
				player.getUserInterface().getSettings().setVolume(loadout.getSettings().getVolume());
			}
			
			/*
			 * the player's skills
			 */
			if (loadout.getSkills() != null) {
				loadout.getSkills().forEach(skill -> player.getUserInterface().getStats().set(skill.getIndex(), skill.getLevel(), skill.getExperience()));
			}
			
			/*
			 * Teleport the player to the correct location
			 */
			if (loadout.getPosition() != null) {
				player.teleport().transform(loadout.getPosition().getX(), loadout.getPosition().getY(), loadout.getPosition().getZ());
			}
			
			/*
			 * Load the player's bank
			 */
			if (loadout.getBank() != null) {
				loadout.getBank().forEach(item -> player.getUserInterface().getBank().set(new Item(item.getItem(), item.getAmount()), item.getSlot()));
			}
			
			/*
			 * Load the player's inventory
			 */
			if (loadout.getInventory() != null) {
				loadout.getInventory().forEach(item -> player.getUserInterface().getInventory().set(new Item(item.getItem(), item.getAmount()), item.getSlot()));
			}
			
			/*
			 * Load the player's equipment
			 */
			if (loadout.getEquipment() != null) {
				loadout.getEquipment().forEach(item -> player.getUserInterface().getEquipment().set(new Item(item.getItem(), item.getAmount()), item.getSlot()));
			}
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Player user) throws Exception {
		
	}

	@SuppressWarnings("unused")
	public static class Request {
		
		/**
		 * The username, password and token properties
		 */
		private final String username, password, token;
		
		/**
		 * @param username
		 * @param password
		 * @param token
		 */
		public Request(Credentials credentials, String token) {
			this.username = credentials.getUsername();
			this.password = credentials.getPassword();
			this.token = token;
		}
		
	}

}
