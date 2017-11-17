package net.einsteinsci.betterbeginnings.blocks;

import java.util.Random;

import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySpecializedFurnace;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public abstract class BlockSpecializedFurnace extends BlockHorizontal implements IBBName
{
    protected boolean isLit;

    protected BlockSpecializedFurnace(Material materialIn)
    {
        super(materialIn);
    }

    // Drop stuff everywhere
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        if(!(world.getBlockState(pos).getBlock() instanceof BlockSpecializedFurnace))
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileEntitySpecializedFurnace)
            {
                IItemHandler itemHandler = CapUtils.getItemHandler(tileentity);
                Util.dropInventory(world, pos, itemHandler);
                world.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(world, pos, state);
    }

    // TODO: resolve deprecation
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
        float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
            ItemStack stack)
    {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

        if (stack.hasDisplayName())
        {
            TileEntity teBase = world.getTileEntity(pos);
            if (teBase instanceof TileEntitySpecializedFurnace)
            {
                TileEntitySpecializedFurnace tileEntity = (TileEntitySpecializedFurnace)teBase;
                tileEntity.setBlockName(stack.getDisplayName());
            }
        }
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    // TODO: resolve deprecation
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public EnumParticleTypes getFlameParticle()
    {
        return EnumParticleTypes.FLAME;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        if (isLit)
        {
            double d0 = (double)pos.getX() + 0.5D;
            double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double)pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch (state.getValue(FACING))
            {
            case WEST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(getFlameParticle(), d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(getFlameParticle(), d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case NORTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(getFlameParticle(), d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(getFlameParticle(), d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
            default:
                break;
            }
        }
    }
}
