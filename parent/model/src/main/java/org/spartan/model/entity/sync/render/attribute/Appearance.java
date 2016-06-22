package org.spartan.model.entity.sync.render.attribute;

import org.spartan.model.entity.sync.render.Attribute;

public class Appearance implements Attribute {

	/**
	 * The gender of the character
	 */
	private int gender;
	
	/**
	 * The character's torso id
	 */
	private int torso;
	
	/**
	 * The character's legs id
	 */
	private int legs;
	
	/**
	 * The character's hands id
	 */
	private int hands;
	
	/**
	 * The character's feet id
	 */
	private int feet;

	/**
	 * Creates a default appearance
	 */
	public Appearance() {
		this (0, 25, 39, 35, 44);
	}

	/**
	 * @param gender
	 * @param torso
	 * @param legs
	 * @param hands
	 * @param feet
	 */
	public Appearance(int gender, int torso, int legs, int hands, int feet) {
		this.gender = gender;
		this.torso = torso;
		this.legs = legs;
		this.hands = hands;
		this.feet = feet;
	}

	/**
	 * @return the gender
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * @return the torso
	 */
	public int getTorso() {
		return torso;
	}

	/**
	 * @param torso the torso to set
	 */
	public void setTorso(int torso) {
		this.torso = torso;
	}

	/**
	 * @return the legs
	 */
	public int getLegs() {
		return legs;
	}

	/**
	 * @param legs the legs to set
	 */
	public void setLegs(int legs) {
		this.legs = legs;
	}

	/**
	 * @return the hands
	 */
	public int getHands() {
		return hands;
	}

	/**
	 * @param hands the hands to set
	 */
	public void setHands(int hands) {
		this.hands = hands;
	}

	/**
	 * @return the feet
	 */
	public int getFeet() {
		return feet;
	}

	/**
	 * @param feet the feet to set
	 */
	public void setFeet(int feet) {
		this.feet = feet;
	}

}
