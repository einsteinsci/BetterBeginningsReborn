package net.einsteinsci.betterbeginnings;

import java.io.File;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.commands.JsonGenerateCommand;
import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.config.BBConfigFolderLoader;
import net.einsteinsci.betterbeginnings.crafttweaker.CraftTweakerCompat;
import net.einsteinsci.betterbeginnings.event.BBEventHandler;
import net.einsteinsci.betterbeginnings.network.*;
import net.einsteinsci.betterbeginnings.register.*;
import net.einsteinsci.betterbeginnings.register.achievement.RegisterAchievements;
import net.einsteinsci.betterbeginnings.register.recipe.elements.ElementRegistry;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.einsteinsci.betterbeginnings.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModMain.MODID, version = ModMain.VERSION, name = ModMain.NAME,
     guiFactory = "net.einsteinsci.betterbeginnings.config.BBConfigGuiFactory")
public class ModMain
{
	public static final String MODID = "betterbeginnings";
	public static final String VERSION = "b0.9.8-R7c";
	public static final String NAME = "BetterBeginnings";
	
	@Instance(ModMain.MODID)
	public static ModMain modInstance;
	
	public static final CreativeTabs tabBetterBeginnings = new CreativeTabs("tabBetterBeginnings")
	{
		@Override
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem()
		{
			return RegisterItems.flintKnife;
		}
	};
	
	public static Configuration configFile;
	
	public BBEventHandler eventHandler = new BBEventHandler();

	@SidedProxy(clientSide = "net.einsteinsci.betterbeginnings.network.ClientProxy",
	            serverSide = "net.einsteinsci.betterbeginnings.network.ServerProxy")
	public static ServerProxy proxy;
	public static SimpleNetworkWrapper network;

	static
	{
		//Activate ze universal bucket!
		FluidRegistry.enableUniversalBucket();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e)
	{
		LogUtil.logDebug("Starting pre-initialization...");

		configFile = BBConfigFolderLoader.getConfigFile(e);
		configFile.load();
		BBConfig.initialize();
		BBConfig.syncConfig(configFile);
		
		MinecraftForge.EVENT_BUS.register(eventHandler);

		network = NetworkRegistry.INSTANCE.newSimpleChannel("bbchannel");
		network.registerMessage(PacketNetherBrickOvenFuelLevel.PacketHandler.class,
			PacketNetherBrickOvenFuelLevel.class, 0, Side.CLIENT);
		network.registerMessage(PacketCampfireState.PacketHandler.class,
			PacketCampfireState.class, 1, Side.CLIENT);

		RegisterItems.register();
		RegisterBlocks.register();
		RegisterEntities.register();
		RegisterTileEntities.register();
		FuelRegistry.addDefaultFuels();
		ElementRegistry.init();
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e)
	{
		proxy.init(e);

		BBConfigFolderLoader.loadRemovedRecipes(e);

		RemoveRecipes.remove();
		if (BBConfig.moduleFurnaces)
		{
			RemoveRecipes.removeFurnaceRecipes();
		}

		RegisterRecipes.addShapelessRecipes();
		RegisterRecipes.addShapedRecipes();
		RegisterRecipes.addAdvancedRecipes();
		RegisterRecipes.addFurnaceRecipes();
		InfusionRepairUtil.registerVanillaEnchantsConfig();
		TileEntitySmelterBase.registerDefaultBoosters();

		BBConfigFolderLoader.loadRecipes(e);
		if(!BBConfigFolderLoader.wasLoadingSuccessfull())
		{
		    LogUtil.log(Level.ERROR, "If you have not modified the JSON recipe files, they may be corrupt. Please delete them and relaunch Minecraft, they will be regenerated."
		    	+ "\n If you have modified them, check your modifications are correct.");
		    FMLCommonHandler.instance().exitJava(0, false);
		}
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e)
	{
		BBConfig.fillAlwaysBreakable();
		BBConfig.fillAlsoPickaxes();
		BBConfig.fillAlsoAxes();

		MinecraftForge.EVENT_BUS.register(RegisterItems.wickerShield);
		RegisterItems.tweakVanilla();
		AchievementPage.registerAchievementPage(new AchievementPage(NAME, RegisterAchievements.getAchievements()));
		if(Loader.isModLoaded("MineTweaker3")) CraftTweakerCompat.register();
		LogUtil.logDebug("Finished post-initialization.");
	}

	@EventHandler
	public void serverLoad(FMLServerStartingEvent e)
	{
		e.registerServerCommand(new JsonGenerateCommand());
	}
	
	@EventHandler
	public void remapIDs(FMLMissingMappingsEvent e)
	{
	    for(MissingMapping mapping : e.get())
	    {
		switch (mapping.type)
		{
		case BLOCK:
		    mapping.remap(RegistryUtil.getBlockFromRegistry(Util.CASE_CONVERTER.convert(mapping.name)));
		    break;
		    
		case ITEM:
		    mapping.remap(RegistryUtil.getItemFromRegistry(Util.CASE_CONVERTER.convert(mapping.name)));
		    break;

		default:
		    break;
		}
	    }
	}
}
