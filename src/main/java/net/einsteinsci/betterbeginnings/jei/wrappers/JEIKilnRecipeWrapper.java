package net.einsteinsci.betterbeginnings.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class JEIKilnRecipeWrapper extends BlankRecipeWrapper 
{
	RecipeElement input; 
	ItemStack output;

	public JEIKilnRecipeWrapper(RecipeElement recipeElement, ItemStack output) 
	{
		this.input = recipeElement;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputs(ItemStack.class, input.getValidItems());
		ingredients.setOutput(ItemStack.class, output);
	}
}
