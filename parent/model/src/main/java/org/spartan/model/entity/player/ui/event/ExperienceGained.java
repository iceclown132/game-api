package org.spartan.model.entity.player.ui.event;

public class ExperienceGained {

	/**
	 * The skill the person has gained experience in
	 */
	private final int skill;
	
	/**
	 * The total amount of experience the player has in the given skill
	 */
	private final int totalExperience;

	/**
	 * The experience gained
	 */
	private final int experienceGained;

	/**
	 * @param skill
	 * @param totalExperience
	 * @param experienceGained
	 */
	public ExperienceGained(int skill, int totalExperience, int experienceGained) {
		this.skill = skill;
		this.totalExperience = totalExperience;
		this.experienceGained = experienceGained;
	}

	/**
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @return the totalExperience
	 */
	public int getTotalExperience() {
		return totalExperience;
	}

	/**
	 * @return the experienceGained
	 */
	public int getExperienceGained() {
		return experienceGained;
	}

}
