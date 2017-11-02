package net.einsteinsci.betterbeginnings.jei.recipehandlers;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.categories.CategoryUIDs;
import net.einsteinsci.betterbeginnings.jei.wrappers.ShapedOvenRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.wrappers.ShapelessOvenRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.*;

public class JEIOvenRecipeHandler implements IRecipeHandler<IBrickOvenRecipe> 
{
	@Override
	public Class<IBrickOvenRecipe> getRecipeClass() 
	{
		return IBrickOvenRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(IBrickOvenRecipe recipe) 
	{
		return getRecipeCategoryUid();
	}
	
	@Override
	public String getRecipeCategoryUid() 
	{
		return CategoryUIDs.OVEN;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(IBrickOvenRecipe recipe) 
	{
		if(recipe instanceof BrickOvenShapedRecipe)
		    return new ShapedOvenRecipeWrapper((BrickOvenShapedRecipe) recipe);
		if(recipe instanceof BrickOvenShapelessRecipe)
		    return new ShapelessOvenRecipeWrapper((BrickOvenShapelessRecipe) recipe);
		return null;
	}

	@Override
	public boolean isRecipeValid(IBrickOvenRecipe recipe)
	{
		return true;
	}
}
