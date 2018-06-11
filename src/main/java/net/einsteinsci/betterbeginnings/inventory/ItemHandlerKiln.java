package net.einsteinsci.betterbeginnings.inventory;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityKilnBase;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class ItemHandlerKiln extends ItemHandlerBase {
    private boolean adv;

    public ItemHandlerKiln(int slots, boolean adv) {
        super(slots);
        this.adv = adv;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack stack, boolean simulate) {
        if (slot == TileEntityKilnBase.SLOT_FUEL)
            return TileEntityFurnace.isItemFuel(stack);
        return true;
    }

    @Override
    public boolean canExtractItem(int slot, int amount, boolean simulate) {
        if (adv) {
            if (slot == TileEntityKilnBase.SLOT_FUEL) {
                return Util.isEmptyFluidContainer(getStackInSlot(slot));
            }
            return true;
        }
        return slot != TileEntityKilnBase.SLOT_FUEL;
    }
}
