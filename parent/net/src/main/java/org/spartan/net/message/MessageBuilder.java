package org.spartan.net.message;

import org.spartan.net.message.game.GameMessage;
import org.spartan.net.message.game.HeadlessMessage;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class MessageBuilder {

	/**
	 * The opcode of the messag being built
	 */
	private int opcode;
	
	/**
	 * Indicates the size is sent in the header
	 */
	private boolean variableSize;
	
	/**
	 * Payload 
	 */
	private ByteBuf payload;
	
	public MessageBuilder() {
		this.payload = ByteBufAllocator.DEFAULT.buffer();
	}

	/**
	 * @param opcode
	 * @return
	 */
	public MessageBuilder opcode(int opcode) {
		this.opcode = opcode;
		return this;
	}

	/**
	 * @param opcode
	 * @return
	 */
	public MessageBuilder variableSize(boolean variableSize) {
		this.variableSize = variableSize;
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeByte(int)
	 */
	public MessageBuilder writeByte(int value) {
		payload.writeByte(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeShort(int)
	 */
	public MessageBuilder writeShort(int value) {
		payload.writeShort(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeMedium(int)
	 */
	public MessageBuilder writeMedium(int value) {
		payload.writeMedium(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeInt(int)
	 */
	public MessageBuilder writeInt(int value) {
		payload.writeInt(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeLong(long)
	 */
	public MessageBuilder writeLong(long value) {
		payload.writeLong(value);
		return this;
	}

	/**
	 * @param value
	 * @return
	 * @see io.netty.buffer.ByteBuf#writeLong(long)
	 */
	public MessageBuilder writeBytes(byte... value) {
		payload.writeBytes(value);
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public Message raw() {
		return new HeadlessMessage(ConnectionState.UNDEFINED, payload);
	}

	/**
	 * 
	 * @param payload
	 * @return
	 */
	public MessageBuilder write(ByteBuf payload) {
		this.payload.writeBytes(payload);
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public Message build() {
		return new GameMessage(opcode, variableSize ? -1 : 0, payload);
	}

}
