package org.spartan.net.netty.game.handler;

import org.spartan.net.message.Message;
import org.spartan.net.message.game.GameMessage;
import org.spartan.net.message.game.HeadlessMessage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author brock
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
		/*
		 * If the connectionstate is set to GAME, the default parser needs to handle the message
		 */
		if (msg instanceof GameMessage) {
			GameMessage gameMessage = (GameMessage) msg;
			
			int opcode = gameMessage.getOpcode();
			if (ctx.channel().pipeline().get(GameChannel.class).getIsaac() != null) {
				opcode += ctx.channel().pipeline().get(GameChannel.class).getIsaac().getEncodingCipher().next();
			}
			out.writeByte(opcode);
			if (gameMessage.getSize() == -1) {
				out.writeByte(gameMessage.getPayload().readableBytes());
			}
			out.writeBytes(gameMessage.getPayload());
		}
		
		/*
		 * Otherwise all of the data is being sent as raw data without a header
		 */
		else if (msg instanceof HeadlessMessage) {
			out.writeBytes(msg.getPayload());
		}
	}

}
