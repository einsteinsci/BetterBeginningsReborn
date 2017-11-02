package net.einsteinsci.betterbeginnings.jei.recipehandlers;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.categories.CategoryUIDs;
import net.einsteinsci.betterbeginnings.jei.wrappers.JEICampfireRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeWrapper;

public class JEICampfireRecipeHandler implements IRecipeHandler<CampfireRecipeWrapper> 
{
	@Override
	public Class<CampfireRecipeWrapper> getRecipeClass() 
	{
		return CampfireRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(CampfireRecipeWrapper recipe) 
	{
		return getRecipeCategoryUid();
	}
	
	@Override
	public String getRecipeCategoryUid() 
	{
		return CategoryUIDs.CAMPFIRE;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CampfireRecipeWrapper recipe) 
	{
		return new JEICampfireRecipeWrapper(recipe.getInput(), recipe.getOutput(), recipe.requiresPan());
	}

	@Override
	public boolean isRecipeValid(CampfireRecipeWrapper recipe)
	{
		return true;
	}
}
