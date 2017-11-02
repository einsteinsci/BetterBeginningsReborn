package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.inventory.slots.SlotConfigurableAccess;
import net.einsteinsci.betterbeginnings.inventory.slots.SlotOutput;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

public class ContainerNetherBrickOven extends ContainerSpecializedFurnace<TileEntityNetherBrickOven>
{
	public static final int FUEL_LEVEL_ID = 3;
	
	protected int lastFuelLevel;
	
	protected TileEntityNetherBrickOven tileEntityNetherBrickOven;
	
	public ContainerNetherBrickOven(EntityPlayer player, TileEntityNetherBrickOven tileEntityBrickOven)
	{
		super(tileEntityBrickOven);
		PlayerInvWrapper playerInv = (PlayerInvWrapper) player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		tileEntityNetherBrickOven = tileEntityBrickOven;
		addSlotToContainer(new SlotConfigurableAccess((IItemHandlerModifiable) itemHandler, TileEntityNetherBrickOven.FUEL, 17, 63));
		addSlotToContainer(new SlotOutput(itemHandler, TileEntityOvenBase.OUTPUT, 138, 35));

		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 3; j++)
			{
				addSlotToContainer(new SlotItemHandler(itemHandler, j + i * 3 + TileEntityNetherBrickOven.INPUTSTART,
				                            44 + j * 18, 17 + i * 18));
			}
		}

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
		
		listener.sendProgressBarUpdate(this, FUEL_LEVEL_ID, tileEntity.currentItemBurnLength);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		
		for (IContainerListener listener : listeners)
		{	
			if (lastFuelLevel != tileEntityNetherBrickOven.getFuelLevel())
			{
				listener.sendProgressBarUpdate(this, FUEL_LEVEL_ID, tileEntityNetherBrickOven.getFuelLevel());
			}
		}
		
		lastFuelLevel = tileEntityNetherBrickOven.getFuelLevel();
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int progressBar, int level)
	{
		super.updateProgressBar(progressBar, level);
		
		if (progressBar == FUEL_LEVEL_ID)
		{
			tileEntityNetherBrickOven.setFuelLevel(level);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotId == TileEntityNetherBrickOven.OUTPUT)
			{
				if (!mergeItemStack(itemstack1, 11, 47, true)) // move to inventory (all, avoid hotbar)
				{
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotId >= 11 && slotId < 38) // non-hotbar inventory
			{
				if (itemstack1.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
				{
					if (!mergeItemStack(itemstack1, TileEntityNetherBrickOven.FUEL,
					                    TileEntityNetherBrickOven.FUEL + 1, false))
					{
						return null;
					}
				}
				else if (BrickOvenRecipeHandler.instance().isInRecipe(itemstack1))
				{
					if (!mergeItemStack(itemstack1, TileEntityNetherBrickOven.INPUTSTART,
					                    TileEntityNetherBrickOven.INPUTSTART + 9, false)) // move to craft matrix
					{
						return null;
					}
				}
				else if (!mergeItemStack(itemstack1, 38, 47, false)) // move to hotbar
				{
					return null;
				}
			}
			else if (slotId > 37 && slotId <= 46) // hotbar
			{
				if (itemstack1.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null))
				{
					if (!mergeItemStack(itemstack1, TileEntityNetherBrickOven.FUEL,
					                    TileEntityNetherBrickOven.FUEL + 1, false))
					{
						return null;
					}
				}
				else if (BrickOvenRecipeHandler.instance().isInRecipe(itemstack1))
				{
					if (!mergeItemStack(itemstack1, TileEntityNetherBrickOven.INPUTSTART,
					                    TileEntityNetherBrickOven.INPUTSTART + 9, false)) // move to craft matrix
					{
						return null;
					}
				}
				else if (!mergeItemStack(itemstack1, 11, 38, false)) // move to inventory (non-hotbar)
				{
					return null;
				}
			}
			else if (!mergeItemStack(itemstack1, 11, 47, false)) // move to inventory (all)
			{
				return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}

			slot.onPickupFromSlot(player, itemstack1);
		}

		return itemstack;
	}
}
