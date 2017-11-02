package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.blocks.BlockBrickOven;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerOven;
import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityBrickOven extends TileEntityOvenBase
{
	public TileEntityBrickOven()
	{
		super(new ItemHandlerOven(11));
		processTime = 120;
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		currentItemBurnLength = FuelRegistry.getBurnTime(FuelConsumerType.OVEN, mainHandler.getStackInSlot(FUEL));
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.brickoven";
	}

	@Override
	public ItemStack findMatchingRecipe()
	{
		return BrickOvenRecipeHandler.instance().findMatchingRecipe(this);
	}

	@Override
	public void updateBlockState()
	{
		BlockBrickOven.updateBlockState(burnTime > 0, world, pos);
	}
}