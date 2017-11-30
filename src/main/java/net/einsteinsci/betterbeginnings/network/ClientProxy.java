package net.einsteinsci.betterbeginnings.network;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.client.*;
import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
import net.einsteinsci.betterbeginnings.register.RegisterModels;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityInfusionRepair;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ModMain.MODID)
public class ClientProxy extends ServerProxy
{
	private Minecraft mc = Minecraft.getMinecraft();
	
	@Override
	public void preInit(FMLPreInitializationEvent e)
	{
		super.preInit(e);
		registerTileEntitySpecialRenderers();
		RenderingRegistry.registerEntityRenderingHandler(EntityThrownKnife.class, new IRenderFactory<EntityThrownKnife>() 
		{
			@Override
			public Render<EntityThrownKnife> createRenderFor(RenderManager manager) 
			{
				return new RenderThrownKnife(manager, mc.getRenderItem());
			}
		});
	}

	@Override
	public void init(FMLInitializationEvent e)
	{
		super.init(e);
	}

	public void registerTileEntitySpecialRenderers()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityInfusionRepair.class, new InfusionRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityWickerBasket.class, new WickerBasketRender());
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent e)
	{
		System.out.println("Model registration");
		RegisterModels.register();
		RegisterMetaItemRenders.init();
	}

	@Override
	public EntityPlayer getPlayerFromMessageContext(MessageContext ctx)
	{
		switch (ctx.side)
		{
			case CLIENT:
			{
				return Minecraft.getMinecraft().player;
			}
			case SERVER:
			{
				return ctx.getServerHandler().player;
			}
			default:
				LogUtil.log(Level.ERROR, "Invalid side in TestMsgHandler: " + ctx.side);
		}
		return null;
	}
}
