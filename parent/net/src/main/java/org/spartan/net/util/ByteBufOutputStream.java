package org.spartan.net.util;


import java.io.IOException;
import java.io.OutputStream;

import io.netty.buffer.ByteBuf;

public class ByteBufOutputStream extends OutputStream {
	
	/**
	 * The netty ByteBuf
	 */
	private ByteBuf byteBuffer;

	public ByteBufOutputStream(ByteBuf byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public ByteBuf buffer() {
		return byteBuffer;
	}

	public void buffer(ByteBuf byteBuffer) {
		this.byteBuffer = byteBuffer;
	}

	public void write(int b) throws IOException {
		byteBuffer.writeByte(b);
	}

	public void write(byte[] bytes, int offset, int length) throws IOException {
		byteBuffer.writeBytes(bytes, offset, length);
	}

}
