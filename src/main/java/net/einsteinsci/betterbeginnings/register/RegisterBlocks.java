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
import net.einsteinsci.betterbeginnings.tileentity.TileEntityBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityEnderSmelter;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityInfusionRepair;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityKiln;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityObsidianKiln;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelter;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber(modid = ModMain.MODID)
public class RegisterBlocks {
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

	public static final List<Block> allBlocks = new ArrayList<>();

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> e) {
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

		RegisterHelper.registerBlock(e.getRegistry(), wickerBasket);

		registerTileEntities();
	}

	private static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityKiln.class, RegisterBlocks.kiln.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityObsidianKiln.class, RegisterBlocks.obsidianKiln.getRegistryName());

		GameRegistry.registerTileEntity(TileEntityBrickOven.class, RegisterBlocks.brickOven.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityNetherBrickOven.class,
				RegisterBlocks.netherBrickOven.getRegistryName());

		GameRegistry.registerTileEntity(TileEntitySmelter.class, RegisterBlocks.smelter.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityEnderSmelter.class, RegisterBlocks.enderSmelter.getRegistryName());

		GameRegistry.registerTileEntity(TileEntityCampfire.class, RegisterBlocks.campfire.getRegistryName());

		GameRegistry.registerTileEntity(TileEntityInfusionRepair.class,
				RegisterBlocks.infusionRepairStation.getRegistryName());
		GameRegistry.registerTileEntity(TileEntityWickerBasket.class, RegisterBlocks.wickerBasket.getRegistryName());
	}
}
