package net.einsteinsci.betterbeginnings.network;

import io.netty.buffer.ByteBuf;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketCampfireState implements IMessage
{
	BlockPos pos;

	byte state;

	public static class PacketHandler extends PacketHandlerBase<PacketCampfireState, IMessage>
	{
		@Override
		protected void processMessage(PacketCampfireState message, MessageContext ctx) 
		{
			EntityPlayer player = ModMain.proxy.getPlayerFromMessageContext(ctx);

			TileEntityCampfire campfire = (TileEntityCampfire)player.world.getTileEntity(message.pos);

			if (campfire != null)
			{
				campfire.campfireState = message.state;
			}
		}
	}

	public PacketCampfireState()
	{
		pos = new BlockPos(0, 0, 0);

		state = 0;
	}

	public PacketCampfireState(BlockPos _pos, byte _state)
	{
		pos = _pos;

		state = _state;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		state = buf.readByte();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		buf.writeByte(state);
	}
}
