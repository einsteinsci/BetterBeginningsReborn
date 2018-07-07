package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.blocks.BlockDoubleWorkbench;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ContainerSimpleWorkbench extends Container
{
	/**
	 * The crafting matrix inventory (3x3).
	 */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
	public IInventory craftResult = new InventoryCraftResult();
	BlockPos pos;
	private World worldObj;

	public ContainerSimpleWorkbench(InventoryPlayer invPlayer, World world, BlockPos loc)
	{
		worldObj = world;
		pos = loc;

		addSlotToContainer(new SlotCrafting(invPlayer.player, craftMatrix, craftResult, 0, 124, 35));
		int l;
		int i1;

		for (l = 0; l < 3; ++l)
		{
			for (i1 = 0; i1 < 3; ++i1)
			{
				addSlotToContainer(new Slot(craftMatrix, i1 + l * 3, 30 + i1 * 18, 17 + l * 18));
			}
		}

		for (l = 0; l < 3; ++l)
		{
			for (i1 = 0; i1 < 9; ++i1)
			{
				addSlotToContainer(new Slot(invPlayer, i1 + l * 9 + 9, 8 + i1 * 18, 84 + l * 18));
			}
		}

		for (l = 0; l < 9; ++l)
		{
			addSlotToContainer(new Slot(invPlayer, l, 8 + l * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	public boolean needsBigBoyBench()
	{
		return AdvancedCraftingHandler.crafting().hasRecipe(craftMatrix, worldObj);

	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that. I am not touching this code.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotId)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot)inventorySlots.get(slotId);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotId == 0)
			{
				if (!mergeItemStack(itemstack1, 10, 46, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotId >= 10 && slotId < 37)
			{
				if (!mergeItemStack(itemstack1, 37, 46, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (slotId >= 37 && slotId < 46)
			{
				if (!mergeItemStack(itemstack1, 10, 37, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!mergeItemStack(itemstack1, 10, 46, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}

			slot.onTake(player, itemstack1);
		}

		return itemstack;
	}

	// Can Stack Enter? (I think)
	@Override
	public boolean canMergeSlot(ItemStack stack, Slot slot)
	{
		return slot.inventory != craftResult && super.canMergeSlot(stack, slot);
	}

	/**
	 * Called when the container is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);

		if (!worldObj.isRemote)
		{
			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = craftMatrix.removeStackFromSlot(i);

				if (!itemstack.isEmpty())
				{
					player.dropItem(itemstack, false);
				}
			}
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory inventory)
	{
		IRecipe recipe = CraftingManager.findMatchingRecipe(craftMatrix, worldObj);
		if (recipe != null)
			craftResult.setInventorySlotContents(0, recipe.getCraftingResult(craftMatrix));
		else
			craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		// return worldObj.getBlock(posX, posY, posZ) !=
		// RegisterBlocks.blockDoubleWorkbench ? false : player.getDistanceSq(
		// posX + 0.5D, posY + 0.5D, posZ + 0.5D) <= 64.0D;

		return worldObj.getBlockState(pos).getBlock() == RegisterBlocks.doubleWorkbench
				&& worldObj.getBlockState(pos).getValue(BlockDoubleWorkbench.CONNECTION) == EnumFacing.UP
				&& player.getDistanceSq((double)pos.getX() + 0.5d, (double)pos.getY() + 0.5d,
				                        (double)pos.getZ() + 0.5d) <= 64.0D;
	}

}
