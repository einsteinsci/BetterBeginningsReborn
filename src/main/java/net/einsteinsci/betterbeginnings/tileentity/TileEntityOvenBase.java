package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraft.item.ItemStack;

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

		if (mainHandler.getStackInSlot(OUTPUT).isEmpty())// STACKNULL
		{
			mainHandler.setStackInSlot(OUTPUT, itemStack.copy());
		} else if (mainHandler.getStackInSlot(OUTPUT).getItem() == itemStack.getItem()) {
			CapUtils.incrementStack(mainHandler, OUTPUT, itemStack.getCount());
		}

		for (int i = INPUTSTART; i < mainHandler.getSlots(); ++i) {
			ItemStack stack = mainHandler.getStackInSlot(i);
			if (!stack.isEmpty())// STACKNULL
			{
				ItemStack containerItem = ForgeHooks.getContainerItem(mainHandler.getStackInSlot(i));
				if (!containerItem.isEmpty())// STACKNULL
				{
					mainHandler.setStackInSlot(i, containerItem);
				} else {
					CapUtils.decrementStack(mainHandler, i, 1);

					if (mainHandler.getStackInSlot(i).getCount() <= 0) {
						mainHandler.setStackInSlot(i, ItemStack.EMPTY);// STACKNULL
					}
				}
			}
		}
	}

	@Override
	public boolean canSmelt() {
		boolean empty = true;
		for (int i = INPUTSTART; i < mainHandler.getSlots(); ++i) {
			// STACKNULL
			if (!mainHandler.getStackInSlot(i).isEmpty()) {
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
			if (mainHandler.getStackInSlot(OUTPUT).isEmpty()) {
				return true;
			}
			if (!mainHandler.getStackInSlot(OUTPUT).isItemEqual(stack)) {
				return false;
			}

			int result = mainHandler.getStackInSlot(OUTPUT).getCount() + stack.getCount();
			return result <= mainHandler.getStackInSlot(OUTPUT).getMaxStackSize();
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
				currentItemBurnLength = burnTime = FuelRegistry.getBurnTime(FuelConsumerType.getFromInstance(this),
						mainHandler.getStackInSlot(FUEL));

				if (burnTime > 0) {
					flag1 = true;
					if (!mainHandler.getStackInSlot(FUEL).isEmpty())// STACKNULL
					{
						CapUtils.decrementStack(mainHandler, FUEL, 1);
						if (mainHandler.getStackInSlot(FUEL).getCount() == 0) {
							mainHandler.setStackInSlot(FUEL,
									ForgeHooks.getContainerItem(mainHandler.getStackInSlot(FUEL)));
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
		return mainHandler.getStackInSlot(INPUTSTART + row + column * 3);
	}

	public abstract ItemStack findMatchingRecipe();

	public abstract void updateBlockState();

	public void updateNetwork() {
	}
}
