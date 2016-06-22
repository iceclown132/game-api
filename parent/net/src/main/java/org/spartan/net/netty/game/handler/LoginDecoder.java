package org.spartan.net.netty.game.handler;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.game.HeadlessMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class LoginDecoder extends MessageToMessageDecoder<HeadlessMessage> {

	/**
	 * The event decoder
	 */
	private static final Logger logger = LogManager.getLogger(LoginDecoder.class);

	/**
	 * The template repository
	 */
	private final MessageRepository repository;

	/**
	 * @param definitions
	 * @param loginDefinition
	 */
	public LoginDecoder(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, HeadlessMessage msg, List<Object> out) throws Exception {
		Set<IncomingMessageDefinition> definitions = repository.incomingStatefulDefinition(msg.getConnectionState());
		if (definitions == null || definitions.size() == 0) {
			logger.warn("[LOGIN] no stateful decoder for {}", msg.getConnectionState());
		}
		else definitions.forEach(definition -> out.add(definition.getDecorator().decorate(msg, definition)));
	}

}
