package org.spartan.net.netty.game;

import org.spartan.cdi.Container;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.netty.game.handler.EventDecoder;
import org.spartan.net.netty.game.handler.GameChannel;
import org.spartan.net.netty.game.handler.LoginDecoder;
import org.spartan.net.netty.game.handler.MessageDecoder;
import org.spartan.net.netty.game.handler.MessageEncoder;
import org.spartan.net.netty.game.handler.SerializableEncoder;
import org.spartan.net.netty.game.handler.SerializerEncoder;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.ipfilter.UniqueIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class GameChannelInitializer extends ChannelInitializer<SocketChannel> {

	/**
	 * A default channelhandler that prohibits a user from logging in more than
	 * once
	 */
	private static final ChannelHandler IP_FILTER = new UniqueIpFilter();

	/**
	 * A default channelhandler that is responsible for logging the network
	 * output to the console
	 */
	private static final ChannelHandler LOGGER = new LoggingHandler(GameChannelInitializer.class, LogLevel.DEBUG);

	/**
	 * The template repository
	 */
	private final MessageRepository repository;
	
	/**
	 * The container
	 */
	private final Container container;

	/**
	 * @param repository
	 * @param container
	 */
	public GameChannelInitializer(MessageRepository repository, Container container) {
		this.repository = repository;
		this.container = container;
	}

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		channel.pipeline()

				/*
				 * Denies connection from IP addresses already connected
				 */
				.addLast("ip-filter", IP_FILTER)

				/*
				 * Denies connection from IP addresses already connected
				 */
				.addLast("logging-handler", LOGGER)

				/*
				 * Splits the received byte buffer into messages
				 */
				.addLast("message-decoder", new MessageDecoder(repository))

				/*
				 * Splits the received byte buffer into messages
				 */
				.addLast("event-decoder", new EventDecoder(repository))

				/*
				 * Handles all of the incoming login traffic
				 */
				.addLast("login-decoder", new LoginDecoder(repository))

				/*
				 * Encodes messages into a bytebuf
				 */
				.addLast("message-encoder", new MessageEncoder())
				
				/*
				 * Attempts to convert an object into a message for the next encoder
				 */
				.addLast("serializer-encoder", new SerializerEncoder(repository))
				
				/*
				 * Converts all of the serializable objects into messages
				 */
				.addLast("serializable-encoder", new SerializableEncoder())

				/*
				 * Distributes game messages to all of the listeners
				 */
				.addLast("game-channel", new GameChannel(channel, container));
	}

}
