package net.einsteinsci.betterbeginnings.register;

import java.util.ArrayList;
import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.blocks.BlockBrickOven;
import net.einsteinsci.betterbeginnings.blocks.BlockCampfire;
import net.einsteinsci.betterbeginnings.blocks.BlockDoubleWorkbench;
import net.einsteinsci.betterbeginnings.blocks.BlockEnderSmelter;
import net.einsteinsci.betterbeginnings.blocks.BlockInfusionRepairStation;
import net.einsteinsci.betterbeginnings.blocks.BlockKiln;
import net.einsteinsci.betterbeginnings.blocks.BlockNetherBrickOven;
import net.einsteinsci.betterbeginnings.blocks.BlockObsidianKiln;
import net.einsteinsci.betterbeginnings.blocks.BlockSmelter;
import net.einsteinsci.betterbeginnings.blocks.BlockWickerBasket;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ModMain.MODID)
public class RegisterBlocks
{
    public static final BlockKiln kiln = new BlockKiln(false);
    public static final BlockKiln kilnLit = new BlockKiln(true);
    public static final BlockDoubleWorkbench doubleWorkbench = new BlockDoubleWorkbench();
    public static final BlockBrickOven brickOven = new BlockBrickOven(false);
    public static final BlockBrickOven brickOvenLit = new BlockBrickOven(true);
    public static final BlockSmelter smelter = new BlockSmelter(false);
    public static final BlockSmelter smelterLit = new BlockSmelter(true);
    public static final BlockInfusionRepairStation infusionRepairStation = new BlockInfusionRepairStation();
    public static final BlockCampfire campfire = new BlockCampfire(false);
    public static final BlockCampfire campfireLit = new BlockCampfire(true);
    public static final BlockObsidianKiln obsidianKiln = new BlockObsidianKiln(false);
    public static final BlockObsidianKiln obsidianKilnLit = new BlockObsidianKiln(true);
    public static final BlockNetherBrickOven netherBrickOven = new BlockNetherBrickOven(false);
    public static final BlockNetherBrickOven netherBrickOvenLit = new BlockNetherBrickOven(true);
    public static final BlockEnderSmelter enderSmelter = new BlockEnderSmelter(false);
    public static final BlockEnderSmelter enderSmelterLit = new BlockEnderSmelter(true);
    public static final BlockWickerBasket wickerBasket = new BlockWickerBasket();
    //public static final BlockRedstoneKiln redstoneKiln = new BlockRedstoneKiln(false);
    //public static final BlockRedstoneKiln redstoneKilnLit = new BlockRedstoneKiln(true);

    public static final List<Block> allBlocks = new ArrayList<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e)
    {
        RegisterHelper.registerBlock(e.getRegistry(), kiln);
        RegisterHelper.registerBlock(e.getRegistry(), kilnLit);

        RegisterHelper.registerBlock(e.getRegistry(), doubleWorkbench);

        RegisterHelper.registerBlock(e.getRegistry(), brickOven);
        RegisterHelper.registerBlock(e.getRegistry(), brickOvenLit);

        RegisterHelper.registerBlock(e.getRegistry(), smelter);
        RegisterHelper.registerBlock(e.getRegistry(), smelterLit);

        RegisterHelper.registerBlock(e.getRegistry(), infusionRepairStation);

        RegisterHelper.registerBlock(e.getRegistry(), campfire);
        RegisterHelper.registerBlock(e.getRegistry(), campfireLit);

        RegisterHelper.registerBlock(e.getRegistry(), obsidianKiln);
        RegisterHelper.registerBlock(e.getRegistry(), obsidianKilnLit);

        RegisterHelper.registerBlock(e.getRegistry(), netherBrickOven);
        RegisterHelper.registerBlock(e.getRegistry(), netherBrickOvenLit);

        RegisterHelper.registerBlock(e.getRegistry(), enderSmelter);
        RegisterHelper.registerBlock(e.getRegistry(), enderSmelterLit);

        //RegisterHelper.registerBlock(e.getRegistry(), redstoneKiln);
        //RegisterHelper.registerBlock(e.getRegistry(), redstoneKilnLit);

        RegisterHelper.registerBlock(e.getRegistry(), wickerBasket);
    }
}
