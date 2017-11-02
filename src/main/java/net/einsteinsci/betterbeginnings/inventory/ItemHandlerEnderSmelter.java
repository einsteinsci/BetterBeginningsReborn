package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.item.ItemStack;

public class ItemHandlerEnderSmelter extends ItemHandlerBase
{
	public ItemHandlerEnderSmelter(int slots) 
	{
		super(slots);
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
	{
		if (slot == TileEntitySmelterBase.FUEL && FuelRegistry.getBurnTime(FuelConsumerType.ENDER_SMELTER, stack) > 0)
		{
			return true;
		}
		return super.canInsertItem(slot, stack, simulate);
	}
}
