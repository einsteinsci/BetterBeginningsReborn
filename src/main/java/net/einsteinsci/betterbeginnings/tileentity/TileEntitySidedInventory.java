package net.einsteinsci.betterbeginnings.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.*;

public abstract class TileEntitySidedInventory extends TileEntityBB 
{
    private static final String INV_TAG = "Inventory";

    protected IItemHandlerModifiable inventory;

    public TileEntitySidedInventory(IItemHandlerModifiable mainHandlerIn) 
    {
	this.inventory = mainHandlerIn;
    }

    protected abstract IItemHandler getItemHandler(EnumFacing side);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
    {
	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
	    return getItemHandler(facing) != null;
	return super.hasCapability(capability, facing); //Compat with attached capabilities
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
    {
	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
	    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(getItemHandler(facing));
	return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
	super.readFromNBT(tagCompound);
	//Deserialise capabilities using the default storage impl
	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null, tagCompound.getTagList(INV_TAG, NBT.TAG_COMPOUND));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
	super.writeToNBT(tagCompound);
	//Serialise capabilities using the default storage impl
	tagCompound.setTag(INV_TAG, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventory, null));
	return tagCompound;
    }
}
