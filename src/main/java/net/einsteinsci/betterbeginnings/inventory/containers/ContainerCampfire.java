package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.inventory.slots.SlotConfigurableAccess;
import net.einsteinsci.betterbeginnings.items.ItemPan;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

public class ContainerCampfire extends ContainerInvTileEntity<TileEntityCampfire>
{
	private static final int SLOT_INPUT = TileEntityCampfire.SLOT_INPUT;
	private static final int SLOT_OUTPUT = TileEntityCampfire.SLOT_OUTPUT;
	private static final int SLOT_FUEL = TileEntityCampfire.SLOT_FUEL;
	private static final int SLOT_PAN = TileEntityCampfire.SLOT_UTENSIL;
	public int lastItemBurnTime;
	public int lastCookTime;
	private int lastBurnTime;
	private int lastDecayTime;

	public ContainerCampfire(EntityPlayer player, TileEntityCampfire campfire)
	{
		super(campfire);
		PlayerInvWrapper playerInv = (PlayerInvWrapper) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		addSlotToContainer(new SlotConfigurableAccess(itemHandler, SLOT_INPUT, 58, 12));
		addSlotToContainer(new SlotConfigurableAccess(itemHandler, SLOT_OUTPUT, 118, 34).setCanInsert(false));
		addSlotToContainer(new SlotConfigurableAccess(itemHandler, SLOT_FUEL, 58, 57)
		{
			@Override
			public boolean isItemValid(ItemStack stack) 
			{
				return TileEntityCampfire.isItemFuel(stack);
			}
		});
		addSlotToContainer(new SlotConfigurableAccess(itemHandler, SLOT_PAN, 32, 35));

		int i;
		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlotToContainer(new SlotItemHandler(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			addSlotToContainer(new SlotItemHandler(playerInv, i, 8 + i * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);

		listener.sendProgressBarUpdate(this, 0, tileEntity.cookTime);
		listener.sendProgressBarUpdate(this, 1, tileEntity.burnTime);
		listener.sendProgressBarUpdate(this, 2, tileEntity.currentItemBurnTime);
		listener.sendProgressBarUpdate(this, 3, tileEntity.decayTime);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (IContainerListener listener : listeners)
		{
			if (lastCookTime != tileEntity.cookTime)
			{
				listener.sendProgressBarUpdate(this, 0, tileEntity.cookTime);
			}
			if (lastBurnTime != tileEntity.burnTime)
			{
				listener.sendProgressBarUpdate(this, 1, tileEntity.burnTime);
			}
			if (lastItemBurnTime != tileEntity.currentItemBurnTime)
			{
				listener.sendProgressBarUpdate(this, 2, tileEntity.currentItemBurnTime);
			}
			if (lastDecayTime != tileEntity.decayTime)
			{
				listener.sendProgressBarUpdate(this, 3, tileEntity.decayTime);
			}
		}

		lastBurnTime = tileEntity.burnTime;
		lastCookTime = tileEntity.cookTime;
		lastItemBurnTime = tileEntity.currentItemBurnTime;
		lastDecayTime = tileEntity.decayTime;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
	{
		ItemStack itemstackCopy = null;
		Slot slot = (Slot)inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack = slot.getStack();
			itemstackCopy = itemstack.copy();

			if (slotId == SLOT_OUTPUT)
			{
				if (!mergeItemStack(itemstack, SLOT_PAN + 1, 39, true))
				{
					return null;
				}
				slot.onSlotChange(itemstack, itemstackCopy);
			}
			else if (slotId != SLOT_INPUT && slotId != SLOT_FUEL && slotId != SLOT_PAN)
			{
				if (itemstack.getItem() instanceof ItemPan)
				{
					if (!mergeItemStack(itemstack, SLOT_PAN, SLOT_PAN + 1, false))
					{
						return null;
					}
				}
				else if (TileEntityCampfire.isItemFuel(itemstack))
				{
					if (!mergeItemStack(itemstack, SLOT_FUEL, SLOT_FUEL + 1, false))
					{
						return null;
					}
				}
				else if (CampfireRecipeHandler.instance().getSmeltingResult(itemstack) != null)
				{
					if (!mergeItemStack(itemstack, SLOT_INPUT, SLOT_INPUT + 1, false))
					{
						return null;
					}
				}
				else if (slotId > SLOT_OUTPUT && slotId < 30)
				{
					if (!mergeItemStack(itemstack, 30, 39, false))
					{
						return null;
					}
				}
				else if (slotId >= 30 && slotId < 39 &&
					!mergeItemStack(itemstack, SLOT_PAN + 1, 30, false))
				{
					return null;
				}
			}
			else if (!mergeItemStack(itemstack, SLOT_PAN + 1, 39, false))
			{
				return null;
			}

			if (itemstack.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}
			if (itemstack.stackSize == itemstackCopy.stackSize)
			{
				return null;
			}
			slot.onPickupFromSlot(player, itemstack);
		}
		return itemstackCopy;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int barId, int newValue)
	{
		if (barId == 0)
		{
			tileEntity.cookTime = newValue;
		}
		if (barId == 1)
		{
			tileEntity.burnTime = newValue;
		}
		if (barId == 2)
		{
			tileEntity.currentItemBurnTime = newValue;
		}
		if (barId == 3)
		{
			tileEntity.decayTime = newValue;
		}
	}
}
