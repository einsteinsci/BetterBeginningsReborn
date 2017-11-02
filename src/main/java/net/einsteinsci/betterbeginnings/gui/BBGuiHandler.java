package net.einsteinsci.betterbeginnings.gui;

import net.einsteinsci.betterbeginnings.inventory.containers.*;
import net.einsteinsci.betterbeginnings.tileentity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class BBGuiHandler implements IGuiHandler
{
	public static final int KILN_ID = 0;
	public static final int SIMPLEWORKBENCH_ID = 1;
	public static final int DOUBLEWORKBENCH_ID = 2;
	public static final int BRICKOVEN_ID = 3;
	public static final int SMELTER_ID = 4;
	public static final int OBSIDIANKILN_ID = 6;
	public static final int NETHERBRICKOVEN_ID = 7;
	public static final int ENDERSMELTER_ID = 8;
	public static final int CAMPFIRE_ID = 9;

	public static final int REDSTONEKILN_ID = 10;
	public static final int FLUXEDOVEN_ID = 11;
	public static final int RESONANTSMELTER_ID = 12;
	
	public static final int WICKER_BASKET_ID = 13;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID)
		{
		case KILN_ID:
			TileEntityKiln tileEntityKiln = (TileEntityKiln)world.getTileEntity(pos);
			return new ContainerKiln(player, tileEntityKiln);

		case SIMPLEWORKBENCH_ID:
			return new ContainerSimpleWorkbench(player.inventory, world, pos);

		case DOUBLEWORKBENCH_ID:
			return new ContainerDoubleWorkbench(player.inventory, world, pos);

		case BRICKOVEN_ID:
			TileEntityBrickOven brickOven = (TileEntityBrickOven)world.getTileEntity(pos);
			return new ContainerBrickOven(player, brickOven);

		case SMELTER_ID:
			TileEntitySmelter smelter = (TileEntitySmelter)world.getTileEntity(pos);
			return new ContainerSmelter(player, smelter);

		case OBSIDIANKILN_ID:
			TileEntityObsidianKiln obsKiln = (TileEntityObsidianKiln)world.getTileEntity(pos);
			return new ContainerObsidianKiln(player, obsKiln);

		case NETHERBRICKOVEN_ID:
			TileEntityNetherBrickOven nbOven = (TileEntityNetherBrickOven)world.getTileEntity(pos);
			return new ContainerNetherBrickOven(player, nbOven);

		case ENDERSMELTER_ID:
			TileEntityEnderSmelter enderSmelter = (TileEntityEnderSmelter)world.getTileEntity(pos);
			return new ContainerEnderSmelter(player, enderSmelter);

		case CAMPFIRE_ID:
			TileEntityCampfire campfire = (TileEntityCampfire)world.getTileEntity(pos);
			return new ContainerCampfire(player, campfire);

		case WICKER_BASKET_ID:
		    TileEntityWickerBasket wickerBasket = (TileEntityWickerBasket)world.getTileEntity(pos);
		    return new ContainerWickerBasket(player, wickerBasket);
		    
		/*case REDSTONEKILN_ID:
			TileEntityRedstoneKiln redKiln = (TileEntityRedstoneKiln)world.getTileEntity(pos);
			return new ContainerRedstoneKiln(player, redKiln);*/
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);
		switch(ID)
		{
		case KILN_ID:
			TileEntityKiln tileEntityKiln = (TileEntityKiln)world.getTileEntity(pos);
			return new GuiKiln(player, tileEntityKiln);

		case SIMPLEWORKBENCH_ID:
			return new GuiSimpleWorkbench(player.inventory, world, pos);

		case DOUBLEWORKBENCH_ID:
			return new GuiDoubleWorkbench(player.inventory, world, pos);

		case BRICKOVEN_ID:
			TileEntityBrickOven brickOven = (TileEntityBrickOven)world.getTileEntity(pos);
			return new GuiBrickOven(player, brickOven);

		case SMELTER_ID:
			TileEntitySmelter smelter = (TileEntitySmelter)world.getTileEntity(pos);
			return new GuiSmelter(player, smelter);

		case OBSIDIANKILN_ID:
			TileEntityObsidianKiln obsKiln = (TileEntityObsidianKiln)world.getTileEntity(pos);
			return new GuiObsidianKiln(player, obsKiln);

		case NETHERBRICKOVEN_ID:
			TileEntityNetherBrickOven nbOven = (TileEntityNetherBrickOven)world.getTileEntity(pos);
			return new GuiNetherBrickOven(player, nbOven);

		case ENDERSMELTER_ID:
			TileEntityEnderSmelter enderSmelter = (TileEntityEnderSmelter)world.getTileEntity(pos);
			return new GuiEnderSmelter(player, enderSmelter);

		case CAMPFIRE_ID:
			TileEntityCampfire campfire = (TileEntityCampfire)world.getTileEntity(pos);
			return new GuiCampfire(player, campfire);
			
		case WICKER_BASKET_ID:
		    TileEntityWickerBasket wickerBasket = (TileEntityWickerBasket)world.getTileEntity(pos);
		    return new GuiWickerBasket(player, wickerBasket);

			/*case REDSTONEKILN_ID:
			TileEntityRedstoneKiln redKiln = (TileEntityRedstoneKiln)world.getTileEntity(pos);
			return new GuiRedstoneKiln(player.inventory, redKiln);*/
		}

		return null;
	}
}
