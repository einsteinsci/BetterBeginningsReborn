package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerSmelter extends ItemHandlerBase {
	public ItemHandlerSmelter(int slots) {
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot == TileEntitySmelterBase.BOOSTER && TileEntitySmelterBase.isBooster(stack)) {
			return true;
		}

		if (slot == TileEntitySmelterBase.FUEL && TileEntityFurnace.isItemFuel(stack)) {
			return true;
		}
		return slot == TileEntitySmelterBase.INPUT;
	}
}
