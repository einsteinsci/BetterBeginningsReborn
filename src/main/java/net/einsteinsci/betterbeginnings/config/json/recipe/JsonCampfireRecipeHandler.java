package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JsonCampfireRecipeHandler
{
	private List<JsonCampfireRecipe> recipes = new ArrayList<>();
	private List<JsonCampfirePanRecipe> panRecipes = new ArrayList<>();

	private List<String> includes = new ArrayList<>();
	private List<String> modDependencies = new ArrayList<>();
	
	private int version = 0;

	public JsonCampfireRecipeHandler()
	{ }

	public JsonCampfireRecipeHandler(boolean includeTesting)
	{
		if (includeTesting)
		{
			// TESTING ONLY
			recipes.add(new JsonCampfireRecipe(new ItemStack(Blocks.BEDROCK), new ItemStack(Items.MAGMA_CREAM), 0.5f));
			panRecipes.add(new JsonCampfirePanRecipe(new ItemStack(Blocks.BEDROCK), new ItemStack(Items.ENDER_EYE), 2.0f));
		}
	}

	public List<JsonCampfireRecipe> getRecipes()
	{
		return recipes;
	}

	public List<JsonCampfirePanRecipe> getPanRecipes()
	{
		return panRecipes;
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
