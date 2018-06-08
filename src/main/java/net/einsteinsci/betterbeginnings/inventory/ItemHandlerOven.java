package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerOven extends ItemHandlerBase {
	public ItemHandlerOven(int slots) {
		super(slots);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
		return slot != TileEntityOvenBase.FUEL || TileEntityFurnace.isItemFuel(stack);
	}

	@Override
	public boolean canExtractItem(int slot, int amount, boolean simulate) {
		return slot != TileEntityOvenBase.FUEL;
	}
}
