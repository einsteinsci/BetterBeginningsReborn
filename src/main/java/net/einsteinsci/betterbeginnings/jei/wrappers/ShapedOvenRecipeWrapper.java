/*package net.einsteinsci.betterbeginnings.jei.wrappers;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenShapedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class ShapedOvenRecipeWrapper extends BlankRecipeWrapper implements IOvenRecipeWrapper 
{
	BrickOvenShapedRecipe recipe;
	
	public ShapedOvenRecipeWrapper(BrickOvenShapedRecipe recipe) 
	{
		this.recipe = recipe;
	}
	
	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		List<List<ItemStack>> recipeInputs = Lists.newArrayList();
		for(RecipeElement element : recipe.getInputs())
		{
			recipeInputs.add(element.getValidItems());
		}
		ingredients.setInputLists(ItemStack.class, recipeInputs);
		ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
	}
	
	public int getWidth()
	{
		return recipe.recipeWidth;
	}
	
	public int getHeight()
	{
		return recipe.recipeHeight;
	}
}*/
