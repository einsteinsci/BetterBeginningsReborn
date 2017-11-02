package net.einsteinsci.betterbeginnings.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IWorldNameable;

public abstract class TileEntityBB extends TileEntity implements IWorldNameable
{
    protected String customName;

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
	NBTTagCompound tag = new NBTTagCompound();
	writeToNBT(tag);
	return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
    {
	readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() 
    {
	return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public boolean hasCustomName() 
    {
	return customName != null;
    }

    public void setBlockName(String displayName)
    {
	customName = displayName;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
	super.readFromNBT(tagCompound);
	if (tagCompound.hasKey("CustomName"))
	{
	    customName = tagCompound.getString("CustomName");
	}
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
	super.writeToNBT(tagCompound);
	if (hasCustomName())
	{
	    tagCompound.setString("CustomName", customName);
	}
	return tagCompound;
    }
}
