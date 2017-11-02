package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class JsonBrickOvenRecipeHandler
{
	private List<JsonBrickOvenShapedRecipe> shaped = new ArrayList<>();
	private List<JsonBrickOvenShapelessRecipe> shapeless = new ArrayList<>();

	private List<String> includes = new ArrayList<>();
	private List<String> modDependencies = new ArrayList<>();

	//private List<String> __COMMENTS = new ArrayList<>();
	private int version = 0;

	public JsonBrickOvenRecipeHandler()
	{
		this(false);
	}
	public JsonBrickOvenRecipeHandler(boolean includeTesting)
	{
		//__COMMENTS = new ArrayList<>();
		//__COMMENTS.add("Brick Oven recipes do not support ore dictionary in ingredients.");
		//__COMMENTS.add("This will change eventually. This only applies to Brick Oven recipes.");

		if (includeTesting)
		{
			// TESTING ONLY
			shaped.add(new JsonBrickOvenShapedRecipe(new ItemStack(Items.BEEF), "x ", "ox", 'x',
				Blocks.BEDROCK, 'o', Items.CHAINMAIL_CHESTPLATE));

			shapeless.add(new JsonBrickOvenShapelessRecipe(new ItemStack(Items.PORKCHOP), Blocks.BEDROCK, Items.CHAINMAIL_BOOTS));
		}
	}

	public List<JsonBrickOvenShapedRecipe> getShaped()
	{
		return shaped;
	}

	public List<JsonBrickOvenShapelessRecipe> getShapeless()
	{
		return shapeless;
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
