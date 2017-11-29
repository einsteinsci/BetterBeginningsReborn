/*package net.einsteinsci.betterbeginnings.jei.wrappers;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenShapelessRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class ShapelessOvenRecipeWrapper extends BlankRecipeWrapper implements IOvenRecipeWrapper 
{
	BrickOvenShapelessRecipe recipe;
	
	public ShapelessOvenRecipeWrapper(BrickOvenShapelessRecipe recipe) 
	{
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		List<List<ItemStack>> recipeInputs = Lists.newArrayList();
		for(RecipeElement element : recipe.recipeItems)
		{
			recipeInputs.add(element.getValidItems());
		}
		ingredients.setInputLists(ItemStack.class, recipeInputs);
		ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
	}
}*/
