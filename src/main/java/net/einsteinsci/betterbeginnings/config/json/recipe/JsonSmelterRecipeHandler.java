package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JsonSmelterRecipeHandler
{
	private List<JsonSmelterRecipe> recipes = new ArrayList<>();

	private List<String> includes = new ArrayList<>();
	private List<String> modDependencies = new ArrayList<>();
	
	private int version = 0;

	public JsonSmelterRecipeHandler()
	{ }

	public JsonSmelterRecipeHandler(boolean addTesting)
	{
		if (addTesting)
		{
			// TESTING ONLY //
			recipes.add(new JsonSmelterRecipe(new ItemStack(Blocks.BEDROCK), new ItemStack(Items.GOLDEN_HOE), 0.5f, 1, 0));
		}
	}

	public List<JsonSmelterRecipe> getRecipes()
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
