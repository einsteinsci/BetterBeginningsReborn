package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.*;
import java.util.Map.Entry;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class KilnRecipeHandler
{
	private static final KilnRecipeHandler INSTANCE = new KilnRecipeHandler();

	private List<KilnRecipe> RECIPES = new ArrayList<>();

	public static void addRecipe(KilnRecipe recipe)
	{
		instance().RECIPES.add(recipe);
	}

	public static KilnRecipeHandler instance()
	{
		return INSTANCE;
	}

	public static void addRecipe(RecipeElement input, ItemStack output, float experience)
	{
		addRecipe(new KilnRecipe(input, output, experience));
	}

	public static void addRecipe(String input, ItemStack output, float experience)
	{
		addRecipe(new KilnRecipe(new OreRecipeElement(input), output, experience));
	}

	public static void addRecipe(Block input, ItemStack output, float experience)
	{
		addRecipe(new StackRecipeElement(input, 1, OreDictionary.WILDCARD_VALUE), output, experience);
	}

	public static void addRecipe(Item input, ItemStack output, float experience)
	{
		addRecipe(new StackRecipeElement(input, 1, OreDictionary.WILDCARD_VALUE), output, experience);
	}

	public static void addRecipe(ItemStack input, ItemStack output, float experience)
	{
		addRecipe(new StackRecipeElement(input), output, experience);
	}

	public ItemStack getSmeltingResult(ItemStack stack)
	{
		Iterator<KilnRecipe> iterator = RECIPES.iterator();
		KilnRecipe recipe;

		do
		{
			if (!iterator.hasNext())
			{
				return ItemStack.EMPTY;
			}

			recipe = iterator.next();
		} while (!recipe.getInput().matches(stack));

		return recipe.getOutput();
	}

	public static List<KilnRecipe> getRecipes()
	{
		return instance().RECIPES;
	}

	private boolean canBeSmelted(ItemStack stack, ItemStack stack2)
	{
		return stack2.getItem() == stack.getItem()
				&& (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack
				.getItemDamage());
	}

	public boolean existsRecipeFrom(ItemStack input)
	{
		for (KilnRecipe recipe : RECIPES)
		{
			if (recipe.getInput().matches(input))
			{
				return true;
			}
		}
		return false;
	}

	public float giveExperience(ItemStack stack)
	{
		Iterator<KilnRecipe> iterator = RECIPES.iterator();
		KilnRecipe recipe;

		do
		{
			if (!iterator.hasNext())
			{
				return 0.0f;
			}

			recipe = iterator.next();
		} while (!recipe.getInput().matches(stack));

		if (stack.getItem().getSmeltingExperience(stack) != -1)
		{
			return stack.getItem().getSmeltingExperience(stack);
		}

		return recipe.getXP();
	}

	public static void removeRecipe(RecipeElement input, ItemStack output)
	{
	    for (KilnRecipe recipe : instance().RECIPES)
		    if (recipe.getInput().equals(input) && ItemStack.areItemStacksEqual(recipe.getOutput(), output))
		    {
			instance().RECIPES.remove((recipe));
			break;
		    }
	}
}
