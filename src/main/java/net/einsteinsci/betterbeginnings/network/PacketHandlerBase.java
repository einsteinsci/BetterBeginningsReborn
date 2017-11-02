package net.einsteinsci.betterbeginnings.network;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

public abstract class PacketHandlerBase<M extends IMessage, R extends IMessage> implements IMessageHandler<M, R> 
{
	@Override
	public R onMessage(final M message, final MessageContext ctx) 
	{
		if(ctx.side == Side.SERVER)
		{
			FMLServerHandler.instance().getServer().addScheduledTask(new Runnable() 
			{
				@Override
				public void run() 
				{
					processMessage(message, ctx);
				}
				
			});
		}
		else if(ctx.side == Side.CLIENT)
		{
			Minecraft.getMinecraft().addScheduledTask(new Runnable() 
			{
				@Override
				public void run() 
				{
					processMessage(message, ctx);
				}
				
			});
		}
		return null;
	}

	protected void processMessage(M message, MessageContext ctx) 
	{
		
	}
}
