package net.einsteinsci.betterbeginnings.inventory.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.SlotItemHandler;

//Allows access to the corresponding slot of the IItemHandler depending on the value of the booleans canExtract and canInsert.
public class SlotConfigurableAccess extends SlotItemHandler 
{
	boolean canExtract = true;
	boolean canInsert = true;
	
	public SlotConfigurableAccess(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		return canInsert;
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer playerIn) 
	{
		return canExtract;
	}
	
	public SlotConfigurableAccess setCanInsert(boolean canInsert) 
	{
		this.canInsert = canInsert;
		return this;
	}
	
	public SlotConfigurableAccess setCanExtract(boolean canExtract) 
	{
		this.canExtract = canExtract;
		return this;
	}
	
	@Override
	public ItemStack decrStackSize(int amount) 
	{
		if (!getStack().isEmpty())//STACKNULL
		{
			ItemStack stack;
			if (getStack().getCount() <= amount)
			{
				stack = getStack();
				((IItemHandlerModifiable)this.getItemHandler()).setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);//STACKNULL
				return stack;
			}
			else
			{
				stack = getStack().splitStack(amount);

				if (getStack().getCount() == 0)
				{
					((IItemHandlerModifiable)this.getItemHandler()).setStackInSlot(this.getSlotIndex(), ItemStack.EMPTY);//STACKNULL
				}

				return stack;
			}
		}
		else
		{
			return ItemStack.EMPTY;//STACKNULL
		}
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) 
	{
		ItemStack currentStack = getStack();
		int maxAdd = stack.getMaxStackSize();
		if(currentStack.isEmpty() ) return 64;
		if(!ItemHandlerHelper.canItemStacksStack(stack, currentStack))  return 64;
		int stackSize = currentStack.getCount() + stack.getCount();
		if(stackSize > maxAdd) stackSize = maxAdd;
		return stackSize;
	}
}
