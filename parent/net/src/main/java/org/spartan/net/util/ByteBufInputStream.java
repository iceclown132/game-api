package org.spartan.net.util;



import java.io.IOException;
import java.io.InputStream;

import io.netty.buffer.ByteBuf;

public class ByteBufInputStream extends InputStream {
	
	/**
	 * The netty buffer
	 */
	private ByteBuf byteBuffer;

	public ByteBufInputStream() {
	}

	public ByteBufInputStream(ByteBuf byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public ByteBuf buffer() {
		return byteBuffer;
	}

	public void buffer(ByteBuf byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public int read() throws IOException {
		return byteBuffer.readUnsignedByte();
	}

	public int read(byte[] bytes, int offset, int length) throws IOException {
		int count = Math.min(byteBuffer.readableBytes(), length);
		if (count == 0)
			return -1;
		byteBuffer.readBytes(bytes, offset, length);
		return count;
	}
}