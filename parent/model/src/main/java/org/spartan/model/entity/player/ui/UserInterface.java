package org.spartan.model.entity.player.ui;

import org.spartan.model.entity.player.Player;

public class UserInterface {

	/**
	 * The equipment
	 */
	private final Equipment equipment;

	/**
	 * The player's friend list
	 */
	private final FriendList friendList;

	/**
	 * The player's ignore list
	 */
	private final IgnoreList ignoreList;

	/**
	 * The player's inventory;
	 */
	private final Inventory inventory;

	/**
	 * The music player
	 */
	private final MusicPlayer musicPlayer;

	/**
	 * The player's prayerbook
	 */
	private final PrayerBook prayerBook;

	/**
	 * The player's settings
	 */
	private final Settings settings;

	/**
	 * the player's spellbook
	 */
	private final SpellBook spellbook;

	/**
	 * The player's stats
	 */
	private final Stats stats;

	/**
	 * The player's weapon details
	 */
	private final WeaponDetails weaponDetails;

	/**
	 * The player's bank
	 */
	private final Bank bank;

	/**
	 * 
	 * @param player
	 */
	public UserInterface(Player player) {
		this.equipment = new Equipment(player);
		this.friendList = new FriendList();
		this.ignoreList = new IgnoreList();
		this.inventory = new Inventory(player);
		this.musicPlayer = new MusicPlayer();
		this.prayerBook = new PrayerBook();
		this.settings = new Settings();
		this.spellbook = new SpellBook();
		this.stats = new Stats(player);
		this.weaponDetails = new WeaponDetails();
		this.bank = new Bank(player, inventory);
	}

	/**
	 * @return the equipment
	 */
	public Equipment getEquipment() {
		return equipment;
	}

	/**
	 * @return the friendList
	 */
	public FriendList getFriendList() {
		return friendList;
	}

	/**
	 * @return the ignoreList
	 */
	public IgnoreList getIgnoreList() {
		return ignoreList;
	}

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return the musicPlayer
	 */
	public MusicPlayer getMusicPlayer() {
		return musicPlayer;
	}

	/**
	 * @return the prayerBook
	 */
	public PrayerBook getPrayerBook() {
		return prayerBook;
	}

	/**
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}

	/**
	 * @return the spellbook
	 */
	public SpellBook getSpellbook() {
		return spellbook;
	}

	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}

	/**
	 * @return the weaponDetails
	 */
	public WeaponDetails getWeaponDetails() {
		return weaponDetails;
	}

	/**
	 * @return the bank
	 */
	public Bank getBank() {
		return bank;
	}

}
