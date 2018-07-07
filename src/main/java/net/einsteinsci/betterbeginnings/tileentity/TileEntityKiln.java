package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockKiln;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerKiln;
import net.einsteinsci.betterbeginnings.items.ItemBonePickaxe;
import net.einsteinsci.betterbeginnings.items.ItemFlintHatchet;
import net.einsteinsci.betterbeginnings.items.ItemKnifeFlint;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileEntityKiln extends TileEntityKilnBase
{
	public TileEntityKiln()
	{
		super(new ItemHandlerKiln(3, false));
		processTime = 250;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.kiln";
	}

	@Override
	public void updateBlockState()
	{
		BlockKiln.updateBlockState(burnTime > 0, world, pos);
	}

	@Override
	protected int calculateFuelBurnTime(ItemStack fuelStack) 
	{
		// TODO can i even do that here? if no - RIP
		int burnTime = -1;

		if (!fuelStack.isEmpty() && fuelStack.isItemEqual(inventory.getStackInSlot(SLOT_FUEL))) 
		{
			Item item = fuelStack.getItem();

			// It may be "wood", but it is not fuel.
			if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe || item instanceof ItemFlintHatchet)
				burnTime = 0;
			else if (item == Items.BLAZE_ROD || item == Items.LAVA_BUCKET)
				burnTime = 0;
		}
		return burnTime;
	}
}
