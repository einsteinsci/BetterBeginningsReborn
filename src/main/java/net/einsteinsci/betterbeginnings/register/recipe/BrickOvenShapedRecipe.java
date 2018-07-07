package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class BrickOvenShapedRecipe implements IBrickOvenRecipe
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
	private boolean strangeField;

	public BrickOvenShapedRecipe(int width, int height, RecipeElement[] input, ItemStack output)
	{
		recipeWidth = width;
		recipeHeight = height;
		recipeItems = input;
		recipeOutput = output;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(TileEntityOvenBase oven)
	{
		for (int i = 0; i <= 3 - recipeWidth; ++i)
		{
			for (int j = 0; j <= 3 - recipeHeight; ++j)
			{
				if (checkMatch(oven, i, j, true))
				{
					return true;
				}

				if (checkMatch(oven, i, j, false))
				{
					return true;
				}
			}
		}

		return false;
	}

	private boolean checkMatch(TileEntityOvenBase oven, int width, int height, boolean flag4)
	{
		for (int k = 0; k < 3; ++k)
		{
			for (int l = 0; l < 3; ++l)
			{
				int i1 = k - width;
				int j1 = l - height;
				RecipeElement recipeIngredient = null;

				if (i1 >= 0 && j1 >= 0 && i1 < recipeWidth && j1 < recipeHeight)
				{
					if (flag4)
					{
						recipeIngredient = recipeItems[recipeWidth - i1 - 1 + j1 * recipeWidth];
					}
					else
					{
						recipeIngredient = recipeItems[i1 + j1 * recipeWidth];
					}
				}

				ItemStack itemstack1 = oven.getStackInRowAndColumn(k, l);

				if (!itemstack1.isEmpty() || recipeIngredient != null)
				{
					if (itemstack1.isEmpty() && recipeIngredient != null || !itemstack1.isEmpty() && recipeIngredient == null)
					{
						return false;
					}

					if(!recipeIngredient.matches(itemstack1))
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(TileEntityOvenBase oven)
	{
		ItemStack itemstack = getRecipeOutput().copy();
		IItemHandler itemHandler = CapUtils.getItemHandler(oven);
		
		if (strangeField)
		{
			for (int i = 0; i < itemHandler.getSlots(); ++i)
			{
				ItemStack itemstack1 = itemHandler.getStackInSlot(i);

				if (!itemstack1.isEmpty() && itemstack1.hasTagCompound())
				{
					itemstack.setTagCompound((NBTTagCompound)itemstack1.getTagCompound().copy());
				}
			}
		}

		return itemstack;
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize()
	{
		return recipeWidth * recipeHeight;
	}

	@Override
	public boolean contains(ItemStack stack)
	{
		for (RecipeElement ore : recipeItems)
		{
			if (ore == null)
			{
				continue;
			}

			if(ore.matches(stack))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return recipeOutput;
	}

	/*public BrickOvenShapedRecipe func_92100_c()
	{
		strangeField = true;
		return this;
	}*/

	@Override
	public RecipeElement[] getInputs()
	{
		List<RecipeElement> buf = new ArrayList<>();
		for (RecipeElement ore : recipeItems)
		{
			if (ore != null)
			{
				buf.add(ore);
			}
		}

		return buf.toArray(new RecipeElement[0]);
	}

	@Override
	public int getRecipeWidth() 
	{
		return recipeWidth;
    	}

	@Override
	public int getRecipeHeight() 
	{
		return recipeHeight;
	}
	public RecipeElement[] getThreeByThree()
	{
		RecipeElement[] res = new RecipeElement[9];

		int y = 0, x = 0;
		int v = 0, u = 0;
		for (int i = 0; i < getRecipeSize(); i++)
		{
			if(recipeItems[u + v * recipeWidth] == null)
			{
				res[x + y * 3] = null;
			}
			else
			{
				res[x + y * 3] = recipeItems[u + v * recipeWidth];
			}
			u++;
			if (u >= recipeWidth)
			{
				u = 0;
				v++;
			}

			x = u;
			y = v;
		}
		return res;
	}
}
