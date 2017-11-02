package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockKiln;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerKiln;

public class TileEntityKiln extends TileEntityKilnBase
{
	public TileEntityKiln()
	{
		super(new ItemHandlerKiln(3));
		processTime = 250;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.kiln";
	}

	@Override
	public void updateBlockState()
	{
		BlockKiln.updateBlockState(burnTime > 0, world, pos);
	}
}
