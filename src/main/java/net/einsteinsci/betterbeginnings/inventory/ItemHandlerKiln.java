package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityKilnBase;
import net.minecraft.item.ItemStack;

public class ItemHandlerKiln extends ItemHandlerBase
{
	public ItemHandlerKiln(int slots) 
	{
		super(slots);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
	{
		if(slot == TileEntityKilnBase.SLOT_FUEL)
			return FuelRegistry.getBurnTime(FuelConsumerType.KILN, stack) > 0;
		return true;
	}
	
	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) 
	{
		return slot != TileEntityKilnBase.SLOT_FUEL;
	}
}
