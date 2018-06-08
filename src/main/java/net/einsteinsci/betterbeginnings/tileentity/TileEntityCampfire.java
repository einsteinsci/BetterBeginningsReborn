package net.einsteinsci.betterbeginnings.tileentity;

import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.blocks.BlockCampfire;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerCampfire;
import net.einsteinsci.betterbeginnings.items.ICampfireUtensil;
import net.einsteinsci.betterbeginnings.network.PacketCampfireState;
import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.register.recipe.CampfirePanRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntityCampfire extends TileEntityBB implements ITickable, IWorldNameable {
	public static final int MAX_COOK_TIME_UNMODIFIED = 200;
	private static final int STACK_LIMIT = 64;
	private static final String INV_TAG = "Items";
	public static final int MAX_DECAY_TIME = 400; // 20 sec

	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 1;
	public static final int SLOT_FUEL = 2;
	public static final int SLOT_UTENSIL = 3;

	public float cookTime;
	public int burnTime;
	public int currentItemBurnTime;
	public int decayTime;

	public static final byte STATE_OFF = 0;
	public static final byte STATE_BURNING = 1;
	public static final byte STATE_DECAYING = 2;

	private IItemHandlerModifiable mainHandler;
	public byte campfireState;

	public TileEntityCampfire() {
		this.mainHandler = new ItemHandlerCampfire(4);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);

		// Inventory
		CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(
				CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainHandler, null,
				tagCompound.getTagList(INV_TAG, NBT.TAG_COMPOUND));

		// Burn Time & Cook Time
		burnTime = tagCompound.getShort("BurnTime");
		cookTime = tagCompound.getShort("CookTime");
		currentItemBurnTime = FuelRegistry.getBurnTime(FuelConsumerType.CAMPFIRE, stackFuel());
		decayTime = tagCompound.getShort("DecayTime");
		campfireState = tagCompound.getByte("CampfireState");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);

		tagCompound.setShort("BurnTime", (short) burnTime);
		tagCompound.setShort("CookTime", (short) cookTime);
		tagCompound.setShort("DecayTIme", (short) decayTime);
		tagCompound.setByte("CampfireState", campfireState);
		// Inventory
		tagCompound.setTag(INV_TAG, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage()
				.writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, mainHandler, null));
		return tagCompound;
	}

	public ItemStack stackFuel() {
		return mainHandler.getStackInSlot(SLOT_FUEL);
	}

	@Override
	public String getName() {
		return hasCustomName() ? customName : "container.campfire";
	}

	private static boolean isCampfireUtensil(ItemStack stack) {
		if (stack.isEmpty()) {
			return false;
		}

		return stack.getItem() instanceof ICampfireUtensil;
	}

	public static boolean isItemFuel(ItemStack itemstack1) {
		return FuelRegistry.getBurnTime(FuelConsumerType.CAMPFIRE, itemstack1) > 0;
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			boolean clean = false;

			boolean burning = processBurning();

			if (campfireState != STATE_OFF && canCook()) {
				boolean finished = processCooking();
				if (!finished) {
					clean = false;
				}
			} else {
				cookTime = 0;
			}

			BlockCampfire.updateBlockState(campfireState == STATE_BURNING, world, pos);

			if (!clean || burning != decayTime > 0) {
				markDirty();
			}

			NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.provider.getDimension(),
					pos.getX(), pos.getY(), pos.getZ(), 128.0d);
			ModMain.network.sendToAllAround(new PacketCampfireState(pos, campfireState), point);

			if (campfireState == STATE_BURNING) {
				// Light idiots on fire
				List<EntityLivingBase> idiots = world.getEntitiesWithinAABB(EntityLivingBase.class,
						new AxisAlignedBB(pos, pos.add(1, 1, 1)));
				for (Object obj : idiots) {
					if (obj instanceof EntityLivingBase) {
						EntityLivingBase idiot = (EntityLivingBase) obj;
						idiot.setFire(5);
					}
				}
			}
		}
	}

	// returns true if it's still burning
	private boolean processBurning() {
		boolean burning = burnTime > 0;
		if (burning) {
			burnTime--;
			decayTime = MAX_DECAY_TIME;
			campfireState = STATE_BURNING;
		} else {
			decayTime = Math.max(0, decayTime - 1);

			if (decayTime > 0) {
				campfireState = STATE_DECAYING;
			} else {
				campfireState = STATE_OFF;
			}
		}

		// only start fuel if lit (w/ F&S or Fire Bow)
		if (campfireState == STATE_DECAYING && canCook()) {
			burnTime = FuelRegistry.getBurnTime(FuelConsumerType.CAMPFIRE, stackFuel());
			currentItemBurnTime = burnTime;
			if (burnTime > 0) {
				burning = true;
				decayTime = MAX_DECAY_TIME;

				if (!stackFuel().isEmpty()) {
					stackFuel().shrink(1);

					if (stackFuel().getCount() == 0) {
						mainHandler.setStackInSlot(SLOT_FUEL, stackFuel().getItem().getContainerItem(stackFuel()));
					}
				}
			}
		}
		return burning;
	}

	// returns true if cooling finished
	private boolean processCooking() {
		float increase = 1.0f;
		if (isCampfireUtensil(stackUtensil())) {
			increase = ((ICampfireUtensil) stackUtensil().getItem()).getCampfireSpeedModifier();
		}
		cookTime += increase;

		if (cookTime >= MAX_COOK_TIME_UNMODIFIED) {
			cookItem(); // Tadaaa!
			cookTime = 0;
			return true;
		}
		return false;
	}

	public boolean canCook() {
		if (stackInput().isEmpty()) {
			return false;
		}

		ItemStack potentialResult = CampfirePanRecipeHandler.instance().getSmeltingResult(stackInput());
		if (potentialResult.isEmpty() || stackUtensil().isEmpty()) {
			potentialResult = CampfireRecipeHandler.instance().getSmeltingResult(stackInput());
		}

		if (potentialResult.isEmpty()) {
			return false; // instant no if there's no recipe
		}

		if (stackOutput().isEmpty()) {
			return true; // instant yes if output is open
		}
		if (!stackOutput().isItemEqual(potentialResult)) {
			return false; // instant no if output doesn't match recipe
		}

		int resultSize = stackOutput().getCount() + potentialResult.getCount();
		boolean canFit = resultSize <= STACK_LIMIT;
		boolean canFitWithItem = resultSize <= stackOutput().getMaxStackSize();
		return canFit && canFitWithItem;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(getName());
	}

	public boolean isDecaying() {
		return decayTime > 0;
	}

	private void cookItem() {
		if (canCook()) {
			ItemStack potentialResult = CampfirePanRecipeHandler.instance().getSmeltingResult(stackInput());
			if (potentialResult.isEmpty() || stackUtensil().isEmpty()) {
				potentialResult = CampfireRecipeHandler.instance().getSmeltingResult(stackInput());
			}

			if (stackOutput().isEmpty()) {
				mainHandler.setStackInSlot(SLOT_OUTPUT, potentialResult.copy());
			} else if (stackOutput().isItemEqual(potentialResult)) {
				CapUtils.incrementStack(mainHandler, SLOT_OUTPUT, potentialResult.getCount());
			}

			stackInput().shrink(1);

			if (stackInput().getCount() <= 0) {
				mainHandler.setStackInSlot(SLOT_INPUT, ItemStack.EMPTY);
			}

			if (!stackUtensil().isEmpty()) {
				if (stackUtensil().getItem() instanceof ICampfireUtensil) {
					ICampfireUtensil pan = (ICampfireUtensil) stackUtensil().getItem();
					boolean destroy = pan.doCookingDamage(stackUtensil());

					if (destroy) {
						mainHandler.setStackInSlot(SLOT_UTENSIL, ItemStack.EMPTY);
					}
				}
			}
		}
	}

	public ItemStack stackInput() {
		return mainHandler.getStackInSlot(SLOT_INPUT);
	}

	public ItemStack stackUtensil() {
		return mainHandler.getStackInSlot(SLOT_UTENSIL);
	}

	public ItemStack stackOutput() {
		return mainHandler.getStackInSlot(SLOT_OUTPUT);
	}

	public void lightFuel() {
		if (campfireState == STATE_BURNING) {
			return;
		}

		int maxBurn = FuelRegistry.getBurnTime(FuelConsumerType.CAMPFIRE, stackFuel());
		if (maxBurn > 0) {
			if (canCook()) {
				burnTime = maxBurn;
				decayTime = MAX_DECAY_TIME;
				campfireState = STATE_BURNING;

				// consume fuel
				currentItemBurnTime = burnTime;
				if (!stackFuel().isEmpty()) {
					stackFuel().shrink(1);

					if (stackFuel().getCount() == 0) {
						mainHandler.setStackInSlot(SLOT_FUEL, stackFuel().getItem().getContainerItem(stackFuel()));
					}
				}
			}
		}
	}

	public boolean isBurning() {
		return burnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int i) {
		return Math.round(cookTime * i / MAX_COOK_TIME_UNMODIFIED);
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int i) {
		if (currentItemBurnTime <= 0) {
			return 0;
		}

		return burnTime * i / currentItemBurnTime;
	}

	public int getDecayTimeRemainingScaled(int i) {
		return decayTime * i / MAX_DECAY_TIME;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
		return super.hasCapability(capability, facing); // Compat with attached capabilities
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(mainHandler);
		return super.getCapability(capability, facing);
	}
}
