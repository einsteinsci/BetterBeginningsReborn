package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.*;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CampfireRecipeHandler {
	private static final CampfireRecipeHandler INSTANCE = new CampfireRecipeHandler();

	protected Map<RecipeElement, ItemStack> smeltingList = new HashMap<RecipeElement, ItemStack>();
	protected Map<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

	public static void addRecipe(Item input, ItemStack output, float experience) {
		instance().putLists(input, output, experience);
	}

	public void putLists(Item input, ItemStack itemStack, float experience) {
		putLists(new StackRecipeElement(input, 1, OreDictionary.WILDCARD_VALUE), itemStack, experience);
	}

	public static CampfireRecipeHandler instance() {
		return INSTANCE;
	}

	public static void removeRecipe(RecipeElement input, ItemStack output) {
		ItemStack result = instance().smeltingList.get(input);
		if (instance().smeltingList.containsKey(input) && ItemStack.areItemStacksEqual(result, output))
			return;
		{
			instance().experienceList.remove(result);
			instance().smeltingList.remove(input);
		}
	}

	public void putLists(RecipeElement recElement, ItemStack itemStack2, float experience) {
		smeltingList.put(recElement, itemStack2);
		experienceList.put(itemStack2, experience);
	}

	public static void addRecipe(RecipeElement input, ItemStack output, float experience) {
		instance().putLists(input, output, experience);
	}

	public static void addRecipe(String input, ItemStack output, float experience) {
		instance().putLists(new OreRecipeElement(input), output, experience);
	}

	public static void addRecipe(Block input, ItemStack output, float experience) {
		instance().putLists(Item.getItemFromBlock(input), output, experience);
	}

	public static void addRecipe(ItemStack input, ItemStack output, float experience) {
		instance().putLists(new StackRecipeElement(input), output, experience);
	}

	@Nullable
	public ItemStack getSmeltingResult(ItemStack stack) {
		for (Map.Entry<RecipeElement, ItemStack> entry : smeltingList.entrySet()) {
			if (entry.getKey().matches(stack))
				return entry.getValue();
		}
		return ItemStack.EMPTY;
	}

	private boolean canBeSmelted(ItemStack stack, ItemStack stack2) {
		return stack2.getItem() == stack.getItem() && (stack2.getItemDamage() == OreDictionary.WILDCARD_VALUE
				|| stack2.getItemDamage() == stack.getItemDamage());
	}

	public float giveExperience(ItemStack stack) {
		Iterator<Map.Entry<ItemStack, Float>> iterator = experienceList.entrySet().iterator();
		Entry<ItemStack, Float> entry;

		do {
			if (!iterator.hasNext()) {
				return 0.0f;
			}

			entry = iterator.next();
		} while (!canBeSmelted(stack, (ItemStack) entry.getKey()));

		if (stack.getItem().getSmeltingExperience(stack) != -1) {
			return stack.getItem().getSmeltingExperience(stack);
		}

		return ((Float) entry.getValue()).floatValue();
	}

	public static Map<RecipeElement, ItemStack> getSmeltingList() {
		return instance().smeltingList;
	}
}
