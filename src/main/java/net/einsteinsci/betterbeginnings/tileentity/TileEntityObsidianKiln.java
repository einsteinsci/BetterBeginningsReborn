package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockObsidianKiln;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerKiln;
import net.einsteinsci.betterbeginnings.items.ItemBonePickaxe;
import net.einsteinsci.betterbeginnings.items.ItemFlintHatchet;
import net.einsteinsci.betterbeginnings.items.ItemKnifeFlint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileEntityObsidianKiln extends TileEntityKilnBase
{
	public TileEntityObsidianKiln()
	{
		super(new ItemHandlerKiln(3, true));
		processTime = 100;
	}
	
	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.obsidianKiln";
	}

	@Override
	public void updateBlockState()
	{
		BlockObsidianKiln.updateBlockState(burnTime > 0, world, pos);
	}

	@Override
	protected int calculateFuelBurnTime(ItemStack fuelStack) 
	{
		int burnTime = -1;

		if (!fuelStack.isEmpty() && fuelStack.isItemEqual(inventory.getStackInSlot(SLOT_FUEL))) 
		{
			Item item = fuelStack.getItem();

			// It may be "wood", but it is not fuel.
			if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe || item instanceof ItemFlintHatchet)
				burnTime = 0;
			else if (item == Items.BLAZE_ROD)
				burnTime = 1600;
			else if (item == Items.LAVA_BUCKET)
				burnTime = 80000;
		}
		return burnTime;
	}
}

