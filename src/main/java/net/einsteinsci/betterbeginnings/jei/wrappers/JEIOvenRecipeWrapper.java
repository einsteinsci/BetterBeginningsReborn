package net.einsteinsci.betterbeginnings.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.IBrickOvenRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JEIOvenRecipeWrapper implements IRecipeWrapper
{
	IBrickOvenRecipe recipe;

	public JEIOvenRecipeWrapper(IBrickOvenRecipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> recipeInputs = new ArrayList<>();
		for (RecipeElement element : recipe.getInputs())
			recipeInputs.add(element.getValidItems());
		ingredients.setInputLists(ItemStack.class, recipeInputs);
		ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
	}

	public IBrickOvenRecipe getRecipe() { return this.recipe; }
}
