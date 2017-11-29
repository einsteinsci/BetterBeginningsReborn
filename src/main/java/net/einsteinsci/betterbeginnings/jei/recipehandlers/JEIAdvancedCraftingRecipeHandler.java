/*package net.einsteinsci.betterbeginnings.jei.recipehandlers;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.categories.CategoryUIDs;
import net.einsteinsci.betterbeginnings.jei.wrappers.AdvancedRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;

public class JEIAdvancedCraftingRecipeHandler implements IRecipeHandler<AdvancedRecipe> 
{
	@Override
	public Class<AdvancedRecipe> getRecipeClass() 
	{
		return AdvancedRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid(AdvancedRecipe recipe) 
	{
		return getRecipeCategoryUid();
	}
	
	@Override
	public String getRecipeCategoryUid() 
	{
		return CategoryUIDs.ADV_CRAFTING;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(AdvancedRecipe recipe) 
	{
		return new AdvancedRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(AdvancedRecipe recipe)
	{
		return true;
	}
}*/
