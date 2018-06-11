package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.item.ItemStack;

public interface IBrickOvenRecipe
{
	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	boolean matches(TileEntityOvenBase inv);

	/**
	 * Returns an Item that is the result of this recipe
	 */
	ItemStack getCraftingResult(TileEntityOvenBase inv);

	/**
	 * Returns the size of the recipe area
	 */
	int getRecipeSize();

	boolean contains(ItemStack stack);

	ItemStack getRecipeOutput();

	RecipeElement[] getInputs();

	int getRecipeWidth();
	int getRecipeHeight();
}
