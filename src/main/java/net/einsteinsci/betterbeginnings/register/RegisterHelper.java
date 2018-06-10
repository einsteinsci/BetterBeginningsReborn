package net.einsteinsci.betterbeginnings.register;

import java.util.ArrayList;
import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class RegisterHelper
{
	private static final List<ItemBlock> itemBlocks = new ArrayList<>();
	
    public static <T extends Item & IBBName> void registerItem(IForgeRegistry<Item> itemRegistry, T item)
    {
        registerItem(itemRegistry, item, item.getName());
    }

    public static <T extends Item & IBBName> void registerItem(IForgeRegistry<Item> itemRegistry, T item, String name)
    {
        setupItem(item, name);
        itemRegistry.register(item);
    }
    
    private static <T extends Item> T setupItem(T item, String name)
    {
    	if(!name.toLowerCase().equals(name))
        {
            throw new IllegalArgumentException(name + " contains non-lowercase characters. Registry names should be lowercase in preparation for 1.11");
        }
        item.setRegistryName(name);
        item.setUnlocalizedName(ModMain.MODID + "." + name);
        RegisterItems.allItems.add(item);
        return item;
    }

    public static <T extends Block & IBBName> void registerBlock(IForgeRegistry<Block> blockRegistry, T block)
    {
        registerBlock(blockRegistry, block, block.getName());
        itemBlocks.add(setupItem(new ItemBlock(block), block.getName()));
    }

    public static <B extends Block & IBBName> void registerBlock(IForgeRegistry<Block> blockRegistry, B block, ItemBlock itemblock)
    {
        registerBlock(blockRegistry, block, block.getName());
        itemBlocks.add(setupItem(itemblock, block.getName()));
    }

    public static void registerBlock(IForgeRegistry<Block> blockRegistry, Block block, ItemBlock itemblock, String name)
    {
        registerBlock(blockRegistry, block, name);
        itemBlocks.add(setupItem(itemblock, name));
    }

    public static void registerBlock(IForgeRegistry<Block> blockRegistry, Block block, String name)
    {
        if (!name.toLowerCase().equals(name))
        {
            throw new IllegalArgumentException(name + " contains non-lowercase characters. Registry names should be lowercase in preparation for 1.11");
        }
        
        block.setRegistryName(name);
        block.setUnlocalizedName(ModMain.MODID + "." + name);
        blockRegistry.register(block);
        RegisterBlocks.allBlocks.add(block);
    }

    public static <T extends Block & IBBName> void registerBlockWithoutItemBlock(IForgeRegistry<Block> blockRegistry, T block)
    {
        registerBlock(blockRegistry, block, block.getName());
    }
    
    public static void registerItemBlocks(IForgeRegistry<Item> itemRegistry)
    {
    	for(ItemBlock itemBlock : itemBlocks) itemRegistry.register(itemBlock);
    }

    public static void registerSmelterOreRecipe(String input, String output, float experience, int boosters, int bonus)
    {
        for (ItemStack stack : OreDictionary.getOres(input, false))
        {
            List<ItemStack> valid = OreDictionary.getOres(output, false);
            if (!valid.isEmpty())
            {
                SmelterRecipeHandler.addRecipe(stack, valid.get(0), experience, boosters, bonus);
            }
        }
    }
}
