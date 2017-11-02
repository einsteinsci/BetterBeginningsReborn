package net.einsteinsci.betterbeginnings.config.json.recipe;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.config.BBConfigFolderLoader;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItem;
import net.einsteinsci.betterbeginnings.register.RemoveRecipes;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.item.ItemStack;

public class JsonRemovedSmeltingRecipe
{
	public JsonLoadedItem removedItem;

	public JsonRemovedSmeltingRecipe(ItemStack stack)
	{
		removedItem = new JsonLoadedItem(stack);
	}

	public void register()
	{
	    	if(!removedItem.isValid()) 
	    	{
	    	    LogUtil.log(Level.ERROR, "(Removed Furnace Recipe) No matching item found for output '" + removedItem.getItemName() + "'");
	    	    BBConfigFolderLoader.failLoading();
	    	    return;
	    	}
		for (ItemStack rem : removedItem.getItemStacks())
		{
			RemoveRecipes.getCustomRemovedFurnaceRecipes().add(rem);
		}
	}

	public JsonLoadedItem getRemovedItem()
	{
		return removedItem;
	}
}
