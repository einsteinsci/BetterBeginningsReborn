package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CampfirePanRecipeHandler
{
	private static final CampfirePanRecipeHandler INSTANCE = new CampfirePanRecipeHandler();

	private Map<RecipeElement, ItemStack> smeltingList = new HashMap<RecipeElement, ItemStack>();
	private Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	private CampfirePanRecipeHandler()
	{
		// nothing here
	}
	
	public static void removeRecipe(RecipeElement input, ItemStack output)
	{
	    if(!instance().smeltingList.containsKey(input) || !ItemStack.areItemStacksEqual(instance().smeltingList.get(input), output)) return;
	    instance().experienceList.remove(instance().smeltingList.get(input));
	    instance().smeltingList.remove(input);
	}

	public static void addRecipe(Item input, ItemStack output, float experience)
	{
		instance().addLists(input, output, experience);
	}

	public void addLists(Item input, ItemStack itemStack, float experience)
	{
		putLists(new StackRecipeElement(input, 1, OreDictionary.WILDCARD_VALUE), itemStack, experience);
	}

	public static CampfirePanRecipeHandler instance()
	{
		return INSTANCE;
	}
	
	public static void addRecipe(RecipeElement input, ItemStack output, float experience)
	{
		instance().putLists(input, output, experience);
	}

	public static void addRecipe(String input, ItemStack output, float experience)
	{
		instance().putLists(new OreRecipeElement(input), output, experience);
	}

	public void putLists(RecipeElement recElement, ItemStack itemStack2, float experience)
	{
		smeltingList.put(recElement, itemStack2);
		experienceList.put(itemStack2, experience);
	}

	public static void addRecipe(Block input, ItemStack output, float experience)
	{
		instance().addLists(Item.getItemFromBlock(input), output, experience);
	}

	public static void addRecipe(ItemStack input, ItemStack output, float experience)
	{
		instance().putLists(new StackRecipeElement(input), output, experience);
	}

	public ItemStack getSmeltingResult(ItemStack stack)
	{
		for(Map.Entry<RecipeElement, ItemStack> entry : smeltingList.entrySet())
		{
			if(entry.getKey().matches(stack)) return entry.getValue();
		}
		return null;
	}

	private boolean canBeSmelted(ItemStack stack, ItemStack stack2)
	{
		return stack2.getItem() == stack.getItem()
				&& (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE || stack2.getItemDamage() == stack
				.getItemDamage());
	}

	public float giveExperience(ItemStack stack)
	{
		Iterator<Map.Entry<ItemStack, Float>> iterator = experienceList.entrySet().iterator();
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

	public static Map<RecipeElement, ItemStack> getSmeltingList()
	{
		return instance().smeltingList;
	}
}
