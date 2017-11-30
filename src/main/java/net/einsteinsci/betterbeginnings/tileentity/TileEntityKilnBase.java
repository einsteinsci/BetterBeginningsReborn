package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeHandler;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class TileEntityKilnBase extends TileEntitySpecializedFurnace
{
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_FUEL = 1;
	public static final int SLOT_OUTPUT = 2;
	
	public TileEntityKilnBase(IItemHandlerModifiable mainHandlerIn)
	{
		super(mainHandlerIn, SLOT_INPUT, SLOT_FUEL, SLOT_FUEL, SLOT_OUTPUT);
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			boolean flag = burnTime > 0;
			boolean flag1 = false;

			if (burnTime > 0)
			{
				--burnTime;
			}

			if (burnTime == 0 && canSmelt())
			{
				currentItemBurnLength = burnTime = FuelRegistry.getBurnTime(FuelConsumerType.getFromInstance(this), mainHandler.getStackInSlot(SLOT_FUEL));

				if (burnTime > 0)
				{
					flag1 = true;
					if (!mainHandler.getStackInSlot(SLOT_FUEL).isEmpty())
					{
						CapUtils.decrementStack(mainHandler, SLOT_FUEL, 1);
						
						ItemStack fuel = mainHandler.getStackInSlot(SLOT_FUEL);
						if (fuel.getCount() == 0)
						{
							mainHandler.setStackInSlot(SLOT_FUEL, ForgeHooks.getContainerItem(fuel));
						}
					}
				}
			}

			if (isBurning() && canSmelt())
			{
				++cookTime;
				if (cookTime == processTime)
				{
					cookTime = 0;
					smeltItem();
					flag1 = true;
				}
			}
			else
			{
				cookTime = 0;
			}

			if (flag != burnTime > 0)
			{
				flag1 = true;
				updateBlockState();
			}

			if (flag1)
			{
				markDirty();
			}
		}
	}

	@Override
	public boolean canSmelt()
	{
		if (mainHandler.getStackInSlot(SLOT_INPUT).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack stack = KilnRecipeHandler.instance().getSmeltingResult(mainHandler.getStackInSlot(SLOT_INPUT));
			if (stack.isEmpty())
			{
				return false;
			}

			if (mainHandler.getStackInSlot(SLOT_OUTPUT).isEmpty())
			{
				return true;
			}
			if (!mainHandler.getStackInSlot(SLOT_OUTPUT).isItemEqual(stack))
			{
				return false;
			}

			int size = mainHandler.getStackInSlot(SLOT_OUTPUT).getCount() + stack.getCount();
			return size <= mainHandler.getStackInSlot(SLOT_OUTPUT).getMaxStackSize();
		}
	}

	@Override
	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack itemStack = KilnRecipeHandler.instance().getSmeltingResult(mainHandler.getStackInSlot(SLOT_INPUT));

			if (mainHandler.getStackInSlot(SLOT_OUTPUT).isEmpty())
			{
				mainHandler.setStackInSlot(SLOT_OUTPUT, itemStack.copy());
			}
			else if (mainHandler.getStackInSlot(SLOT_OUTPUT).getItem() == itemStack.getItem())
			{
				CapUtils.incrementStack(mainHandler, SLOT_OUTPUT, itemStack.getCount());
			}

			CapUtils.decrementStack(mainHandler, SLOT_INPUT, 1);

			if (mainHandler.getStackInSlot(SLOT_INPUT).getCount() <= 0)
			{
				mainHandler.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
	    super.readFromNBT(tagCompound);
	    currentItemBurnLength = FuelRegistry.getBurnTime(FuelConsumerType.getFromInstance(this), mainHandler.getStackInSlot(SLOT_FUEL));
	}

	public abstract void updateBlockState();
}
