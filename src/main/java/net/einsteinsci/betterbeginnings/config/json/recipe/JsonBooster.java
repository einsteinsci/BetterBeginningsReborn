package net.einsteinsci.betterbeginnings.config.json.recipe;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItem;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class JsonBooster
{
	private JsonLoadedItem boosterItem;
	private float boostAmount;

	public JsonBooster(ItemStack stack, float boost)
	{
		boosterItem = new JsonLoadedItem(stack);
		boostAmount = boost;
	}
	public JsonBooster(Item item, float boost)
	{
		this(new ItemStack(item), boost);
	}
	public JsonBooster(Block block, float boost)
	{
		this(new ItemStack(block), boost);
	}

	public void register()
	{
		if (boosterItem.isOreDictionary())
		{
		    	if(!boosterItem.isValid()) 
		    	{
		    	    LogUtil.log(Level.ERROR, "No matching oredictionary entry found for smelter recipe input '%s'", boosterItem.getItemName());
		    	    return;
		    	}
			for (ItemStack stack : OreDictionary.getOres(boosterItem.getItemName(), false))
			{
				if (!stack.isEmpty())
				{
					TileEntitySmelterBase.registerBooster(stack, boostAmount);
				}
			}
		}
		else
		{
		    	if(!boosterItem.isValid()) 
		    	{
		    	    LogUtil.log(Level.ERROR, "No matching item found for smelter recipe input '%s'", boosterItem.getItemName());
		    	    return;
		    	}
			ItemStack stack = boosterItem.getFirstItemStackOrNull();
			if (!stack.isEmpty())
			{
				TileEntitySmelterBase.registerBooster(stack, boostAmount);
			}
		}
	}

	public JsonLoadedItem getBoosterItem()
	{
		return boosterItem;
	}

	public float getBoostAmount()
	{
		return boostAmount;
	}
}
