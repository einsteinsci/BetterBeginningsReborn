package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityKilnBase;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.item.ItemStack;

public class ItemHandlerObsidianKiln extends ItemHandlerBase
{
	public ItemHandlerObsidianKiln(int slots) 
	{
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
	{
		if(slot == TileEntityKilnBase.SLOT_FUEL)
		    return FuelRegistry.getBurnTime(FuelConsumerType.OBSIDIAN_KILN, stack) > 0;
		return true;
	}
	
	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) 
	{
		if(slot == TileEntityKilnBase.SLOT_FUEL)
		{
			return Util.isEmptyFluidContainer(getStackInSlot(slot));
		}
		return true;
	}
}
