package org.spartan.core.event.player;

public class PlayerMagic {

	/**
	 * Id of the spell
	 */
	private int spellId;
	
	/**
	 * Id of the player
	 */
	private int playerId;
	
	/**
	 * Id of the interface
	 */
	private int interfaceId;

	/**
	 * @return the spellId
	 */
	public int getSpellId() {
		return spellId;
	}

	/**
	 * @param spellId the spellId to set
	 */
	public void setSpellId(int spellId) {
		this.spellId = spellId;
	}

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
	 * @return the interfaceId
	 */
	public int getInterfaceId() {
		return interfaceId;
	}

	/**
	 * @param interfaceId the interfaceId to set
	 */
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}

}
