package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.minecraft.item.ItemStack;

// Needed for looking at the inventory of a NBE instead of a normal Brick Oven
public class NetherBrickOvenRecipeHandler extends BrickOvenRecipeHandler
{
	private static final NetherBrickOvenRecipeHandler INSTANCE = new NetherBrickOvenRecipeHandler();

	public NetherBrickOvenRecipeHandler()
	{
		super();
	}

	public static NetherBrickOvenRecipeHandler instance()
	{
		return INSTANCE;
	}

	public ItemStack findMatchingRecipe(TileEntityNetherBrickOven oven)
	{
		for (IBrickOvenRecipe recipe : BrickOvenRecipeHandler.instance().recipes)
		{
			if (recipe.matches(oven))
			{
				return recipe.getCraftingResult(oven);
			}
		}

		return ItemStack.EMPTY;
	}
}
