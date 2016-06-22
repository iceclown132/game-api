package org.spartan.model.entity.player.ui.event;

public class LevelUp {

	/**
	 * The skill
	 */
	private final int skill;
	
	/**
	 * The level before
	 */
	private final int levelBefore;
	
	/**
	 * The level after
	 */
	private final int levelAfter;

	/**
	 * @param skill
	 * @param levelBefore
	 * @param levelAfter
	 */
	public LevelUp(int skill, int levelBefore, int levelAfter) {
		this.skill = skill;
		this.levelBefore = levelBefore;
		this.levelAfter = levelAfter;
	}

	/**
	 * @return the skill
	 */
	public int getSkill() {
		return skill;
	}

	/**
	 * @return the levelBefore
	 */
	public int getLevelBefore() {
		return levelBefore;
	}

	/**
	 * @return the levelAfter
	 */
	public int getLevelAfter() {
		return levelAfter;
	}

}
