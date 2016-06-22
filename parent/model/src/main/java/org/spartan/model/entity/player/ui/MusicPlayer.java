package org.spartan.model.entity.player.ui;

public class MusicPlayer {

	/**
	 * Index of the song currently playing
	 */
	private int currentlyPlaying;
	
	/**
	 * Indicates the songs are auto playing
	 */
	private boolean autoplay;

	/**
	 * Loops the current song
	 */
	private boolean loop;

	/**
	 * @return the currentlyPlaying
	 */
	public int getCurrentlyPlaying() {
		return currentlyPlaying;
	}

	/**
	 * @param currentlyPlaying the currentlyPlaying to set
	 */
	public void setCurrentlyPlaying(int currentlyPlaying) {
		this.currentlyPlaying = currentlyPlaying;
	}

	/**
	 * @return the autoplay
	 */
	public boolean isAutoplay() {
		return autoplay;
	}

	/**
	 * @param autoplay the autoplay to set
	 */
	public void setAutoplay(boolean autoplay) {
		this.autoplay = autoplay;
	}

	/**
	 * @return the loop
	 */
	public boolean isLoop() {
		return loop;
	}

	/**
	 * @param loop the loop to set
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

}
