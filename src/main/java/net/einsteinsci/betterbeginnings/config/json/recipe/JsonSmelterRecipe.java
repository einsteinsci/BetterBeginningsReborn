package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.einsteinsci.betterbeginnings.config.BBConfigFolderLoader;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItem;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItemStack;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.List;

public class JsonSmelterRecipe
{
	private JsonLoadedItem input;
	private JsonLoadedItemStack output;
	private float experience;
	private int boosterCount;
	private int bonusPerBoostLevel;

	public JsonSmelterRecipe(JsonLoadedItem input, JsonLoadedItemStack output, float xp, int boosters, int bonus)
	{
		this.input = input;
		this.output = output;
		experience = xp;
		boosterCount = boosters;
		bonusPerBoostLevel = bonus;
	}
	public JsonSmelterRecipe(ItemStack input, ItemStack output, float xp, int boosters, int bonus)
	{
		this(new JsonLoadedItem(input), new JsonLoadedItemStack(output), xp, boosters, bonus);
	}

	public JsonLoadedItem getInput()
	{
		return input;
	}

	public JsonLoadedItemStack getOutput()
	{
		return output;
	}

	public int getBoosterCount()
	{
		return boosterCount;
	}

	public int getBonusPerBoostLevel()
	{
		return bonusPerBoostLevel;
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
			LogUtil.log(Level.ERROR, "No matching item found for smelter recipe output '%s'", output.getItemName());
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
			SmelterRecipeHandler.addRecipe(input.getItemName(), possibleOutputs.get(0),
				experience, boosterCount, bonusPerBoostLevel);
			LogUtil.logDebug("Successfully loaded smelter recipe (OreDictionary) for %s", possibleOutputs.get(0).toString());
		}
		else
		{
			List<ItemStack> possibleInputs = input.getItemStacks();
			if (possibleInputs.isEmpty())
			{
				LogUtil.log(Level.ERROR, "No matching item found for smelter recipe input '%s'", input.getItemName());
				BBConfigFolderLoader.failLoading();
				return;
			}

			SmelterRecipeHandler.addRecipe(possibleInputs.get(0), possibleOutputs.get(0),
				experience, boosterCount, bonusPerBoostLevel);
			LogUtil.logDebug("Successfully loaded smelter recipe for %s", possibleOutputs.get(0).toString());
		}
	}
}
