package net.einsteinsci.betterbeginnings.network;

import io.netty.buffer.ByteBuf;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketNetherBrickOvenFuelLevel implements IMessage
{
	BlockPos pos;

	FluidStack fluidStack;

	public static class PacketHandler extends PacketHandlerBase<PacketNetherBrickOvenFuelLevel, IMessage>
	{
		protected void processMessage(PacketNetherBrickOvenFuelLevel message, MessageContext ctx)
		{
			EntityPlayer player = ModMain.proxy.getPlayerFromMessageContext(ctx);

			TileEntityNetherBrickOven oven = (TileEntityNetherBrickOven)player.world.getTileEntity(message.pos);
			oven.setFuelLevel(message.fluidStack);
		}
	}

	public PacketNetherBrickOvenFuelLevel()
	{
		pos = BlockPos.ORIGIN;

		fluidStack = null;
	}

	public PacketNetherBrickOvenFuelLevel(BlockPos loc, FluidStack fuel)
	{
		pos = loc;

		fluidStack = fuel;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());

		int level = buf.readInt();
		String fluidId = ByteBufUtils.readUTF8String(buf);

		if (level != 0)
		{
			fluidStack = new FluidStack(FluidRegistry.getFluid(fluidId), level);
		}
		else
		{
			fluidStack = null;
		}
	}
	
	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());

		if (fluidStack != null)
		{
			buf.writeInt(fluidStack.amount);
			ByteBufUtils.writeUTF8String(buf, fluidStack.getFluid().getName());
		}
		else
		{
			buf.writeInt(0);
			buf.writeBytes(("").getBytes());
		}
	}
}
