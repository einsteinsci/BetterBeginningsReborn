package net.einsteinsci.betterbeginnings.jei;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import net.einsteinsci.betterbeginnings.register.recipe.*;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class CampfireRecipeMaker 
{
	public static List<CampfireRecipeWrapper> getCampfireRecipes()
	{
		List<CampfireRecipeWrapper> recipes = Lists.newArrayList();
		for(Entry<RecipeElement, ItemStack> entry : CampfireRecipeHandler.getSmeltingList().entrySet())
		{
			recipes.add(new CampfireRecipeWrapper(entry.getKey(), entry.getValue(), CampfireRecipeHandler.instance().giveExperience(entry.getValue()), false));
		}
		for(Entry<RecipeElement, ItemStack> entry : CampfirePanRecipeHandler.getSmeltingList().entrySet())
		{
			recipes.add(new CampfireRecipeWrapper(entry.getKey(), entry.getValue(), CampfirePanRecipeHandler.instance().giveExperience(entry.getValue()), true));
		}
		return recipes;
	}
}
