package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.item.ItemStack;

public class ItemHandlerOven extends ItemHandlerBase
{
    public ItemHandlerOven(int slots) 
    {
	super(slots);
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
    {
	return slot != TileEntityOvenBase.FUEL || FuelRegistry.getBurnTime(FuelConsumerType.OVEN, stack) > 0;
    }

    @Override
    public boolean canExtractItem(int slot, int amount, boolean simulate)
    {
	return slot != TileEntityOvenBase.FUEL;
    }
}
