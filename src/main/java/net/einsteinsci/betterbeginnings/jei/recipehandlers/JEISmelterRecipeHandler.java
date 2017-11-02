package net.einsteinsci.betterbeginnings.jei.recipehandlers;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.categories.CategoryUIDs;
import net.einsteinsci.betterbeginnings.jei.wrappers.SmelterRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipe;

public class JEISmelterRecipeHandler implements IRecipeHandler<SmelterRecipe> 
{
	@Override
	public Class<SmelterRecipe> getRecipeClass() 
	{
		return SmelterRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(SmelterRecipe recipe) 
	{
		return getRecipeCategoryUid();
	}
	
	@Override
	public String getRecipeCategoryUid() 
	{
		return CategoryUIDs.SMELTER;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(SmelterRecipe recipe) 
	{
		return new SmelterRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(SmelterRecipe recipe)
	{
		return true;
	}
}
