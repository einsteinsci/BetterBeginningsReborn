package net.einsteinsci.betterbeginnings.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class InventoryWorkbenchAdditionalMaterials implements IInventory
{
	private ItemStack[] stackList;

	private Container container;

	public InventoryWorkbenchAdditionalMaterials(Container container_, int size)
	{
		stackList = new ItemStack[size];
		container = container_;
	}

	@Override
	public int getSizeInventory()
	{
		return stackList.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (getSizeInventory() <= slot)
		{
			return ItemStack.EMPTY;
		}
		else
		{
			return stackList[slot];
		}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount)
	{
		if (!stackList[slot].isEmpty())
		{
			ItemStack itemstack;

			if (stackList[slot].getCount() <= amount)
			{
				itemstack = stackList[slot];
				stackList[slot] = ItemStack.EMPTY;
				container.onCraftMatrixChanged(this);
				return itemstack;
			}
			else
			{
				itemstack = stackList[slot].splitStack(amount);

				if (stackList[slot].getCount() == 0)
				{
					stackList[slot] = ItemStack.EMPTY;
				}

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
		if (!stackList[slot].isEmpty())
		{
			ItemStack itemstack = stackList[slot];
			stackList[slot] = ItemStack.EMPTY;
			return itemstack;
		}
		else
		{
			return ItemStack.EMPTY;
		}
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		stackList[slot] = stack;
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
		for (int i = 0; i < stackList.length; i++)
		{
			stackList[i] = ItemStack.EMPTY;
		}
	}
}
