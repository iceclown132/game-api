package org.spartan.net.netty.game.handler;

import java.util.List;

import org.spartan.net.message.Serializable;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class SerializableEncoder extends MessageToMessageEncoder<Serializable> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Serializable msg, List<Object> out) throws Exception {
		out.add(msg.serialize());
	}

}
