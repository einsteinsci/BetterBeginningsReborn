package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityBrickOven;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BrickOvenRecipeHandler
{
	private static final BrickOvenRecipeHandler INSTANCE = new BrickOvenRecipeHandler();
	protected List<IBrickOvenRecipe> recipes = new ArrayList<>();

	public BrickOvenRecipeHandler()
	{ }

	public static void addShapedRecipe(ItemStack output, Object... args)
	{
		instance().putShapedRecipe(output, args);
	}

	public BrickOvenShapedRecipe putShapedRecipe(ItemStack result, Object... args)
	{
		String s = "";
		int i = 0;
		int j = 0;
		int k = 0;

		if (args[i] instanceof String[])
		{
			String[] astring = (String[])args[i++];

			for (int l = 0; l < astring.length; ++l)
			{
				String s1 = astring[l];
				++k;
				j = s1.length();
				s = s + s1;
			}
		}
		else
		{
			while (args[i] instanceof String)
			{
				String s2 = (String)args[i++];
				++k;
				j = s2.length();
				s = s + s2;
			}
		}

		Map<Character, RecipeElement> hashmap;

		for (hashmap = new HashMap<Character, RecipeElement>(); i < args.length; i += 2)
		{
			Character character = (Character)args[i];
			RecipeElement stackInRecipe = null;

			if (args[i + 1] instanceof Item)
			{
				stackInRecipe = new StackRecipeElement(new ItemStack((Item)args[i + 1], 1, OreDictionary.WILDCARD_VALUE));
			}
			else if (args[i + 1] instanceof Block)
			{
				stackInRecipe = new StackRecipeElement(
					new ItemStack((Block)args[i + 1], 1, OreDictionary.WILDCARD_VALUE));
			}
			else if (args[i + 1] instanceof ItemStack)
			{
				stackInRecipe = new StackRecipeElement((ItemStack)args[i + 1]);
			}
			else if (args[i + 1] instanceof String)
			{
				stackInRecipe = new OreRecipeElement((String)args[i + 1], 1);
			}

			hashmap.put(character, stackInRecipe);
		}

		RecipeElement[] aOreRecipeElement = new RecipeElement[j * k];

		for (int i1 = 0; i1 < j * k; ++i1)
		{
			char c0 = s.charAt(i1);

			if (hashmap.containsKey(c0))
			{
				aOreRecipeElement[i1] = ((RecipeElement)hashmap.get(c0)).copy();
			}
			else
			{
				aOreRecipeElement[i1] = null;
			}
		}

		BrickOvenShapedRecipe ovenrecipe = new BrickOvenShapedRecipe(j, k, aOreRecipeElement, result);
		recipes.add(ovenrecipe);
		return ovenrecipe;
	}

	public static BrickOvenRecipeHandler instance()
	{
		return INSTANCE;
	}

	public static void addShapelessRecipe(ItemStack output, Object... args)
	{
		instance().putShapelessRecipe(output, args);
	}

	public BrickOvenShapelessRecipe putShapelessRecipe(ItemStack output, Object... args)
	{
		ArrayList<RecipeElement> res = new ArrayList<RecipeElement>();

		for (Object obj : args)
		{

			if (obj instanceof ItemStack)
			{
				res.add(new StackRecipeElement((ItemStack)obj));
			}
			else if (obj instanceof Item)
			{
				res.add(new StackRecipeElement(new ItemStack((Item)obj)));
			}
			else if (obj instanceof Block)
			{
				res.add(new StackRecipeElement(new ItemStack((Block)obj)));
			}
			else if (obj instanceof String)
			{
				res.add(new OreRecipeElement((String)obj));
			}
			else if(obj instanceof RecipeElement)
			{
				res.add((RecipeElement) obj);
			}
			else
			{
				LogUtil.log(Level.WARN, "Invalid shapeless recipe!");
			}
		}

		BrickOvenShapelessRecipe recipe = new BrickOvenShapelessRecipe(output, res.toArray(new RecipeElement[0]));
		recipes.add(recipe);

		return recipe;
	}

	public ItemStack findMatchingRecipe(TileEntityBrickOven oven)
	{
		for (IBrickOvenRecipe recipe : recipes)
		{
			if (recipe.matches(oven))
			{
				return recipe.getCraftingResult(oven);
			}
		}

		return ItemStack.EMPTY;
	}

	public boolean isInRecipe(ItemStack stack)
	{
		for (IBrickOvenRecipe recipe : recipes)
		{
			if (recipe.contains(stack))
			{
				return true;
			}
		}
		return false;
	}

	public boolean existsRecipeFor(ItemStack stack)
	{
		for (IBrickOvenRecipe recipe : recipes)
		{
			if (Util.areItemStacksEqualIgnoreSize(recipe.getRecipeOutput(), stack))
			{
				return true;
			}
		}

		return false;
	}

	public static List<IBrickOvenRecipe> getRecipeList()
	{
		return instance().recipes;
	}
}
