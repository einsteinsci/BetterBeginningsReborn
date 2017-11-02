package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.einsteinsci.betterbeginnings.config.BBConfigFolderLoader;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItem;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItemStack;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeHandler;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.List;

public class JsonKilnRecipe
{
	private JsonLoadedItem input;
	private JsonLoadedItemStack output;
	private float experience;

	public JsonKilnRecipe(JsonLoadedItem input, JsonLoadedItemStack output, float xp)
	{
		this.input = input;
		this.output = output;
		experience = xp;
	}
	public JsonKilnRecipe(ItemStack input, ItemStack output, float xp)
	{
		this(new JsonLoadedItem(input), new JsonLoadedItemStack(output), xp);
	}

	public JsonLoadedItem getInput()
	{
		return input;
	}

	public JsonLoadedItemStack getOutput()
	{
		return output;
	}

	public float getExperience()
	{
		return experience;
	}

	public void register()
	{
		List<ItemStack> possibleOutputs = output.getItemStacks();
		if (possibleOutputs.isEmpty())
		{
			LogUtil.log(Level.ERROR, "No matching item found for kiln recipe output '" + output.getItemName() + "'");
			BBConfigFolderLoader.failLoading();
			return;
		}

		if (input.isOreDictionary())
		{
		    	if(!input.isValid()) 
		    	{
		    	    LogUtil.log(Level.ERROR, "No matching oredictionary entry found for smelter recipe input '%s'", input.getItemName());
		    	    BBConfigFolderLoader.failLoading();
		    	    return;
		    	}
			KilnRecipeHandler.addRecipe(input.getItemName(), possibleOutputs.get(0), experience);
			LogUtil.logDebug("Successfully loaded kiln recipe (OreDictionary) for " + possibleOutputs.get(0).toString());
		}
		else
		{
			List<ItemStack> possibleInputs = input.getItemStacks();
			if (possibleInputs.isEmpty())
			{
				LogUtil.log(Level.ERROR, "No matching item found for kiln recipe input '" + input.getItemName() + "'");
				BBConfigFolderLoader.failLoading();
				return;
			}

			KilnRecipeHandler.addRecipe(possibleInputs.get(0), possibleOutputs.get(0), experience);
			LogUtil.logDebug("Successfully loaded kiln recipe for " + possibleOutputs.get(0).toString());
		}
	}
}
