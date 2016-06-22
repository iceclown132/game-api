package org.spartan.net.netty.game.handler;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.game.GameMessage;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

@Sharable
public class EventDecoder extends MessageToMessageDecoder<GameMessage> {

	/**
	 * The event decoder
	 */
	private static final Logger logger = LogManager.getLogger(EventDecoder.class);

	/**
	 * The template repository
	 */
	private final MessageRepository repository;

	/**
	 * @param definitions
	 * @param loginDefinition
	 */
	public EventDecoder(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, GameMessage msg, List<Object> out) throws Exception {
		try {
			Set<IncomingMessageDefinition> definitions = repository.incomingNumberedDefinition(msg.getOpcode());
			if (definitions == null || definitions.size() == 0)
				logger.warn("no decorator found for {}", msg.getOpcode());
			else
				definitions.forEach(definition -> out.add(definition.getDecorator().decorate(msg, definition)));
		} finally {
			msg.getPayload().release();
		}
	}

}
