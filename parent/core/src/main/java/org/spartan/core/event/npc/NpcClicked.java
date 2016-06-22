package org.spartan.core.event.npc;

public class NpcClicked {
	
	/**
	 * Option to attack the other player
	 */
	public static final int OPTION_ATTACK = 0;
	
	/**
	 * Option to trade the other player
	 */
	public static final int OPTION_TRADE = 1;

	/**
	 * the id of the player
	 */
	private int npcId;
	
	/**
	 * The option
	 */
	private int option;

	/**
	 * @return the npcId
	 */
	public int getNpcId() {
		return npcId;
	}

	/**
	 * @param npcId the npcId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
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
