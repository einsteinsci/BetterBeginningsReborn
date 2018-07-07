package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerOven extends ItemHandlerBase
{
    boolean adv;
    public ItemHandlerOven(int slots, boolean adv) 
    {
	super(slots);
	this.adv = adv;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
    {
	return slot != TileEntityOvenBase.FUEL || TileEntityFurnace.isItemFuel(stack);
    }

    @Override
    public boolean canExtractItem(int slot, int amount, boolean simulate)
    {
        if (adv) 
	{
		if (slot == TileEntityOvenBase.FUEL)
			return Util.isEmptyFluidContainer(getStackInSlot(slot));
		return true;
        }
	return slot != TileEntityOvenBase.FUEL;
    }
}
