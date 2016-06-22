package org.spartan.model.user.serialization;

import java.util.List;

public class Loadout {
	
	/**
	 * The position of the player
	 */
	private Position position;

	/**
	 * The player's appearance
	 */
	private Appearance appearance;
	
	/**
	 * The player's stats
	 */
	private List<Skill> skills;
	
	/**
	 * The user's inventory
	 */
	private List<Item> inventory;
	
	/**
	 * The user's inventory
	 */
	private List<Item> bank;
	
	/**
	 * The user's inventory
	 */
	private List<Item> equipment;
	
	/**
	 * The settings
	 */
	private Settings settings;

	/**
	 * @return the appearance
	 */
	public Appearance getAppearance() {
		return appearance;
	}

	/**
	 * @param appearance the appearance to set
	 */
	public void setAppearance(Appearance appearance) {
		this.appearance = appearance;
	}

	/**
	 * @return the position
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Position position) {
		this.position = position;
	}

	/**
	 * @return the inventory
	 */
	public List<Item> getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}

	/**
	 * @return the bank
	 */
	public List<Item> getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(List<Item> bank) {
		this.bank = bank;
	}

	/**
	 * @return the equipment
	 */
	public List<Item> getEquipment() {
		return equipment;
	}

	/**
	 * @param equipment the equipment to set
	 */
	public void setEquipment(List<Item> equipment) {
		this.equipment = equipment;
	}

	/**
	 * @return the skills
	 */
	public List<Skill> getSkills() {
		return skills;
	}

	/**
	 * @param skills the skills to set
	 */
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @param settings the settings to set
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

}
