/*package net.einsteinsci.betterbeginnings.jei.recipehandlers;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.categories.CategoryUIDs;
import net.einsteinsci.betterbeginnings.jei.wrappers.JEIKilnRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeWrapper;

public class JEIKilnRecipeHandler implements IRecipeHandler<KilnRecipeWrapper> 
{
	@Override
	public Class<KilnRecipeWrapper> getRecipeClass() 
	{
		return KilnRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid(KilnRecipeWrapper recipe) 
	{
		return getRecipeCategoryUid();
	}
	
	@Override
	public String getRecipeCategoryUid() 
	{
		return CategoryUIDs.KILN;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(KilnRecipeWrapper recipe) 
	{
		return new JEIKilnRecipeWrapper(recipe.getInput(), recipe.getOutput());
	}

	@Override
	public boolean isRecipeValid(KilnRecipeWrapper recipe)
	{
		return true;
	}
}*/
