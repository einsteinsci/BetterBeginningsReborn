package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.IItemHandler;

//Base class for containers that reference TEs with inventories
public abstract class ContainerInvTileEntity<T extends TileEntity>  extends Container
{
	protected T tileEntity;
	protected IItemHandler itemHandler;
	
	public ContainerInvTileEntity(T tileEntityIn) 
	{
		this.tileEntity = tileEntityIn;
		this.itemHandler = CapUtils.getItemHandler(tileEntity, null);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.getDistanceSq((double)tileEntity.getPos().getX() + 0.5d, (double)tileEntity.getPos().getY() + 0.5d, (double)tileEntity.getPos().getZ() + 0.5d) <= 64.0d;
	}
}
