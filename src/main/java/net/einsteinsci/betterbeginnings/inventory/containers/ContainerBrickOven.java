package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.inventory.slots.SlotConfigurableAccess;
import net.einsteinsci.betterbeginnings.inventory.slots.SlotOutput;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityBrickOven;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

public class ContainerBrickOven extends ContainerSpecializedFurnace<TileEntityBrickOven> {
	public ContainerBrickOven(EntityPlayer player, TileEntityBrickOven tileEntityBrickOven) {
		super(tileEntityBrickOven);
		PlayerInvWrapper playerInv = (PlayerInvWrapper) player
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotConfigurableAccess(itemHandler, TileEntityBrickOven.FUEL, 92, 58));
		addSlotToContainer(new SlotOutput(itemHandler, TileEntityBrickOven.OUTPUT, 124, 21));

		int i;

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; j++) {
				addSlotToContainer(new SlotItemHandler(itemHandler, j + i * 3 + TileEntityBrickOven.INPUTSTART,
						30 + j * 18, 17 + i * 18));
			}
		}

		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new SlotItemHandler(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i) {
			addSlotToContainer(new SlotItemHandler(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotId == TileEntityBrickOven.OUTPUT) {
				if (!mergeItemStack(itemstack1, 11, 47, true)) // move to inventory (all, avoid hotbar)
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (slotId >= 11 && slotId < 38) // non-hotbar inventory
			{
				if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!mergeItemStack(itemstack1, TileEntityBrickOven.FUEL, TileEntityBrickOven.FUEL + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (BrickOvenRecipeHandler.instance().isInRecipe(itemstack1)) {
					if (!mergeItemStack(itemstack1, TileEntityBrickOven.INPUTSTART, TileEntityBrickOven.INPUTSTART + 9,
							false)) // move to craft matrix
					{
						return ItemStack.EMPTY;
					}
				} else if (!mergeItemStack(itemstack1, 38, 47, false)) // move to hotbar
				{
					return ItemStack.EMPTY;
				}
			} else if (slotId > 37 && slotId <= 46) // hotbar
			{
				if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!mergeItemStack(itemstack1, TileEntityBrickOven.FUEL, TileEntityBrickOven.FUEL + 1, false)) {
						return ItemStack.EMPTY;
					}
				} else if (BrickOvenRecipeHandler.instance().isInRecipe(itemstack1)) {
					if (!mergeItemStack(itemstack1, TileEntityBrickOven.INPUTSTART, TileEntityBrickOven.INPUTSTART + 9,
							false)) // move to craft matrix
					{
						return ItemStack.EMPTY;
					}
				} else if (!mergeItemStack(itemstack1, 11, 38, false)) // move to inventory (non-hotbar)
				{
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(itemstack1, 11, 47, false)) // move to inventory (all)
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}
}
