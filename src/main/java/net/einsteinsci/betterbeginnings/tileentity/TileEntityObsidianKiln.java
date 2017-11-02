package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockObsidianKiln;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerObsidianKiln;

public class TileEntityObsidianKiln extends TileEntityKilnBase
{
	public TileEntityObsidianKiln()
	{
		super(new ItemHandlerObsidianKiln(3));
		processTime = 100;
	}
	
	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.obsidianKiln";
	}

	@Override
	public void updateBlockState()
	{
		BlockObsidianKiln.updateBlockState(burnTime > 0, world, pos);
	}
}

