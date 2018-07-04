package net.einsteinsci.betterbeginnings;

import java.io.File;

import net.einsteinsci.betterbeginnings.config.BBConfig;
//import net.einsteinsci.betterbeginnings.integration.crafttweaker.CraftTweakerCompat;
import net.einsteinsci.betterbeginnings.integration.crafttweaker.CraftTweakerPlugIn;
import net.einsteinsci.betterbeginnings.event.BBEventHandler;
import net.einsteinsci.betterbeginnings.network.*;
import net.einsteinsci.betterbeginnings.register.*;
//import net.einsteinsci.betterbeginnings.register.achievement.RegisterAchievements;
import net.einsteinsci.betterbeginnings.register.recipe.elements.ElementRegistry;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
//import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.*;
//import net.minecraftforge.fml.common.event.FMLMissingMappingsEvent.MissingMapping;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// 1.12 UPDATE: LIST OF DISABLED FEATURES
// - Achievements (will be replaced with equivalent advancements)
// - Item rename snippet (will be replaced with proper lowercase & underscore naming convention)

@Mod(modid = ModMain.MODID, version = ModMain.VERSION, name = ModMain.NAME, guiFactory = "net.einsteinsci.betterbeginnings.config.BBConfigGuiFactory", dependencies = ModMain.DEPENDENCIES)
public class ModMain {
    public static final String MODID = "betterbeginnings";
    public static final String VERSION = "@version@";
    public static final String NAME = "BetterBeginnings";
    public static final String CONFIG_FILENAME = "betterbeginnings.cfg";
    public static final String DEPENDENCIES = "after:crafttweaker;after:jei;after:waila;after:theoneprobe;";

    @Instance(ModMain.MODID)
    public static ModMain modInstance;

    public static final CreativeTabs tabBetterBeginnings = new CreativeTabs("tabBetterBeginnings") {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getTabIconItem() {
            return new ItemStack(RegisterItems.flintKnife);
        }
    };

    public static Configuration configFile;

    public BBEventHandler eventHandler = new BBEventHandler();

    @SidedProxy(clientSide = "net.einsteinsci.betterbeginnings.network.ClientProxy", serverSide = "net.einsteinsci.betterbeginnings.network.ServerProxy")
    public static ServerProxy proxy;
    public static SimpleNetworkWrapper network;

    static {
        // Activate ze universal bucket!
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LogUtil.logDebug("Starting pre-initialization...");

        File file = new File(e.getModConfigurationDirectory(), CONFIG_FILENAME);
        configFile = new Configuration(file);
        configFile.load();
        BBConfig.initialize();
        BBConfig.syncConfig(configFile);

        MinecraftForge.EVENT_BUS.register(eventHandler);

        network = NetworkRegistry.INSTANCE.newSimpleChannel("bbchannel");
        network.registerMessage(PacketNetherBrickOvenFuelLevel.PacketHandler.class,
                PacketNetherBrickOvenFuelLevel.class, 0, Side.CLIENT);
        network.registerMessage(PacketCampfireState.PacketHandler.class, PacketCampfireState.class, 1, Side.CLIENT);

        ElementRegistry.init();
        proxy.preInit(e);

        if (Loader.isModLoaded("crafttweaker")) {
            try {
                CraftTweakerPlugIn.init();
                //Class.forName("net.einsteinsci.betterbeginnings.integration.crafttweaker.CraftTweakerPlugIn").getMethod("init").invoke(null);
            } catch (Exception ex) {
                //throw new ReportedException(new CrashReport("Error initializing minetweaker integration", e));
            }
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);

        RemoveRecipes.remove();
        if (BBConfig.moduleFurnaces) {
            RemoveRecipes.removeFurnaceRecipes();
        }

        RegisterRecipes.register();
        TileEntitySmelterBase.registerDefaultBoosters();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        BBConfig.fillAlwaysBreakable();
        BBConfig.fillAlsoPickaxes();
        BBConfig.fillAlsoAxes();

        MinecraftForge.EVENT_BUS.register(RegisterItems.wickerShield);
        RegisterItems.tweakVanilla();
        // TODO: replace achievement registry with advancements
        // AchievementPage.registerAchievementPage(new AchievementPage(NAME,
        // RegisterAchievements.getAchievements()));
        LogUtil.logDebug("Finished post-initialization.");
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent e) {
        //e.registerServerCommand(new JsonGenerateCommand());
    }

    // Renames item and block IDs to lowercase. Will be replaced by proper item_name
    // convention with this update.
    // @EventHandler
    // public void remapIDs(FMLMissingMappingsEvent e)
    // {
    // for(MissingMapping mapping : e.get())
    // {
    // switch (mapping.type)
    // {
    // case BLOCK:
    // mapping.remap(RegistryUtil.getBlockFromRegistry(Util.CASE_CONVERTER.convert(mapping.name)));
    // break;
    //
    // case ITEM:
    // mapping.remap(RegistryUtil.getItemFromRegistry(Util.CASE_CONVERTER.convert(mapping.name)));
    // break;
    //
    // default:
    // break;
    // }
    // }
    // }
}
