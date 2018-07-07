package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockEnderSmelter;
import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerSmelter;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileEntityEnderSmelter extends TileEntitySmelterBase
{
	public boolean oreDoubled = false;

	public TileEntityEnderSmelter()
	{
		super(new ItemHandlerSmelter(4, true));
		processTime = 140;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.enderSmelter";
	}

	@Override
	public float getBaseBoosterLevel()
	{
		return 1.75f;
	}

	@Override
	public void updateBlockState()
	{
		BlockEnderSmelter.updateBlockState(burnTime > 0, world, pos);
	}

	@Override
	protected int calculateFuelBurnTime(ItemStack fuelStack) 
	{
		int burnTime = 0;

		if (!fuelStack.isEmpty() && fuelStack.isItemEqual(inventory.getStackInSlot(FUEL))) 
		{
			Item item = fuelStack.getItem();
			Block block = Block.getBlockFromItem(item);

			if (item == Items.COAL)
				burnTime = BBConfig.smeltersCanUseCoal ? 1600 : 0;
			else if (block == Blocks.COAL_BLOCK)
				burnTime = BBConfig.smeltersCanUseCoal ? 16000 : 0;
			else if (item == Items.BLAZE_ROD)
				burnTime = 600;
			else if (item == Items.LAVA_BUCKET)
				burnTime = 7200;
			else if (item == Items.ENDER_PEARL)
				burnTime = 48000;
			else if (item == Items.ENDER_EYE)
				burnTime = 96000;
		}
		return burnTime;
	}
}
