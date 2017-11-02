package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.client.ISpecialRender;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

public class RegisterModels
{
	@SuppressWarnings("unchecked")
	public static <BBBlock extends Block & IBBName, BBItem extends Item & IBBName> void register()
	{
		// blocks
		for (Block b : RegisterBlocks.allBlocks)
		{
			registerBlock((BBBlock)b);
		}

		// items
		for (Item i : RegisterItems.allItems)
		{
			//Skip ItemBlocks as their models are registered during the block model registration
			if(i instanceof ItemBlock && RegisterBlocks.allBlocks.contains(Block.getBlockFromItem(i))) continue;
			registerItem((BBItem)i);
		}
	}

	public static <BBBlock extends Block & IBBName> void registerBlock(BBBlock block)
	{
		Item item = Item.getItemFromBlock(block);
		if(item != null)
		{
		    if(item instanceof ISpecialRender)
			((ISpecialRender) item).registerRender();
		    else
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
		}
	}

	public static <BBItem extends Item & IBBName> void registerItem(BBItem item)
	{
	    if(item instanceof ISpecialRender)
		((ISpecialRender) item).registerRender();
	    else
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
