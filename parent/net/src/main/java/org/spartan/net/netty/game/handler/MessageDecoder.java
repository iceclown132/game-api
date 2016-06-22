package org.spartan.net.netty.game.handler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spartan.net.message.IncomingMessageDefinition;
import org.spartan.net.message.Message;
import org.spartan.net.message.MessageRepository;
import org.spartan.net.message.game.GameMessage;
import org.spartan.net.message.game.HeadlessMessage;
import org.spartan.net.netty.game.handler.GameChannel.ConnectionState;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {

	/**
	 * The logger for this class
	 */
	private static final Logger logger = LogManager.getLogger(MessageDecoder.class);
	
	/**
	 * The template repository
	 */
	private final MessageRepository repository;

	/**
	 * @param definitions
	 * @param loginDefinition
	 */
	public MessageDecoder(MessageRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void decode(ChannelHandlerContext context, ByteBuf in, List<Object> object) throws Exception {
		if (!in.isReadable()) {
			logger.warn("unreadable message received - {}", in);
			return;
		}
		
		/*
		 * If the connectionstate is set to GAME, the default parser needs to handle the message
		 */
		if (ConnectionState.GAME.equals(context.channel().pipeline().get(GameChannel.class).getConnectionState())) {
			AtomicInteger opcode = new AtomicInteger(in.readUnsignedByte());

			/*
			 * if the ISAAC cipher is not null, it means isaac is enabled and should be applied
			 */
			if (context.channel().pipeline().get(GameChannel.class).getIsaac() != null) {
				opcode.set((opcode.get() - context.channel().pipeline().get(GameChannel.class).getIsaac().getDecodingCipher().next()) & 0xFF);
			}
			Set<IncomingMessageDefinition> definitions = repository.incomingNumberedDefinition(opcode.get());
			if (definitions == null || definitions.size() == 0) {
				logger.warn("no parser found for {}", opcode);
			}
			else definitions.forEach(definition -> object.add(parse(opcode.get(), definition, in)));
		}
		
		/*
		 * Otherwise the user has not completed login and needs to go through the handshake/login sequence.
		 */
		else object.add(new HeadlessMessage(context.channel().pipeline().get(GameChannel.class).getConnectionState(), in.readSlice(in.readableBytes()).retain()));
	}
	
	/**
	 * @param definition
	 * @param buffer
	 * @return
	 */
	private final Message parse(int opcode, IncomingMessageDefinition definition, ByteBuf buffer) {
		int size = definition.getSize() == -1 ? buffer.readUnsignedByte() : definition.getSize();
		return new GameMessage(opcode, size, buffer.readSlice(size).retain());
	}

}
