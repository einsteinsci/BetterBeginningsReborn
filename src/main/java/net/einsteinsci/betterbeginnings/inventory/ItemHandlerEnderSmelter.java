package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerEnderSmelter extends ItemHandlerBase {
	public ItemHandlerEnderSmelter(int slots) {
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot == TileEntitySmelterBase.FUEL && TileEntityFurnace.isItemFuel(stack)) {
			return true;
		}
		return super.canInsertItem(slot, stack, simulate);
	}
}
