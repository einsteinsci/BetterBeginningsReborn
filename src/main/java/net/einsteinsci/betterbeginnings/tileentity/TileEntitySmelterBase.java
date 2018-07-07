package net.einsteinsci.betterbeginnings.tileentity;

import java.util.*;

import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.oredict.OreDictionary;

public abstract class TileEntitySmelterBase extends TileEntitySpecializedFurnace
{
	public static final int INPUT = 0;
	public static final int FUEL = 1;
	public static final int BOOSTER = 2;
	public static final int OUTPUT = 3;

	public static Map<ItemStack, Float> boosterRegistry = new HashMap<>();

	public static final Random RANDOM = new Random();

	public static void registerBooster(ItemStack booster, float amount)
	{
		boosterRegistry.put(booster, amount);
	}

	public static void registerDefaultBoosters()
	{
		boosterRegistry.put(new ItemStack(Blocks.GRAVEL), 0.0f);
		boosterRegistry.put(new ItemStack(Blocks.SOUL_SAND), 0.3f);
		boosterRegistry.put(new ItemStack(Items.QUARTZ), 1.0f);
		boosterRegistry.put(new ItemStack(Items.PRISMARINE_SHARD), 1.5f);
		// BoosterConfig.registerBooster(new ItemStack(Items.CRUSHED_PURPUR), 1.3f); // 1.9+
	}
	
	public TileEntitySmelterBase(IItemHandlerModifiable mainHandlerIn)
	{
		super(mainHandlerIn, INPUT, BOOSTER, OUTPUT, OUTPUT);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
	{
		super.readFromNBT(tagCompound);
		currentItemBurnLength = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(FUEL));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
		return tagCompound;
	}

	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			boolean flag = burnTime > 0;
			boolean flag1 = false;

			if (burnTime > 0)
			{
				--burnTime;
			}

			if (burnTime == 0 && canSmelt())
			{
				currentItemBurnLength = burnTime = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(FUEL));

