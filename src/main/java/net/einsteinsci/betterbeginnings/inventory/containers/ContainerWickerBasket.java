package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.inventory.slots.SlotConfigurableAccess;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

public class ContainerWickerBasket extends ContainerInvTileEntity<TileEntityWickerBasket>
{
    public ContainerWickerBasket(EntityPlayer player, TileEntityWickerBasket tileEntityIn)
    {
	super(tileEntityIn);
	PlayerInvWrapper playerInv = (PlayerInvWrapper) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

	//Wicker Basket Inventory
	for (int x = 0; x < 9; ++x)
	{
	    addSlotToContainer(new SlotConfigurableAccess(itemHandler, x, x * 18 + 8, 18).setCanExtract(true).setCanInsert(true));
	}

	//Player Inventory
	//TODO Replace with function
	int i;
	for (i = 0; i < 3; ++i)
	{
	    for (int j = 0; j < 9; ++j)
	    {
		addSlotToContainer(new SlotConfigurableAccess(playerInv, j + i * 9 + 9, 8 + j * 18, 50 + i * 18).setCanExtract(true).setCanInsert(true));
	    }
	}
	//Player Hotbar
	for (i = 0; i < 9; ++i)
	{
	    addSlotToContainer(new SlotConfigurableAccess(playerInv, i, 8 + i * 18, 108).setCanExtract(true).setCanInsert(true));
	}
    }



    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
	ItemStack newStack = ItemStack.EMPTY;
	Slot slot = (Slot)this.inventorySlots.get(index);

	if (slot != null && slot.getHasStack())
	{
	    ItemStack containedStack = slot.getStack();
	    newStack = containedStack.copy();

	    if (index < TileEntityWickerBasket.INV_SIZE)
	    {
		if (!this.mergeItemStack(containedStack, TileEntityWickerBasket.INV_SIZE, this.inventorySlots.size(), true))
		{
		    return ItemStack.EMPTY;
		}
	    }
	    else if (!this.mergeItemStack(containedStack, 0, TileEntityWickerBasket.INV_SIZE, false))
	    {
		return ItemStack.EMPTY;
	    }

	    if (containedStack.getCount() == 0)
	    {
		slot.putStack((ItemStack)null);
	    }
	    else
	    {
		slot.onSlotChanged();
	    }
	}

	return newStack;
    }

}
