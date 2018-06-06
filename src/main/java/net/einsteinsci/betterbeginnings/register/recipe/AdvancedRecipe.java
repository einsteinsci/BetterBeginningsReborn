package net.einsteinsci.betterbeginnings.register.recipe;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.inventory.InventoryWorkbenchAdditionalMaterials;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class AdvancedRecipe
{
	/**
	 * How many horizontal slots this recipe is wide.
	 */
	public final int recipeWidth;
	/**
	 * How many vertical slots this recipe uses.
	 */
	public final int recipeHeight;
	/**
	 * Is a array of ItemStack that composes the recipe.
	 */
	public final RecipeElement[] recipeItems;
	/**
	 * Is the ItemStack that you get when craft the recipe.
	 */
	private ItemStack recipeOutput;
	/**
	 * Whether the recipe should be valid when mirrored
	 */
	protected boolean mirrored = true;
	
	public final boolean hideFromNEI;

	

	// additional materials in the side slots
	private RecipeElement[] addedMaterials;

	// ...something...
	private boolean strangeFlag;

	public AdvancedRecipe(int width, int height, RecipeElement[] items, ItemStack output,
	                      RecipeElement[] materials, boolean hide)
	{
		recipeWidth = width;
		recipeHeight = height;
		recipeItems = items;
		recipeOutput = output;
		addedMaterials = materials;
		hideFromNEI = hide;
	}

	// @Override
	public boolean matches(InventoryCrafting invCrafting, InventoryWorkbenchAdditionalMaterials materials,
	                       World world)
	{
		for (int i = 0; i <= 3 - recipeWidth; ++i)
		{
			for (int j = 0; j <= 3 - recipeHeight; ++j)
			{
				if (checkMatch(invCrafting, materials, i, j, false))
				{
					return true;
				}

				if (mirrored && checkMatch(invCrafting, materials, i, j, true))
				{
					return true;
				}
			}
		}

		return false;
	}

	// Not sure what that fourth flag is...(it's mirroring)
	private boolean checkMatch(InventoryCrafting crafting, InventoryWorkbenchAdditionalMaterials materials, int startX,
	                           int startY, boolean mirror)
	{
		for (int k = 0; k < 3; ++k)
		{
			for (int l = 0; l < 3; ++l)
			{
				int i1 = k - startX;
				int j1 = l - startY;
				RecipeElement neededCraftingStack = null;

				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
				{
					if (mirror)
					{
						neededCraftingStack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					}
					else
					{
						neededCraftingStack = recipeItems[i1 + j1 * recipeWidth];
					}
				}

				ItemStack craftingStackInQuestion = crafting.getStackInRowAndColumn(k, l);

				if (!craftingStackInQuestion.isEmpty() && neededCraftingStack != null)
				{
					if (!neededCraftingStack.matches(craftingStackInQuestion))
					{
						return false;
					}
				}
				else if ((craftingStackInQuestion == null) ^ (neededCraftingStack == null))
				{
					return false;
				}
			}
		}

		if(addedMaterials.length > materials.getSizeInventory())
		{
			LogUtil.log(Level.ERROR, "Recipe for " + this.recipeOutput.getDisplayName() + " has too many catalysts required.");
			return false;
		}
		
		for (RecipeElement requiredMatStack : addedMaterials)
		{
			boolean foundIt = false;
			for (int i2 = 0; i2 < materials.getSizeInventory(); ++i2)
			{
				ItemStack testedMatStack = materials.getStackInSlot(i2);
				if (!testedMatStack.isEmpty())
				{
					foundIt = requiredMatStack.matchesCheckSize(testedMatStack);
				}

				if (foundIt)
				{
					break;
				}
			}

			if (!foundIt)
			{
				return false;
			}
		}

		return true;
	}

	public boolean matchesMostly(InventoryCrafting invCrafting, World world)
	{
		for (int i = 0; i <= 3 - recipeWidth; ++i)
		{
			for (int j = 0; j <= 3 - recipeHeight; ++j)
			{
				if (checkMatchMostly(invCrafting, i, j, false))
				{
					return true;
				}

				if (mirrored && checkMatchMostly(invCrafting, i, j, true))
				{
					return true;
				}
			}
		}

		return false;
	}

	public boolean checkMatchMostly(InventoryCrafting crafting, int width, int height, boolean flag4)
	{
		for (int k = 0; k < 3; ++k)
		{
			for (int l = 0; l < 3; ++l)
			{
				int i1 = k - width;
				int j1 = l - height;
				RecipeElement neededCraftingStack = null;

				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
				{
					if (flag4)
					{
						neededCraftingStack = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					}
					else
					{
						neededCraftingStack = recipeItems[i1 + j1 * recipeWidth];
					}
				}

				ItemStack craftingStackInQuestion = crafting.getStackInRowAndColumn(k, l);

				if (!craftingStackInQuestion.isEmpty() && neededCraftingStack != null)
				{
					if (!neededCraftingStack.matches(craftingStackInQuestion))
					{
						return false;
					}
				}
				else if ((craftingStackInQuestion == null) ^ (neededCraftingStack == null))
				{
					return false;
				}

			}
		}

		return true;
	}

	public int getNeededMaterialCount(ItemStack material)
	{
		for (RecipeElement stack : addedMaterials)
		{
			if (stack.matches(material))
			{
				return stack.getStackSize();
			}
		}

		return 0;
	}

	public RecipeElement[] getNeededMaterials()
	{
		return addedMaterials;
	}

	public ItemStack getCraftingResult(InventoryCrafting crafting)
	{
		ItemStack itemstack = getRecipeOutput().copy();

		if (strangeFlag)
		{
			for (int i = 0; i < crafting.getSizeInventory(); ++i)
			{
				ItemStack itemstack1 = crafting.getStackInSlot(i);

				if (!itemstack1.isEmpty() && itemstack1.hasTagCompound())
				{
					itemstack.setTagCompound((NBTTagCompound)itemstack1.getTagCompound().copy());
				}
			}
		}

		return itemstack;
	}

	public ItemStack getRecipeOutput()
	{
		return recipeOutput;
	}

	public int getRecipeSize()
	{
		return recipeWidth * recipeHeight;
	}

	public AdvancedRecipe func_92100_c()
	{
		strangeFlag = true;
		return this;
	}

	@Override
	public String toString()
	{
		return "Crafts " + recipeOutput.toString() + " with " + addedMaterials.length + " catalysts";
	}

	public boolean hasMaterial(ItemStack stack)
	{
		for (RecipeElement s : addedMaterials)
		{
			if (s.matches(stack))
			{
				return true;
			}
		}
		return false;
	}
}
