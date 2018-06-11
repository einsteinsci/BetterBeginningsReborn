package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerSmelter extends ItemHandlerBase {
	boolean adv;
	public ItemHandlerSmelter(int slots, boolean adv) {
		super(slots);
		this.adv = adv;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot == TileEntitySmelterBase.FUEL && TileEntityFurnace.isItemFuel(stack)) {
            return true;
        }

        if (adv) {
            return super.canInsertItem(slot, stack, simulate);
        }

		if (slot == TileEntitySmelterBase.BOOSTER && TileEntitySmelterBase.isBooster(stack)) {
			return true;
		}

		return slot == TileEntitySmelterBase.INPUT;
	}
}
