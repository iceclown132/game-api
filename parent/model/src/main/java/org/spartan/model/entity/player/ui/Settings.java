package org.spartan.model.entity.player.ui;

public class Settings {

	/**
	 * Indicates the player is running
	 */
	private boolean running;

	/**
	 * 
	 */
	private boolean acceptAid;

	/**
	 * 
	 */
	private boolean oneButton;

	/**
	 * 
	 */
	private int brightness;
	
	/**
	 * 
	 */
	private int volume;
	
	/**
	 * 
	 */
	private boolean chatEffects;

	/**
	 * @return the running
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running the running to set
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @return the acceptAid
	 */
	public boolean isAcceptAid() {
		return acceptAid;
	}

	/**
	 * @param acceptAid the acceptAid to set
	 */
	public void setAcceptAid(boolean acceptAid) {
		this.acceptAid = acceptAid;
	}

	/**
	 * @return the oneButton
	 */
	public boolean isOneButton() {
		return oneButton;
	}

	/**
	 * @param oneButton the oneButton to set
	 */
	public void setOneButton(boolean oneButton) {
		this.oneButton = oneButton;
	}

	/**
	 * @return the brightness
	 */
	public int getBrightness() {
		return brightness;
	}

	/**
	 * @param brightness the brightness to set
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	/**
	 * @return the volume
	 */
	public int getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * @return the chatEffects
	 */
	public boolean isChatEffects() {
		return chatEffects;
	}

	/**
	 * @param chatEffects the chatEffects to set
	 */
	public void setChatEffects(boolean chatEffects) {
		this.chatEffects = chatEffects;
	}

}
