package net.einsteinsci.betterbeginnings.config.json.recipe;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.config.BBConfigFolderLoader;
import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItem;
import net.einsteinsci.betterbeginnings.register.RemoveRecipes;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.item.ItemStack;

public class JsonRemovedCraftingRecipe
{
	public JsonLoadedItem removedItem;

	public JsonRemovedCraftingRecipe(ItemStack stack)
	{
		removedItem = new JsonLoadedItem(stack);
	}

	public void register()
	{
	    	if(!removedItem.isValid())
	    	{
	    	    LogUtil.log(Level.ERROR, "(Removed Crafting Recipe) No matching item found for output '" + removedItem.getItemName() + "'");
	    	    BBConfigFolderLoader.failLoading();
	    	    return;
	    	}
		for (ItemStack rem : removedItem.getItemStacks())
		{
			RemoveRecipes.getCustomRemovedCraftingRecipes().add(rem);
		}
	}

	public JsonLoadedItem getRemovedItem()
	{
		return removedItem;
	}
}
