package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JsonAdvancedCraftingHandler
{
	private List<JsonAdvancedRecipe> recipes = new ArrayList<>();

	private List<String> includes = new ArrayList<>();
	private List<String> modDependencies = new ArrayList<>();

	private int version = 0;

	public JsonAdvancedCraftingHandler()
	{ }

	public JsonAdvancedCraftingHandler(boolean addTesting)
	{
		if (addTesting)
		{
			// TESTING ONLY
			recipes.add(new JsonAdvancedRecipe(new ItemStack(Items.STRING, 38),
				new Object[]{new ItemStack(RegisterItems.cloth, 7), "dustRedstone", 13}, "ox ", "xxx", " xo",
				'x', Blocks.BEDROCK, 'o', "nuggetIron"));
		}
	}

	public List<JsonAdvancedRecipe> getRecipes()
	{
		return recipes;
	}

	public List<String> getIncludes()
	{
		return includes;
	}

	public List<String> getModDependencies()
	{
		return modDependencies;
	}

	public int getActualVersion() 
	{
		return version;
	}
	
	public void updateVersion(int newVersion)
	{
		version = newVersion;
	}
}
