package net.einsteinsci.betterbeginnings.inventory;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class ItemHandlerBase extends ItemStackHandler 
{	
	public ItemHandlerBase(int slots) 
	{
		super(slots);
	}
	
	public ItemHandlerBase(ItemStack[] stacksIn) 
	{
		super(stacksIn);
	}
	
	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) 
	{
		return canInsertItem(slot, stack, simulate) ? super.insertItem(slot, stack, simulate) : stack;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) 
	{
		return canExtractItem(slot, amount, simulate) ? super.extractItem(slot, amount, simulate) : null;
	}
	
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate)
	{
		return true;
	}
	
	public boolean canExtractItem(int slot, int amount, boolean simulate)
	{
		return true;
	}
}
