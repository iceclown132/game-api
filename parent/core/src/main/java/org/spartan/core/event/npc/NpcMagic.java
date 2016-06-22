package org.spartan.core.event.npc;

public class NpcMagic {

	/**
	 * Id of the spell
	 */
	private int spellId;
	
	/**
	 * Id of the player
	 */
	private int npcId;
	
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
	public int getNpcId() {
		return npcId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
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
