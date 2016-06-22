package org.spartan.core.event.player;

public class PlayerClicked {

	/**
	 * Option to follow the other player
	 */
	public static final int OPTION_FOLLOW = 0;
	
	/**
	 * Option to attack the other player
	 */
	public static final int OPTION_ATTACK = 1;
	
	/**
	 * Option to trade the other player
	 */
	public static final int OPTION_TRADE = 2;
	
	/**
	 * Option to duel the other player
	 */
	public static final int OPTION_DUEL = 3;

	/**
	 * the id of the player
	 */
	private int playerId;
	
	/**
	 * The option
	 */
	private int option;

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the option
	 */
	public int getOption() {
		return option;
	}

	/**
	 * @param option the option to set
	 */
	public void setOption(int option) {
		this.option = option;
	}

}
