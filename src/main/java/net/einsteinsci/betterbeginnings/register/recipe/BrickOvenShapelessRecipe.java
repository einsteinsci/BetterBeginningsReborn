package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.item.ItemStack;
import java.util.*;

import com.google.common.collect.Lists;

public class BrickOvenShapelessRecipe implements IBrickOvenRecipe
{
	/**
	 * Is a List of ItemStack that composes the recipe.
	 */
	public final RecipeElement[] recipeItems;
	/**
	 * Is the ItemStack that you get when craft the recipe.
	 */
	private final ItemStack recipeOutput;

	public BrickOvenShapelessRecipe(ItemStack output, RecipeElement[] input)
	{
		recipeOutput = output;
		recipeItems = input;
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	@Override
	public boolean matches(TileEntityOvenBase oven)
	{
		ArrayList<RecipeElement> arraylist = Lists.newArrayList(recipeItems);

		for (int col = 0; col < 3; ++col)
		{
			for (int row = 0; row < 3; ++row)
			{
				ItemStack itemstack = oven.getStackInRowAndColumn(row, col);

				if (!itemstack.isEmpty())
				{
					boolean flag = false;
					Iterator<RecipeElement> iterator = arraylist.iterator();

					while (iterator.hasNext())
					{
						RecipeElement itemstack1 = (RecipeElement)iterator.next();

						if (itemstack1 != null && itemstack1.matches(itemstack))
						{
							flag = true;
							arraylist.remove(itemstack1);
							break;
						}
					}

					if (!flag)
					{
						return false;
					}
				}
			}
		}

		return arraylist.isEmpty();
	}

	/**
	 * Returns an Item that is the result of this recipe
	 */
	@Override
	public ItemStack getCraftingResult(TileEntityOvenBase oven)
	{
		return recipeOutput.copy();
	}

	/**
	 * Returns the size of the recipe area
	 */
	@Override
	public int getRecipeSize()
	{
		return recipeItems.length;
	}

	@Override
	public boolean contains(ItemStack stack)
	{
		for (RecipeElement ore : recipeItems)
		{
			if (ore.matches(stack))
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

	@Override
	public RecipeElement[] getInputs()
	{
		return recipeItems;
	}

	@Override
	public int getRecipeWidth() 
	{
		return 0;
	}

	@Override
	public int getRecipeHeight() 
	{
		return 0;
	}
}
