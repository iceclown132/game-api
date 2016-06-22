package org.spartan.net.netty.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;

public class ByteBufferUtil {

	/**
	 * The string delimiter character
	 */
	private static final char DELIMITER = 0x0a;

	/**
	 * Reads an ASCII string that is terminated with character 0x0a
	 * 
	 * @param buffer
	 * @return
	 */
	public static String readString(ByteBuffer buffer) {
		StringBuilder builder = new StringBuilder();
		for (byte b = buffer.get(); b != DELIMITER && buffer.hasRemaining(); b = buffer.get()) {
			builder.append((char) b);
		}
		return builder.toString();
	}

	/**
	 * Reads an ASCII string that is terminated with character 0x0a
	 * 
	 * @param buffer
	 * @return
	 */
	public static void writeString(ByteBuffer buffer, String string) {
		buffer.put(string.getBytes(Charset.forName("UTF-8"))).put((byte) DELIMITER);
	}

	/**
	 * Reads an ASCII string that is terminated with character 0x0a
	 * 
	 * @param buffer
	 * @return
	 */
	public static String readString(ByteBuf buffer) {
		StringBuilder builder = new StringBuilder();
		for (byte b = buffer.readByte(); b != DELIMITER && buffer.isReadable(); b = buffer.readByte()) {
			builder.append((char) b);
		}
		return builder.toString();
	}

	/**
	 * Reads an ASCII string that is terminated with character 0x0a
	 * 
	 * @param buffer
	 * @return
	 */
	public static void writeString(ByteBuf buffer, String string) {
		ByteBufferUtil.writeString(buffer.nioBuffer(), string);
	}

}