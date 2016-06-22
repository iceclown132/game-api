package org.spartan.net.netty;

import org.spartan.cdi.scope.component.Component;
import org.spartan.net.OutputStream;

import io.netty.channel.socket.SocketChannel;

@Component
public class NettyOutputStream implements OutputStream {
	
	/**
	 * The socket channel for this output stream
	 */
	private final SocketChannel channel;

	/**
	 * @param channel
	 */
	public NettyOutputStream(SocketChannel channel) {
		this.channel = channel;
	}

	@Override
	public void write(Object msg) {
		channel.write(msg);
	}

	@Override
	public void flush() {
		channel.flush();
	}

	@Override
	public void writeAndFlush(Object msg) {
		channel.writeAndFlush(msg);
	}
	
	@Override
	public void close() {
		channel.close();
	}
	
	@Override
	public String toString() {
		return "OutputStream [destination=" + channel.remoteAddress() + "]";
	}

}
