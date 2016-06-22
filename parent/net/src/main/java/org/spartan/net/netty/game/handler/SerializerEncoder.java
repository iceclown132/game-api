package org.spartan.net.netty.game.handler;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.OutgoingMessageDefinition;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class SerializerEncoder extends MessageToMessageEncoder<Object> {

	/**
	 * The logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(SerializerEncoder.class);

	/**
	 * The message repository
	 */
	private final MessageRepository repository;

	/**
	 * @param repository
	 */
	public SerializerEncoder(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
		/*
		 * There is no need for messages to be converted to messages
		 */
		if (msg instanceof Message) {
			out.add(msg);
		}
		
		/*
		 * Look for the correct serializer and bamboozle it
		 */
		else {
			Set<OutgoingMessageDefinition> definitions = repository.outgoingTypedDefinition(msg.getClass());
			if (definitions == null || definitions.size() == 0) {
				logger.warn("no serializer found for {}", msg.getClass().getName());
			}
			else {
				logger.debug("sending message {} to {}", msg.getClass().getName(), ctx.channel().remoteAddress());
				definitions.forEach(definition -> out.add(definition.getSerializer().serialize(msg, definition)));
			}
		}
	}

	

}
