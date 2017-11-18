package net.einsteinsci.betterbeginnings.register;

import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.config.json.SmelterConfig;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterHelper
{
    public static <T extends Item & IBBName> void registerItem(T item)
    {
        registerItem(item, item.getName());
    }

    public static void registerItem(Item item, String name)
    {
        if(!name.toLowerCase().equals(name))
        {
            throw new IllegalArgumentException(name + " contains non-lowercase characters. Registry names should be lowercase in preparation for 1.11");
        }
        item.setRegistryName(name);
        item.setUnlocalizedName(ModMain.MODID + "." + name);
        GameRegistry.register(item); // TODO: Replace with Forge registry constants.
        RegisterItems.allItems.add(item);
    }

    public static <T extends Block & IBBName> void registerBlock(T block)
    {
        registerBlock(block, block.getName());
        registerItem(new ItemBlock(block), block.getName());
    }

    public static <B extends Block & IBBName> void registerBlock(B block, ItemBlock itemblock)
    {
        registerBlock(block, block.getName());
        registerItem(itemblock, block.getName());
    }

    public static void registerBlock(Block block, ItemBlock itemblock, String name)
    {
        registerBlock(block, name);
        registerItem(itemblock, name);
    }

    public static void registerBlock(Block block, String name)
    {
        if (!name.toLowerCase().equals(name))
        {
            throw new IllegalArgumentException(name + " contains non-lowercase characters. Registry names should be lowercase in preparation for 1.11");
        }
        
        block.setRegistryName(name);
        block.setUnlocalizedName(ModMain.MODID + "." + name);
        GameRegistry.register(block); // TODO: Replace with Forge registry constants.
        RegisterBlocks.allBlocks.add(block);
    }

    public static <T extends Block & IBBName> void registerBlockWithoutItemBlock(T block)
    {
        registerBlock(block, block.getName());
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

    public static void registerSmelterConfigOreRecipe(String input, String output, float experience, int boosters, int bonus)
    {
        for (ItemStack stack : OreDictionary.getOres(input, false))
        {
            List<ItemStack> valid = OreDictionary.getOres(output, false);
            if (!valid.isEmpty())
            {
                SmelterConfig.addRecipe(stack, valid.get(0), experience, boosters, bonus);
            }
        }
    }
}
