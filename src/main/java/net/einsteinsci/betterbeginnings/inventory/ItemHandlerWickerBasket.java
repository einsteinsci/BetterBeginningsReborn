package net.einsteinsci.betterbeginnings.inventory;

import net.minecraft.item.ItemStack;

public class ItemHandlerWickerBasket extends ItemHandlerBase
{
    public ItemHandlerWickerBasket(int slots)
    {
	super(slots);
    }
    
    @Override
    public boolean canExtractItem(int slot, int amount, boolean simulate)
    {
	return false;
    }
    
    @Override
    public boolean canInsertItem(int slot, ItemStack stack, boolean simulate)
    {
        return false;
    }
}
