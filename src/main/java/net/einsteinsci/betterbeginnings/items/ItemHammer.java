package net.einsteinsci.betterbeginnings.items;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

// TODO: Rework heavily for better compatibility.
public class ItemHammer extends ItemTool
{
    private static final Set<Block> breakableBlocks = Sets.newHashSet(Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRAVEL, Blocks.SANDSTONE);

    protected ItemHammer(Item.ToolMaterial material)
    {
        super(2.0F, -3.1F, material, breakableBlocks);
        setHarvestLevel("pickaxe", 1);
    }

    public static ItemStack getCrushResult(Block broken, IBlockState state)
    {
        if (broken == Blocks.STONE && broken.getMetaFromState(state) == 0)
        {
            return new ItemStack(Blocks.COBBLESTONE);
        }
        else if (broken == Blocks.COBBLESTONE)
        {
            return new ItemStack(Blocks.GRAVEL);
        }
        else if (broken == Blocks.GRAVEL)
        {
            return new ItemStack(Items.FLINT, 2);
        }
        else if (broken == Blocks.SANDSTONE)
        {
            return new ItemStack(Blocks.SAND);
        }
        else
        {
            return ItemStack.EMPTY;
        }
    }
}
