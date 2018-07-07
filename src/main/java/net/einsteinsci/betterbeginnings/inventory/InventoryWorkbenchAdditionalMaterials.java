package net.einsteinsci.betterbeginnings.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryWorkbenchAdditionalMaterials implements IInventory
{
	private NonNullList<ItemStack> stackList;

	private Container container;

	public InventoryWorkbenchAdditionalMaterials(Container container_, int size)
	{
		stackList = NonNullList.withSize(size, ItemStack.EMPTY);
		container = container_;
	}

	@Override
	public int getSizeInventory()
	{
		return stackList.size();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (getSizeInventory() <= slot)
			return ItemStack.EMPTY;
		else
			return stackList.get(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (!stackList.get(slot).isEmpty())
		{
			ItemStack itemstack;

			if (stackList.get(slot).getCount() <= amount)
			{
				itemstack = stackList.get(slot);
				stackList.set(slot, ItemStack.EMPTY);
				container.onCraftMatrixChanged(this);
				return itemstack;
			}
			else
			{
				itemstack = stackList.get(slot).splitStack(amount);

				if (stackList.get(slot).getCount() == 0)
					stackList.set(slot, ItemStack.EMPTY);

				container.onCraftMatrixChanged(this);
				return itemstack;
			}
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		if (!stackList.get(slot).isEmpty())
			return stackList.set(slot, ItemStack.EMPTY);
		else
			return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		stackList.set(slot, stack);
		container.onCraftMatrixChanged(this);
	}
	
	@Override
	public boolean isEmpty() 
	{
		for(ItemStack stack : stackList)
		{
			if(!stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public String getName()
	{
		return "container.workbenchmaterials";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TextComponentString(getName());
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public void markDirty()
	{
		container.onCraftMatrixChanged(this);
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{ }

	@Override
	public void closeInventory(EntityPlayer player)
	{ }

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return true;
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value)
	{ }

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		stackList = NonNullList.withSize(stackList.size(), ItemStack.EMPTY);
	}
}
