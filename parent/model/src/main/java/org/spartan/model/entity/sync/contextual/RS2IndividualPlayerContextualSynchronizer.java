package org.spartan.model.entity.sync.contextual;

import java.util.Iterator;

import org.spartan.model.entity.player.IndividualPlayerSynchronizationContext;
import org.spartan.model.entity.player.Player;
import org.spartan.model.entity.player.RenderedPlayer;
import org.spartan.model.entity.sync.render.RenderedEntity;
import org.spartan.net.message.Message;
import org.spartan.net.util.BitOutputStream;
import org.spartan.net.util.ByteBufOutputStream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * An abstract implementation for the RS2 player synchronization protocol. 
 * 
 * Client-version specific details are kept away and need to be implemented when
 * extending this class.
 * 
 * @author brock
 */
public abstract class RS2IndividualPlayerContextualSynchronizer extends AbstractIndividualPlayerContextualSynchronizer {

	/**
	 * 
	 * @param other_player
	 * @param bitstream
	 */
	protected abstract void desynchronize(RenderedEntity<Player> other_player, BitOutputStream bitstream);
	
	/**
	 * 
	 * @param player
	 * @param bitstream
	 */
	protected abstract void synchronizeSelf(RenderedEntity<Player> player, BitOutputStream bitstream);
	
	/**
	 * 
	 * @param other
	 * @param bitstream
	 */
	protected abstract void synchronizeOther(RenderedEntity<Player> other, BitOutputStream bitstream);
	
	/**
	 * 
	 * @param other
	 * @param bitstream
	 */
	protected abstract void synchronizeNew(RenderedEntity<Player> self, RenderedEntity<Player> other, BitOutputStream bitstream);

	/**
	 * Wraps both of the buffers in a message
	 * 
	 * @param bitstream
	 * @param payload
	 * @return
	 */
	protected abstract Message wrap(ByteBuf packet, ByteBuf payload);

	@Override
	public void synchronize(IndividualPlayerSynchronizationContext context) {
		ByteBuf payload = Unpooled.buffer();
		ByteBufOutputStream bytebuf_stream = new ByteBufOutputStream(Unpooled.buffer());
		BitOutputStream bitstream = new BitOutputStream(bytebuf_stream);
		
		/*
		 * Synchronize our own player
		 */
		synchronizeSelf(context.getPlayer(), bitstream);
		
		/*
		 * Update player if required
		 */
		if (!context.getPlayer().getRender().empty()) {
			payload.writeBytes(context.getPlayer().getRender().buffer());
		}
		
		/*
		 * Write the amount of players currently in the player's scope
		 */
		bitstream.write((byte) context.getOwner().getViewScope().getPlayers().size());
		for (Iterator<RenderedEntity<Player>> iterator = context.getOwner().getViewScope().getPlayers().iterator(); iterator.hasNext(); ) {
			RenderedEntity<Player> other_player = iterator.next();

			/*
			 * If the other player does not exist, remove it from the list
			 */
			if (other_player.getEntity().getUser().getSession().inactive()) {
				desynchronize(other_player, bitstream);
				iterator.remove();
			}

			/*
			 * Otherwise synchronize the player's position and render the player
			 */
			else {
				synchronizeOther(other_player, bitstream);
				if (!other_player.getRender().empty()) {
					payload.writeBytes(other_player.getRender().buffer());
				}
			}
		}
		
		/*
		 * Loop through all of the players in the vicinity
		 */
		for (Iterator<RenderedEntity<Player>> iterator = context.entities().iterator(); iterator.hasNext(); ) {
			RenderedEntity<Player> other_player = iterator.next();

			/*
			 * No need to update players twice. Only those who have been newly
			 * discovered need to be included here.
			 */
			if (other_player.getEntity() != context.getOwner() && !context.getOwner().getViewScope().contains(other_player.getEntity())) {
				/*
				 * Add the new player to the player's client
				 */
				context.getOwner().getViewScope().add(other_player);
				synchronizeNew(context.getPlayer(), other_player, bitstream);

				/*
				 * If the player is a newly discovered player, the appearance
				 * update flag needs to be forcefully set in order to make the
				 * player visible
				 */
				if (!other_player.getRender().empty()) {
					payload.writeBytes(((RenderedPlayer) other_player).getAlternateRender().buffer());
				}
			}
		}
		
		/*
		 * Close off the bit_vector with a player id of 2047
		 */
		if (payload.isReadable()) {
			bitstream.write(11, 2047);
		}
		
		/*
		 * Wrap the packet in a message and write it to the client
		 */
		context.getOwner().getOutputStream().write(wrap(bytebuf_stream.buffer(), payload));
	}

}
