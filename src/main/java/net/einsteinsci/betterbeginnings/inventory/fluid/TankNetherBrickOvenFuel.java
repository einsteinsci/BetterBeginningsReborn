package net.einsteinsci.betterbeginnings.inventory.fluid;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TankNetherBrickOvenFuel extends FluidTank
{
	public TankNetherBrickOvenFuel(TileEntityNetherBrickOven oven, int capacity)
	{
		super(capacity);

		tile = oven;
	}

	public boolean fillFromContainer(ItemStack container)
	{
		if(!container.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null)) return false;
		IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int maxAmount = this.fluid != null ? this.capacity - this.fluid.amount : this.capacity;
		return FluidUtil.tryEmptyContainerAndStow(container, fluidHandler, itemHandler, maxAmount, null);
	}

	@Override
	public int getFluidAmount()
	{
		if (fluid == null)
		{
			return 0;
		}
		return fluid.amount;
	}
	
	@Override
	public boolean canFillFluidType(FluidStack fluid) 
	{
		return fluid.getFluid() == FluidRegistry.LAVA;
	}
}
