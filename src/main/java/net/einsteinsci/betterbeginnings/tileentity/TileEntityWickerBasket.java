package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.inventory.ItemHandlerWickerBasket;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.IItemHandler;

public class TileEntityWickerBasket extends TileEntitySidedInventory
{
    public static final int INV_SIZE = 9;
    
    public TileEntityWickerBasket()
    {
	super(new ItemHandlerWickerBasket(INV_SIZE));
    }

    @Override
    protected IItemHandler getItemHandler(EnumFacing side)
    {
	return inventory;
    }

    @Override
    public String getName()
    {
	return hasCustomName() ? customName : "container.wicker_basket";
    }
}
