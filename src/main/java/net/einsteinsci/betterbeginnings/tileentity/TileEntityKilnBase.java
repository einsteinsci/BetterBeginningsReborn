package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeHandler;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
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
				currentItemBurnLength = burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(SLOT_FUEL));

				if (burnTime > 0)
				{
					flag1 = true;
					if (!inventory.getStackInSlot(SLOT_FUEL).isEmpty())
					{
						CapUtils.decrementStack(inventory, SLOT_FUEL, 1);
						
						ItemStack fuel = inventory.getStackInSlot(SLOT_FUEL);
						if (fuel.getCount() == 0)
						{
							inventory.setStackInSlot(SLOT_FUEL, ForgeHooks.getContainerItem(fuel));
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
		if (inventory.getStackInSlot(SLOT_INPUT).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack stack = KilnRecipeHandler.instance().getSmeltingResult(inventory.getStackInSlot(SLOT_INPUT));
			if (stack.isEmpty())
			{
				return false;
			}

			if (inventory.getStackInSlot(SLOT_OUTPUT).isEmpty())
			{
				return true;
			}
			if (!inventory.getStackInSlot(SLOT_OUTPUT).isItemEqual(stack))
			{
				return false;
			}

			int size = inventory.getStackInSlot(SLOT_OUTPUT).getCount() + stack.getCount();
			return size <= inventory.getStackInSlot(SLOT_OUTPUT).getMaxStackSize();
		}
	}

	@Override
	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack itemStack = KilnRecipeHandler.instance().getSmeltingResult(inventory.getStackInSlot(SLOT_INPUT));

			if (inventory.getStackInSlot(SLOT_OUTPUT).isEmpty())
			{
				inventory.setStackInSlot(SLOT_OUTPUT, itemStack.copy());
			}
			else if (inventory.getStackInSlot(SLOT_OUTPUT).getItem() == itemStack.getItem())
			{
				CapUtils.incrementStack(inventory, SLOT_OUTPUT, itemStack.getCount());
			}

			CapUtils.decrementStack(inventory, SLOT_INPUT, 1);

			if (inventory.getStackInSlot(SLOT_INPUT).getCount() <= 0)
			{
				inventory.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
	    super.readFromNBT(tagCompound);
	    currentItemBurnLength = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(SLOT_FUEL));
	}

	public abstract void updateBlockState();
}
