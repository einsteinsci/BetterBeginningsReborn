package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockSmelter;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerSmelter;

public class TileEntitySmelter extends TileEntitySmelterBase
{
	public TileEntitySmelter()
	{
		super(new ItemHandlerSmelter(4));
		processTime = 160;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.smelter";
	}

	@Override
	public float getBaseBoosterLevel()
	{
		return 1.0f;
	}

	@Override
	public void updateBlockState()
	{
		BlockSmelter.updateBlockState(burnTime > 0, world, pos);
	}
}
