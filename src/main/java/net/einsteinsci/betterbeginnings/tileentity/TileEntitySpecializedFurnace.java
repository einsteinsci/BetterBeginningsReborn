package net.einsteinsci.betterbeginnings.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.RangedWrapper;

public abstract class TileEntitySpecializedFurnace extends TileEntitySidedInventory implements ITickable {
	public int burnTime;
	public int currentItemBurnLength;
	public int cookTime;
	protected int processTime;
	protected RangedWrapper inputHandler;
	protected RangedWrapper outputHandler;

	public TileEntitySpecializedFurnace(IItemHandlerModifiable mainHandlerIn, int inputMin, int inputMax, int outputMin,
			int outputMax) {
		super(mainHandlerIn);
		this.inputHandler = new RangedWrapper(mainHandlerIn, inputMin, inputMax + 1);
		this.outputHandler = new RangedWrapper(mainHandlerIn, outputMin, outputMax + 1);
	}

	// Client
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int progress) {
		return cookTime * progress / processTime;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int time) {
		if (currentItemBurnLength <= 0) {
			currentItemBurnLength = processTime;
		}

		return burnTime * time / currentItemBurnLength;
	}

	// TE related
	@Override
	public void markDirty() {
	}

	@Override
	protected IItemHandler getItemHandler(EnumFacing side) {
		if (side == null)
			return inventory;
		if (side == EnumFacing.DOWN)
			return outputHandler;
		else
			return inputHandler;
	}

	// NBT
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		burnTime = tagCompound.getShort("BurnTime");
		cookTime = tagCompound.getShort("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		// The Nether Brick oven doesn't use burnTime
		if (!(this instanceof TileEntityNetherBrickOven)) {
			tagCompound.setShort("BurnTime", (short) burnTime);
		}

		tagCompound.setShort("CookTime", (short) cookTime);
		return tagCompound;
	}

	public abstract void smeltItem();

	public abstract boolean canSmelt();

	public boolean isBurning() {
		return burnTime > 0;
	}
	
	@SubscribeEvent
	public void onFurnaceFuelTime(final FurnaceFuelBurnTimeEvent event) {
		event.setBurnTime(calculateFuelBurnTime(event.getItemStack()));
	}

	// Set -1 for default
	protected abstract int calculateFuelBurnTime(final ItemStack fuelStack);
}
