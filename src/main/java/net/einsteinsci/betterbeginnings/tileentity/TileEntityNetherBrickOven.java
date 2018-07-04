package net.einsteinsci.betterbeginnings.tileentity;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.blocks.BlockNetherBrickOven;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerOven;
import net.einsteinsci.betterbeginnings.inventory.fluid.TankNetherBrickOvenFuel;
import net.einsteinsci.betterbeginnings.network.PacketNetherBrickOvenFuelLevel;
import net.einsteinsci.betterbeginnings.register.recipe.NetherBrickOvenRecipeHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityNetherBrickOven extends TileEntityOvenBase {
	public static final float FUELFORLAVA = 15; // 960 mb per stack
	public static final int MINIMUMTEMPERATURE = 500;

	public TankNetherBrickOvenFuel fuelTank;

	public TileEntityNetherBrickOven() {
		super(new ItemHandlerOven(11, true));
		fuelTank = new TankNetherBrickOvenFuel(this, 8000);
		processTime = 80;
	}

	public FluidStack getFuelStack() {
		return fuelTank.getFluid();
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fuelTank);
		return super.getCapability(capability, facing);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		fuelTank = (TankNetherBrickOvenFuel) fuelTank.readFromNBT(tagCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		return fuelTank.writeToNBT(tagCompound);
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.netherbrickoven";
	}

	@Override
	public void update() {
		boolean couldSmelt = canSmelt();
		boolean dirty = false;

		if (!world.isRemote) {
			if (canSmelt()) {
				++cookTime;
				if (cookTime == processTime) {
					cookTime = 0;
					smeltItem();
					dirty = true;
				}
			} else {
				cookTime = 0;
			}
			updateBlockState();
			// STACKNULL
			if (!inventory.getStackInSlot(FUEL).isEmpty()) {
				if (fuelTank.fillFromContainer(inventory.getStackInSlot(FUEL))) {
					updateNetwork();
				}
			}
		}

		if (couldSmelt != canSmelt()) {
			dirty = true;
		}

		if (dirty) {
			markDirty();
		}
	}

	@Override
	public void updateNetwork() {
		NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(),
				pos.getY(), pos.getZ(), 16.0d);
		ModMain.network.sendToAllAround(new PacketNetherBrickOvenFuelLevel(pos, fuelTank.getFluid()), point);
	}

	@Override
	public void updateBlockState() {
		BlockNetherBrickOven.updateBlockState(canSmelt(), world, pos);
	}

	@Override
	public boolean canSmelt() {
		if (fuelTank.getFluidAmount() <= 0 || getFuelNeededForSmelt() > fuelTank.getFluidAmount()) {
			return false;
		}

		return super.canSmelt();
	}

	@Override
	public void smeltItem() {
		if (canSmelt()) {
			fuelTank.getFluid().amount -= getFuelNeededForSmelt();
		}

		super.smeltItem();

		updateNetwork();
	}

	@Override
	public ItemStack findMatchingRecipe() {
		return NetherBrickOvenRecipeHandler.instance().findMatchingRecipe(this);
	}

	public int getFuelNeededForSmelt() {
		if (fuelTank.getFluid() == null) {
			return 0;
		}

		float temperature = (float) fuelTank.getFluid().getFluid().getTemperature();

		// Math!
		float precise = FUELFORLAVA * FluidRegistry.LAVA.getTemperature() / temperature;

		int result = (int) precise;

		if (result <= 0) {
			result = 1; // No free smelting if you have blazing pyrotheum or something even hotter.
		}

		return result;
	}

	public static boolean isItemFuelContainer(ItemStack stack) {
		// STACKNULL
		if (stack.isEmpty())
			return false;
		return stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}

	public int getFuelLevelScaled(int maxLevel) {
		float levelAbs = getFuelLevel();
		float capacity = fuelTank.getCapacity();
		float level = levelAbs / capacity;
		float scaled = level * (float) maxLevel;

		return (int) scaled;
	}

	public int getFuelLevel() {
		return fuelTank.getFluidAmount();
	}

	// I think this will only be called on the client side
	public void setFuelLevel(FluidStack fluid) {
		fuelTank.setFluid(fluid);
	}

	public void setFuelLevel(int level) {
		if (level <= 0) {
			setFuelLevel(null);
		}

		FluidStack stack = fuelTank.getFluid();
		if (stack != null) {
			stack.amount = level;
			setFuelLevel(stack);
		}
	}

	@Override
	protected int calculateFuelBurnTime(ItemStack fuelStack) {
		// TODO Auto-generated method stub
		return -1;
	}
}
