package net.einsteinsci.betterbeginnings.inventory.slots;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOutput extends SlotItemHandler 
{	
	public SlotOutput(IItemHandler itemHandler, int index, int xPosition, int yPosition) 
	{
		super(itemHandler, index, xPosition, yPosition);
	}

	public boolean isItemValid(@Nullable ItemStack stack)
	{
		return false;
	}
}
