package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockEnderSmelter;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerEnderSmelter;

public class TileEntityEnderSmelter extends TileEntitySmelterBase
{
	public boolean oreDoubled = false;

	public TileEntityEnderSmelter()
	{
		super(new ItemHandlerEnderSmelter(4));
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
}
