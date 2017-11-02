package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.*;
import java.util.Map.Entry;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class SmelterRecipeHandler
{
	private static final SmelterRecipeHandler INSTANCE = new SmelterRecipeHandler();

	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	private List<SmelterRecipe> recipes = new ArrayList<SmelterRecipe>();

	private SmelterRecipeHandler()
	{
		// nothing here
	}

	public static SmelterRecipe addRecipe(RecipeElement input, ItemStack output, float experience, int boosters, int bonus)
	{
		return instance().putLists(input, output, experience, boosters, bonus);
	}
	
	public static void addRecipe(Item input, ItemStack output, float experience, int boosters, int bonus)
	{
		instance().putLists(input, output, experience, boosters, bonus);
	}

	public void putLists(Item input, ItemStack output, float experience, int boosters, int bonus)
	{
		putLists(new StackRecipeElement(input, 1, OreDictionary.WILDCARD_VALUE), output, experience, boosters, bonus);
	}

	public static SmelterRecipeHandler instance()
	{
		return INSTANCE;
	}

	public SmelterRecipe putLists(RecipeElement input, ItemStack output, float experience, int boosters, int bonus)
	{
		experienceList.put(output, experience);

		SmelterRecipe recipe = new SmelterRecipe(output, input, experience, boosters, bonus);
		recipes.add(recipe);
		return recipe;
	}

	public static void addRecipe(String input, ItemStack output, float experience, int boosters, int bonus)
	{
			instance().putLists(new OreRecipeElement(input), output, experience, boosters, bonus);
	}
	
	public static void addRecipe(Block input, ItemStack output, float experience, int boosters, int bonus)
	{
		instance().putLists(Item.getItemFromBlock(input), output, experience, boosters, bonus);
	}

	public static void addRecipe(ItemStack input, ItemStack output, float experience, int boosters, int bonus)
	{
		instance().putLists(new StackRecipeElement(input), output, experience, boosters, bonus);
	}

	public ItemStack getSmeltingResult(ItemStack input)
	{
		for (SmelterRecipe recipe : recipes)
		{
			if (recipe.getInput().matches(input))
			{
				return recipe.getOutput();
			}
		}

		return null;
	}

	public int getBoosterCount(ItemStack stack)
	{
		for (SmelterRecipe recipe : recipes)
		{
			if (recipe.getInput() != null)
			{
				if (recipe.getInput().matches(stack))
				{
					return recipe.getBoosters();
				}
			}
		}

		return -1;
	}

	public float giveExperience(ItemStack stack)
	{
		Iterator<Entry<ItemStack, Float>> iterator = experienceList.entrySet().iterator();
		Entry<ItemStack, Float> entry;

		do
		{
			if (!iterator.hasNext())
			{
				return 0.0f;
			}

			entry = iterator.next();
		} while (!canBeSmelted(stack, (ItemStack)entry.getKey()));

		if (stack.getItem().getSmeltingExperience(stack) != -1)
		{
			return stack.getItem().getSmeltingExperience(stack);
		}

		return (Float)entry.getValue();
	}

	private boolean canBeSmelted(ItemStack stack, ItemStack stack2)
	{
		return stack2.getItem() == stack.getItem() &&
				(stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack
						.getItemDamage());
	}

	public boolean existsRecipeFor(ItemStack stack)
	{
		for (SmelterRecipe recipe : recipes)
		{
			if (Util.areItemStacksEqualIgnoreSize(recipe.getOutput(), stack))
			{
				return true;
			}
		}

		return false;
	}

	public boolean existsRecipeFrom(ItemStack stack)
	{
		for (SmelterRecipe recipe : recipes)
		{
			if (recipe.getInput().matches(stack))
			{
				return true;
			}
		}

		return false;
	}

	public int getBonusPerBoost(ItemStack input)
	{
		for (SmelterRecipe recipe : recipes)
		{
			if (recipe.getInput().matches(input))
			{
				return recipe.getBonusPerBoost();
			}
		}

		return 0;
	}

	public static List<SmelterRecipe> getRecipes()
	{
		return instance().recipes;
	}

	public static SmelterRecipe removeRecipe(RecipeElement input, ItemStack output)
	{
	    SmelterRecipe recipe;
	    for(Iterator<SmelterRecipe> iter = SmelterRecipeHandler.getRecipes().iterator(); iter.hasNext();)
	    {
		 recipe = iter.next();
		 if(recipe.getInput().equals(input) && ItemStack.areItemStacksEqual(recipe.getOutput(), output))
		 {
		     iter.remove();
		     return recipe;
		 }
	    }
	    return null;
	}
}
