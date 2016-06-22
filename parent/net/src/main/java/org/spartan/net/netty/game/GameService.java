package org.spartan.net.netty.game;

import java.net.SocketAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.cdi.Container;
import org.spartan.net.Service;
import org.spartan.net.ServiceException;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.game.GameMessageRepository;
import org.spartan.net.netty.handler.log.Log4j2LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class GameService implements Service {

	/**
	 * The static logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(GameService.class);

	/**
	 * The server bootstrap
	 */
	private final ServerBootstrap bootstrap = new ServerBootstrap();
	
	/**
	 * The channel initializer
	 */
	private final MessageRepository repository = new GameMessageRepository();
	
	/**
	 * The container for this class
	 */
	private final Container container;

	/**
	 * @param container
	 */
	public GameService(Container container) {
		this.container = container;
	}

	/**
	 * 
	 * @throws Exception
	 */
	@Override
	public final void startup() throws Exception {
		InternalLoggerFactory.setDefaultFactory(new Log4j2LoggerFactory());
		
		/*
		 * Initialize the repository
		 */
		repository.initialize(container);
	}
	
	@Override
	public void bind(SocketAddress address) throws Exception {
		EventLoopGroup boss_group = new NioEventLoopGroup();
		EventLoopGroup worker_group = new NioEventLoopGroup();
		try {
			bootstrap.group(boss_group, worker_group).channel(NioServerSocketChannel.class)
					.childHandler(new GameChannelInitializer(repository, container))
					.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture channel_future = bootstrap.bind(address).sync();
			logger.info("Game service online on {}", address);
			channel_future.channel().closeFuture().sync();
		} catch (Exception ex) {
			throw new ServiceException("could not start Netty service", ex);
		} finally {
			worker_group.shutdownGracefully();
			boss_group.shutdownGracefully();
		}
	}

}
