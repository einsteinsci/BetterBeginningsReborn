package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.blocks.BlockCampfire;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemFireBow extends Item implements IBBName
{
    public ItemFireBow()
    {
        super();

        setMaxStackSize(1);
        setMaxDamage(8);

        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
            EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
  
        ItemStack stack = player.getHeldItem(hand);
        
        if (world.getBlockState(pos).getBlock() instanceof BlockCampfire)
        {
            stack.damageItem(1, player);
            return EnumActionResult.SUCCESS;
        }

        switch (side)
        {
        case UP:
            ++y;
            break;
        case DOWN:
            --y;
            break;
        case NORTH:
            --z;
            break;
        case SOUTH:
            ++z;
            break;
        case EAST:
            ++x;
            break;
        case WEST:
            --x;
            break;
        default:
            break;
        }
        
        if (side == EnumFacing.DOWN)
        {
            --y;
        }

        if (side == EnumFacing.UP)
        {
            ++y;
        }

        if (side == EnumFacing.NORTH)
        {
            --z;
        }

        if (side == EnumFacing.SOUTH)
        {
            ++z;
        }

        if (side == EnumFacing.WEST)
        {
            --x;
        }

        if (side == EnumFacing.EAST)
        {
            ++x;
        }

        if (!player.canPlayerEdit(pos, side, stack))
        {
            return EnumActionResult.PASS;
        }
        else
        {
            if (world.isAirBlock(pos))
            {
                world.playSound((double)x + 0.5D, (double)y + 0.5D, (double)z + 0.5D, SoundEvents.ITEM_FLINTANDSTEEL_USE,
                    SoundCategory.BLOCKS, 1.0F, itemRand.nextFloat() * 0.4F + 0.8F, false);
                world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }

            stack.damageItem(1, player);
            return EnumActionResult.SUCCESS;
        }
    }

    @Override
    public String getName()
    {
        return "fire_bow";
    }
}