				if (burnTime > 0)
				{
					flag1 = true;
					if (!inventory.getStackInSlot(FUEL).isEmpty())
					{
						CapUtils.decrementStack(inventory, FUEL, 1);

						if (inventory.getStackInSlot(FUEL).getCount() == 0)
						{
							inventory.setStackInSlot(FUEL, ForgeHooks.getContainerItem(inventory.getStackInSlot(FUEL)));
						}
					}
				}
			}

			if (isBurning() && canSmelt())
			{
				++cookTime;
				if (cookTime == processTime)
				{
					cookTime = 0;
					smeltItem(); // TADA!!!
					flag1 = true;
				}
			}
			else
			{
				cookTime = 0;
			}

			if (flag != burnTime > 0)
			{
				flag1 = true;
				updateBlockState();
			}

			if (flag1)
			{
				markDirty();
			}
		}
	}

	@Override
	public boolean canSmelt()
	{
		if (inventory.getStackInSlot(INPUT).isEmpty() || inventory.getStackInSlot(BOOSTER).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack stack = SmelterRecipeHandler.instance().getSmeltingResult(inventory.getStackInSlot(INPUT));
			int boostersNeeded = SmelterRecipeHandler.instance().getBoosterCount(inventory.getStackInSlot(INPUT));
			if (stack.isEmpty())
			{
				return false;
			}

			if (boostersNeeded > inventory.getStackInSlot(BOOSTER).getCount())
			{
				return false;
			}

			if (inventory.getStackInSlot(OUTPUT).isEmpty())
			{
				return true;
			}
			if (!inventory.getStackInSlot(OUTPUT).isItemEqual(stack))
			{
				return false;
			}

			int resultCount = inventory.getStackInSlot(OUTPUT).getCount() + getMaxNextOutputCount();
			return resultCount <= inventory.getStackInSlot(OUTPUT).getMaxStackSize();
		}
	}

	@Override
	public void smeltItem()
	{
		if (canSmelt())
		{
			ItemStack result = SmelterRecipeHandler.instance().getSmeltingResult(inventory.getStackInSlot(INPUT));
			int outputCount = getNextOutputCount();
			
			if (inventory.getStackInSlot(OUTPUT).isEmpty())
			{
				ItemStack stack = result.copy();
				stack.setCount(outputCount);
				inventory.setStackInSlot(OUTPUT, stack);
			}
			else if (inventory.getStackInSlot(OUTPUT).getItem() == result.getItem())
			{
				CapUtils.incrementStack(inventory, OUTPUT, outputCount);
			}

			int gravelUsed = SmelterRecipeHandler.instance().getBoosterCount(inventory.getStackInSlot(INPUT));

			CapUtils.decrementStack(inventory, INPUT, 1);

			if (inventory.getStackInSlot(INPUT).getCount() <= 0)
			{
				inventory.setStackInSlot(INPUT, ItemStack.EMPTY);
			}
			CapUtils.decrementStack(inventory, BOOSTER, gravelUsed);

			if (inventory.getStackInSlot(BOOSTER).getCount() <= 0)
			{
				inventory.setStackInSlot(BOOSTER, ItemStack.EMPTY);
			}
		}
	}

	// used for space checking in canSmelt(), not actual smelting
	public final int getMaxNextOutputCount()
	{
		float boost = getTotalBoost();
		int ceiling = (int) Math.ceil(boost);
		return getOutputCountFromBoostLevel(ceiling);
	}

	public final int getNextOutputCount()
	{
		float boost = getTotalBoost();
		int floor = (int)boost;

		if (boost == (float)floor)
		{
			return getOutputCountFromBoostLevel(floor);
		}

		int ceiling = (int) Math.ceil(boost);
		float determiner = RANDOM.nextFloat() + floor; // width is always 1

		if (determiner > boost) // It's kind of reversed if you try to picture it.
		{
			return getOutputCountFromBoostLevel(floor);
		}
		else
		{
			return  getOutputCountFromBoostLevel(ceiling);
		}
	}

	public final float getTotalBoost()
	{
		float booster = getBoostFromBooster(inventory.getStackInSlot(BOOSTER));
		if (Float.isNaN(booster))
		{
			booster = 0;
		}

		return getBaseBoosterLevel() + booster;
	}

	public final int getMinOutputCount()
	{
		int boostFloor = (int)getTotalBoost();
		return getOutputCountFromBoostLevel(boostFloor);
	}

	public final int getMaxOutputCount()
	{
		int boostCeiling = (int) Math.ceil(getTotalBoost());
		return getOutputCountFromBoostLevel(boostCeiling);
	}

	public final int getOutputCountFromBoostLevel(int roundedBoost)
	{
		int countPerBoost = SmelterRecipeHandler.instance().getBonusPerBoost(inventory.getStackInSlot(INPUT));
		int countUnboosted = SmelterRecipeHandler.instance().getSmeltingResult(inventory.getStackInSlot(INPUT)).getCount();
		return countUnboosted + (roundedBoost - 1) * countPerBoost;
	}

	public abstract void updateBlockState();

	public abstract float getBaseBoosterLevel();

	public static float getBoostFromBooster(ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return Float.NaN;
		}

		for (Map.Entry<ItemStack, Float> entry : boosterRegistry.entrySet())
		{
			ItemStack tested = entry.getKey();
			if (tested.getMetadata() == OreDictionary.WILDCARD_VALUE)
			{
				if (tested.getItem() == stack.getItem())
				{
					return entry.getValue();
				}
			}
			else
			{
				if (tested.getItem() == stack.getItem() && tested.getMetadata() == stack.getMetadata())
				{
					return entry.getValue();
				}
			}
		}

		return Float.NaN;
	}

	public static boolean isBooster(ItemStack stack)
	{
		return !Float.isNaN(getBoostFromBooster(stack));
	}
}
