package net.einsteinsci.betterbeginnings.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CapUtils 
{
	public static IFluidHandler getFluidHandler(ICapabilityProvider capProvider)
	{
		return getFluidHandler(capProvider, null);
	}
	
	public static IFluidHandler getFluidHandler(ICapabilityProvider capProvider, EnumFacing side)
	{
		return capProvider.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
	}

	public static boolean hasFluidHandler(ICapabilityProvider capProvider)
	{
		return hasFluidHandler(capProvider, null);
	}
	
	public static boolean hasFluidHandler(ICapabilityProvider capProvider, EnumFacing side)
	{
		return capProvider.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side);
	}
	
	public static IItemHandler getItemHandler(ICapabilityProvider capProvider)
	{
		return getItemHandler(capProvider, null);
	}
	
	public static IItemHandler getItemHandler(ICapabilityProvider capProvider, EnumFacing side)
	{
		return capProvider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}
	
	public static boolean hasItemHandler(ICapabilityProvider capProvider)
	{
		return hasItemHandler(capProvider, null);
	}
	
	public static boolean hasItemHandler(ICapabilityProvider capProvider, EnumFacing side)
	{
		return capProvider.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}
	
	public static void incrementStack(IItemHandlerModifiable handler, int slot, int amount)
	{
		ItemStack stack = handler.getStackInSlot(slot).copy();
		stack.stackSize += amount;
		handler.setStackInSlot(slot, stack);
	}
	
	public static void decrementStack(IItemHandlerModifiable handler, int slot, int amount)
	{
		ItemStack stack = handler.getStackInSlot(slot).copy();
		stack.stackSize -= amount;
		handler.setStackInSlot(slot, stack);
	}
}
