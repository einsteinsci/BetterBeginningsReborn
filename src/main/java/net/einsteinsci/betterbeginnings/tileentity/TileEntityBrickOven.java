package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockBrickOven;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerOven;
import net.einsteinsci.betterbeginnings.items.ItemBonePickaxe;
import net.einsteinsci.betterbeginnings.items.ItemFlintHatchet;
import net.einsteinsci.betterbeginnings.items.ItemKnifeFlint;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityBrickOven extends TileEntityOvenBase {
	public TileEntityBrickOven() {
		super(new ItemHandlerOven(11, false));
		processTime = 120;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		currentItemBurnLength = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(FUEL));
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.brickoven";
	}

	@Override
	public ItemStack findMatchingRecipe() {
		return BrickOvenRecipeHandler.instance().findMatchingRecipe(this);
	}

	@Override
	public void updateBlockState() {
		BlockBrickOven.updateBlockState(burnTime > 0, world, pos);
	}

	@Override
	protected int calculateFuelBurnTime(ItemStack fuelStack) {
		int burnVal = -1; // -1 = use default [if i got that right?]

		if (!fuelStack.isEmpty() && fuelStack.isItemEqual(inventory.getStackInSlot(FUEL))) {
			Item item = fuelStack.getItem();

			// It may be "wood", but it is not fuel.
			if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe || item instanceof ItemFlintHatchet) {
				burnVal = 0;
			} else if (item instanceof ItemTool) {
				if (((ItemTool) item).getToolMaterialName().equals("WOOD")
						|| ((ItemTool) item).getToolMaterialName().equals("noobwood")) {
					burnVal = 200;
				}
			} else if (item instanceof ItemSword) {
				if (((ItemSword) item).getToolMaterialName().equals("WOOD")
						|| ((ItemSword) item).getToolMaterialName().equals("noobwood")) {
					burnVal = 200;
				}
			} else if (item instanceof ItemHoe) {
				if (((ItemHoe) item).getMaterialName().equals("WOOD")
						|| ((ItemHoe) item).getMaterialName().equals("noobwood")) {
					burnVal = 200;
				}
			} else if (item == Items.BLAZE_ROD || item == Items.LAVA_BUCKET)
				burnVal = 0;
		}
		return burnVal;
	}
}