package org.spartan.model.entity.player.ui;

import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.player.ui.event.ExperienceGained;
import org.spartan.model.entity.player.ui.event.LevelUp;

public class Stats {

	/**
	 * Constants for the skill numbers.
	 */
	public static final int ATTACK = 0, DEFENCE = 1, STRENGTH = 2, HITPOINTS = 3, RANGE = 4, PRAYER = 5, MAGIC = 6,
			COOKING = 7, WOODCUTTING = 8, FLETCHING = 9, FISHING = 10, FIREMAKING = 11, CRAFTING = 12, SMITHING = 13,
			MINING = 14, HERBLORE = 15, AGILITY = 16, THIEVING = 17, SLAYER = 18, FARMING = 19, RUNECRAFTING = 20,
			SKILL_COUNT = 21, MAXIMUM_XP = 200_000_000;

	/**
	 * The skill names.
	 */
	static final String[] SKILL_NAME = { "Attack", "Defence", "Strength", "Hitpoints", "Range", "Prayer",
			"Magic", "Cooking", "Woodcutting", "Fletching", "Fishing", "Firemaking", "Crafting", "Smithing", "Mining",
			"Herblore", "Agility", "Thieving", "Slayer", "Farming", "Runecrafting" };

	/**
	 * The amount of quest points
	 */
	private int questPoints;

	/**
	 * The combat level
	 */
	private int combatLevel;

	/**
	 * The stats
	 */
	private final Stat[] stats = new Stat[SKILL_COUNT];

	/**
	 * The player lol
	 */
	private final Player player;

	/**
	 * @param player
	 */
	public Stats(Player player) {
		this.player = player;
		for (int i = 0; i < stats.length; i++) {
			stats[i] = new Stat(i);
			stats[i].experience = i == HITPOINTS ? getXPForLevel(10) : 0;
			stats[i].level = i == HITPOINTS ? 10 : 1;
		}
	}
	
	/**
	 * 
	 * @param amount
	 * @param skill
	 */
	public void addExperience(int skill, int experience) {
		int base_experience = stats[skill].experience;
		int base_level = getLevelForExperience(stats[skill].experience);
		
		/*
		 * Add the experience and make sure the player doesn't exceed the maximum experience
		 */
		stats[skill].experience += experience;
		if (stats[skill].experience >= MAXIMUM_XP) {
			stats[skill].experience = MAXIMUM_XP;
		}
		
		/*
		 * Notify the player of any level ups that have happened
		 */
		if (base_level < getLevelForExperience(stats[skill].experience)) {
			player.notify(new LevelUp(skill, base_level, getLevelForExperience(stats[skill].experience + experience)));
		}
		
		/*
		 * If experience has been gained, send event
		 */
		if (base_experience - stats[skill].experience > 0) {
			player.notify(new ExperienceGained(skill, stats[skill].experience, experience));
		}
	}
	
	/**
	 * Gets a level by experience.
	 * @param skill The skill id.
	 * @return The level.
	 */
	public static int getLevelForExperience(int exp) {
		int points = 0;
		int output = 0;

		for (int lvl = 1; lvl <= 99; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			output = (int) (points / 4);
			if (output >= exp)
				return lvl;
		}
		return 99;
	}
	
	/**
	 * Gets a experience from the level.
	 * @param level The level.
	 * @return The experience.
	 */
	public static int getXPForLevel(int level) {
		int points = 0;
		int output = 0;
		for (int lvl = 1; lvl <= level; lvl++) {
			points += Math.floor(lvl + 300.0 * Math.pow(2.0, lvl / 7.0));
			if (lvl >= level) {
				return output;
			}
			output = (int) (points / 4);
		}
		return 0;
	}
	
	/**
	 * 
	 * @param skill
	 * @return
	 */
	public int getLevel(int skill) {
		return stats[skill].level;
	}
	
	/**
	 * 
	 * @param skill
	 * @return
	 */
	public int getAbsoluteLevel(int skill) {
		return getLevelForExperience(stats[skill].experience);
	}

	/**
	 * @return the questPoints
	 */
	public int getQuestPoints() {
		return questPoints;
	}

	/**
	 * @param questPoints the questPoints to set
	 */
	public void setQuestPoints(int questPoints) {
		this.questPoints = questPoints;
	}

	/**
	 * @param amount
	 */
	public void addQuestPoints(int amount) {
		this.questPoints += amount;
	}

	/**
	 * @return the combatLevel
	 */
	public int getCombatLevel() {
		if (combatLevel > 0) {
			return combatLevel;
		}
		return combatLevel;
	}

	/**
	 * 
	 * @param index
	 * @param experience
	 * @param level
	 */
	public void set(int index, int level, int experience) {
		this.stats[index].experience = experience;
		this.stats[index].level = level;
	}

	/**
	 * Represents a stat
	 * 
	 * @author brock
	 *
	 */
	public static class Stat {
		
		/**
		 * The index
		 */
		private final int index;
		
		/**
		 * 
		 */
		private int experience;
		
		/**
		 * 
		 */
		private int level;

		/**
		 * @param index
		 */
		public Stat(int index) {
			this.index = index;
		}

		/**
		 * @return the experience
		 */
		public int getExperience() {
			return experience;
		}

		/**
		 * @param experience the experience to set
		 */
		public void setExperience(int experience) {
			this.experience = experience;
		}

		/**
		 * @return the level
		 */
		public int getLevel() {
			return level;
		}

		/**
		 * @param level the level to set
		 */
		public void setLevel(int level) {
			this.level = level;
		}

		/**
		 * @return the index
		 */
		public int getIndex() {
			return index;
		}
		
	}

}
