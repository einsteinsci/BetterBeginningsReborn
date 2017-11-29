package net.einsteinsci.betterbeginnings.inventory.containers;

import net.einsteinsci.betterbeginnings.tileentity.TileEntitySpecializedFurnace;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ContainerSpecializedFurnace<T extends TileEntitySpecializedFurnace> extends ContainerInvTileEntity<T>
{
	protected int lastCookTime;
	protected int lastBurnTime;
	protected int lastItemBurnTime;

	public ContainerSpecializedFurnace(T tile) 
	{
		super(tile);
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);

		listener.sendWindowProperty(this, 0, tileEntity.cookTime);
		listener.sendWindowProperty(this, 1, tileEntity.burnTime);
		listener.sendWindowProperty(this, 2, tileEntity.currentItemBurnLength);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (IContainerListener listener : listeners)
		{
			if (lastCookTime != tileEntity.cookTime)
			{
				listener.sendWindowProperty(this, 0, tileEntity.cookTime);
			}
			if (lastBurnTime != tileEntity.burnTime)
			{
				listener.sendWindowProperty(this, 1, tileEntity.burnTime);
			}
			if (lastItemBurnTime != tileEntity.currentItemBurnLength)
			{
				listener.sendWindowProperty(this, 2, tileEntity.currentItemBurnLength);
			}
		}

		lastBurnTime = tileEntity.burnTime;
		lastCookTime = tileEntity.cookTime;
		lastItemBurnTime = tileEntity.currentItemBurnLength;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			tileEntity.cookTime = par2;
		}
		if (par1 == 1)
		{
			tileEntity.burnTime = par2;
		}
		if (par1 == 2)
		{
			tileEntity.currentItemBurnLength = par2;
		}
	}
}
