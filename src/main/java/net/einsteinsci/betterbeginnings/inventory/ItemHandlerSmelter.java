package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.item.ItemStack;

public class ItemHandlerSmelter extends ItemHandlerBase
{
	public ItemHandlerSmelter(int slots) 
	{
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) 
	{
		if (slot == TileEntitySmelterBase.BOOSTER && TileEntitySmelterBase.isBooster(stack))
		{
			return true;
		}

		if (slot == TileEntitySmelterBase.FUEL && FuelRegistry.getBurnTime(FuelConsumerType.SMELTER, stack) > 0)
		{
			return true;
		}
		return slot == TileEntitySmelterBase.INPUT;
	}
}
