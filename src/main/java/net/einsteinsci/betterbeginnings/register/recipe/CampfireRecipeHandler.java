package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CampfireRecipeHandler
{
	private static final CampfireRecipeHandler INSTANCE = new CampfireRecipeHandler();
	protected List<CampfireRecipe> RECIPES = new ArrayList<>();


	public static void addRecipe(CampfireRecipe recipe)
	{
		instance().RECIPES.add(recipe);
	}

	public static CampfireRecipeHandler instance()
	{
		return INSTANCE;
	}
	
	public static void removeRecipe(RecipeElement input, ItemStack output, boolean requirePan)
	{
		for (CampfireRecipe recipe : instance().RECIPES)
			if (recipe.getInput().equals(input) && ItemStack.areItemStacksEqual(recipe.getOutput(), output) && recipe.requiresPan() == requirePan)
			{
				instance().RECIPES.remove(recipe);
				break;
			}
	}


	public static void addRecipe(RecipeElement input, ItemStack output, float experience, boolean requirePan)
	{
		addRecipe(new CampfireRecipe(input, output, experience, requirePan));
	}

	public static void addRecipe(String input, ItemStack output, float experience, boolean requirePan)
	{
		addRecipe(new CampfireRecipe(new OreRecipeElement(input), output, experience, requirePan));
	}
	
	public static void addRecipe(Block input, ItemStack output, float experience, boolean requirePan)
	{
		addRecipe(new CampfireRecipe(new StackRecipeElement(Item.getItemFromBlock(input)), output, experience, requirePan));
	}
	public static void addRecipe(ItemStack input, ItemStack output, float experience, boolean requirePan) 
	{
		addRecipe(new CampfireRecipe(new StackRecipeElement(input), output, experience, requirePan));
	}

	public static void addRecipe(Item input, ItemStack output, float experience, boolean requirePan)
	{
		addRecipe(new CampfireRecipe(new StackRecipeElement(input), output, experience, requirePan));
	}

	@Nullable
	public ItemStack getSmeltingResult(ItemStack stack, boolean hasPan)
	{
		for (CampfireRecipe recipe : RECIPES) 
		{
			if (recipe.equalByInput(stack, hasPan))
				return recipe.getOutput();
		}
		return ItemStack.EMPTY;
	}

	private boolean canBeSmelted(ItemStack stack, ItemStack stack2)
	{
		return stack2.getItem() == stack.getItem()
				&& (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack
				.getItemDamage());
	}

	public float giveExperience(ItemStack stack, boolean requirePan)
	{
		Iterator<CampfireRecipe> iterator = RECIPES.iterator();
		CampfireRecipe recipe;

		do
		{
			if (!iterator.hasNext())
			{
				return 0.0f;
			}

			recipe = iterator.next();
		} while (!recipe.equalByInput(stack, requirePan));

		if (stack.getItem().getSmeltingExperience(stack) != -1)
		{
			return stack.getItem().getSmeltingExperience(stack);
		}

		return recipe.getXp();
	}

	public static List<CampfireRecipe> getRecipes()
	{
		return instance().RECIPES;
	}
}
