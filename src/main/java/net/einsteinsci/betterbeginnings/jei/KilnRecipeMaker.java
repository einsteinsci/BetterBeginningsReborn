package net.einsteinsci.betterbeginnings.jei;

import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.Lists;

import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class KilnRecipeMaker 
{
	public static List<KilnRecipeWrapper> getKilnRecipes()
	{
		List<KilnRecipeWrapper> recipes = Lists.newArrayList();
		for(Entry<RecipeElement, ItemStack> entry : KilnRecipeHandler.getSmeltingList().entrySet())
		{
			recipes.add(new KilnRecipeWrapper(entry.getKey(), entry.getValue(), KilnRecipeHandler.instance().giveExperience(entry.getValue())));
		}
		return recipes;
	}
}
