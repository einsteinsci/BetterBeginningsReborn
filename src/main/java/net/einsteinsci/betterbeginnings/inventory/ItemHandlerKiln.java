package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityKilnBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerKiln extends ItemHandlerBase {
	public ItemHandlerKiln(int slots) {
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
		if (slot == TileEntityKilnBase.SLOT_FUEL)
			return TileEntityFurnace.isItemFuel(stack);
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) {
		return slot != TileEntityKilnBase.SLOT_FUEL;
	}
}
