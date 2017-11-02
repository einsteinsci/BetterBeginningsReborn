package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.item.ItemStack;

public class ItemHandlerNetherBrickOven extends ItemHandlerBase
{
	public ItemHandlerNetherBrickOven(int slots) 
	{
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
	{
		if(slot == TileEntityOvenBase.FUEL)
			return TileEntityNetherBrickOven.isItemFuelContainer(stack);
		return true;
	}
	
	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) 
	{
		if(slot == TileEntityOvenBase.FUEL)
		{
			return Util.isEmptyFluidContainer(getStackInSlot(slot));
		}
		return true;
	}
}
