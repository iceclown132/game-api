package org.spartan.model.entity.sync.render;

import io.netty.buffer.ByteBuf;

public class Render {

	/**
	 * The modifier
	 */
	private final int modifier;

	/**
	 * The buffer
	 */
	private final ByteBuf buffer;

	/**
	 * @param modifier
	 * @param buffer
	 */
	public Render(int modifier, ByteBuf buffer) {
		this.modifier = modifier;
		this.buffer = buffer;
	}

	/**
	 * @return the buffer
	 */
	public ByteBuf buffer() {
		return buffer;
	}

	
	/**
	 * @return true if there are no rendering hints
	 */
	public boolean empty() {
		return modifier == 0;
	}

	
	/**
	 * @return true if the player needs an update
	 */
	public boolean update() {
		return modifier == 0;
	}

}
