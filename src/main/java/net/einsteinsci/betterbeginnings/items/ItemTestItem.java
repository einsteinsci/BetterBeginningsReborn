package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTestItem extends Item implements IBBName
{
    public ItemTestItem()
    {
        super();
        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos,
        EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            EntityMinecartFurnace kilnCart = new EntityMinecartFurnace(world, pos.getX(), pos.getY() + 1.0F, pos.getZ());
            kilnCart.setDisplayTile(RegisterBlocks.kiln.getDefaultState());
            kilnCart.setEntityBoundingBox(kilnCart.getEntityBoundingBox().offset(0.0F, 2.0F, 0.0F));
            world.spawnEntity(kilnCart);
        }
     
        return EnumActionResult.SUCCESS;
    }

    @Override
    public String getName()
    {
        return "test_item";
    }
}
