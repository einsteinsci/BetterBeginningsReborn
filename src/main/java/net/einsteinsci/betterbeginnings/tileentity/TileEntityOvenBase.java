package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public abstract class TileEntityOvenBase extends TileEntitySpecializedFurnace {

	public static final int OUTPUT = 0;
	public static final int FUEL = 1;
	public static final int INPUTSTART = 2;

	public TileEntityOvenBase(IItemHandlerModifiable mainHandlerIn) {
		super(mainHandlerIn, FUEL, 10, OUTPUT, FUEL);
	}

	@Override
	public void smeltItem() {
		ItemStack itemStack = findMatchingRecipe();

		if (inventory.getStackInSlot(OUTPUT).isEmpty())// STACKNULL
		{
			inventory.setStackInSlot(OUTPUT, itemStack.copy());
		} else if (inventory.getStackInSlot(OUTPUT).getItem() == itemStack.getItem()) {
			CapUtils.incrementStack(inventory, OUTPUT, itemStack.getCount());
		}

		for (int i = INPUTSTART; i < inventory.getSlots(); ++i) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty())// STACKNULL
			{
				ItemStack containerItem = ForgeHooks.getContainerItem(inventory.getStackInSlot(i));
				if (!containerItem.isEmpty())// STACKNULL
				{
					inventory.setStackInSlot(i, containerItem);
				} else {
					CapUtils.decrementStack(inventory, i, 1);

					if (inventory.getStackInSlot(i).getCount() <= 0) {
						inventory.setStackInSlot(i, ItemStack.EMPTY);// STACKNULL
					}
				}
			}
		}
	}

	@Override
	public boolean canSmelt() {
		boolean empty = true;
		for (int i = INPUTSTART; i < inventory.getSlots(); ++i) {
			// STACKNULL
			if (!inventory.getStackInSlot(i).isEmpty()) {
				empty = false;
				break;
			}
		}

		if (empty) {
			return false;
		} else {
			ItemStack stack = findMatchingRecipe();
			if (stack.isEmpty())// STACKNULL
			{
				return false;
			}
			// STACKNULL
			if (inventory.getStackInSlot(OUTPUT).isEmpty()) {
				return true;
			}
			if (!inventory.getStackInSlot(OUTPUT).isItemEqual(stack)) {
				return false;
			}

			int result = inventory.getStackInSlot(OUTPUT).getCount() + stack.getCount();
			return result <= inventory.getStackInSlot(OUTPUT).getMaxStackSize();
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			boolean flag = burnTime > 0;
			boolean flag1 = false;

			if (burnTime > 0) {
				--burnTime;
			}

			if (burnTime == 0 && canSmelt()) {
				currentItemBurnLength = burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(FUEL));

				if (burnTime > 0) {
					flag1 = true;
					if (!inventory.getStackInSlot(FUEL).isEmpty())// STACKNULL
					{
						CapUtils.decrementStack(inventory, FUEL, 1);
						if (inventory.getStackInSlot(FUEL).getCount() == 0) {
							inventory.setStackInSlot(FUEL, ForgeHooks.getContainerItem(inventory.getStackInSlot(FUEL)));
						}
					}
				}
			}
			if (isBurning() && canSmelt()) {
				++cookTime;
				if (cookTime == processTime) {
					cookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				cookTime = 0;
			}

			if (flag != burnTime > 0) {
				flag1 = true;
				updateBlockState();
			}

			if (flag1) {
				markDirty();
			}
		}
	}

	public ItemStack getStackInRowAndColumn(int row, int column) {
		return inventory.getStackInSlot(INPUTSTART + row + column * 3);
	}

	public abstract ItemStack findMatchingRecipe();

	public abstract void updateBlockState();

	public void updateNetwork() {
	}
}
