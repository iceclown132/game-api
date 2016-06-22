package org.spartan.net.netty.game.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.cdi.Container;
import org.spartan.cdi.scope.component.Component;
import org.spartan.cdi.scope.component.Session;
import org.spartan.cdi.util.ReflectionUtil;
import org.spartan.net.OutputStream;
import org.spartan.net.event.Disconnect;
import org.spartan.net.netty.NettyOutputStream;
import org.spartan.net.security.ISAACPair;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

@Component
public class GameChannel extends ChannelHandlerAdapter {
	
	/**
	 * The static logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(GameChannel.class);

	/**
	 * The container
	 */
	private final Container container;
	
	/**
	 * This channel's session
	 */
	private final Session session = new Session();
	
	/**
	 * The currently connnected state
	 */
	private ConnectionState connectionState = ConnectionState.HANDSHAKE;
	
	/**
	 * The isaac pair
	 */
	private ISAACPair isaac;

	/**
	 * @param container
	 */
	public GameChannel(SocketChannel channel, Container container) {
		this.container = container;
		this.container.manager().associate(GameChannel.class, this, session);
		this.container.manager().associate(Session.class, session, session);
		this.container.manager().associate(OutputStream.class, new NettyOutputStream(channel), session);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		logger.info("Event {} received from {}", msg, ctx.channel().remoteAddress());
		container.event(msg.getClass()).fire(ReflectionUtil.cast(msg), session);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		container.event(Disconnect.class).fire(new Disconnect(), session);
		session.setInactive(true);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.channel().close();
		if (cause.getCause() != null) {
			cause.getCause().printStackTrace();
		}
	}

	/**
	 * @return the connectionState
	 */
	public ConnectionState getConnectionState() {
		return connectionState;
	}

	/**
	 * @param connectionState the connectionState to set
	 */
	public void setConnectionState(ConnectionState connectionState) {
		this.connectionState = connectionState;
	}

	/**
	 * @return the isaac
	 */
	public ISAACPair getIsaac() {
		return isaac;
	}

	/**
	 * @param isaac the isaac to set
	 */
	public void setIsaac(ISAACPair isaac) {
		this.isaac = isaac;
	}

	/**
	 * @author brock
	 */
	public static enum ConnectionState {
		HANDSHAKE, AUTHENTICATION, GAME, DISCONNECTED, UNDEFINED;
	}

}
