package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JsonKilnRecipeHandler
{
	private List<JsonKilnRecipe> recipes = new ArrayList<>();

	private List<String> includes = new ArrayList<>();
	private List<String> modDependencies = new ArrayList<>();
	
	private int version = 0;

	public JsonKilnRecipeHandler()
	{ }

	public JsonKilnRecipeHandler(boolean includeTesting)
	{
		if (includeTesting)
		{
			// TESTING ONLY
			recipes.add(new JsonKilnRecipe(new ItemStack(Blocks.BEDROCK), new ItemStack(Items.BLAZE_ROD), 0.5f));
		}
	}

	public List<JsonKilnRecipe> getRecipes()
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
