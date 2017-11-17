package net.einsteinsci.betterbeginnings.blocks;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.register.achievement.RegisterAchievements;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDoubleWorkbench extends Block implements IBBName
{
    public static final PropertyDirection CONNECTION = PropertyDirection.create("connection");

    public BlockDoubleWorkbench()
    {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
        setCreativeTab(ModMain.tabBetterBeginnings);
        setDefaultState(blockState.getBaseState().withProperty(CONNECTION, EnumFacing.UP));

        setHardness(2.0f);
        setResistance(5.0f);
    }

    @Override
    public String getName()
    {
        return "double_workbench";
    }

    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(CONNECTION).getIndex();
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(CONNECTION, EnumFacing.getFront(meta));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
    
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        world.setBlockState(pos, state.withProperty(CONNECTION, EnumFacing.UP));
        IBlockState north = world.getBlockState(pos.north());
        if (north.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos, state.withProperty(CONNECTION, EnumFacing.NORTH), 3);
            world.setBlockState(pos.north(), north.withProperty(CONNECTION, EnumFacing.SOUTH), 3);
        }

        IBlockState south = world.getBlockState(pos.south());
        if (south.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos, state.withProperty(CONNECTION, EnumFacing.SOUTH), 3);
            world.setBlockState(pos.south(), south.withProperty(CONNECTION, EnumFacing.NORTH), 3);
        }

        IBlockState east = world.getBlockState(pos.east());
        if (east.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos, state.withProperty(CONNECTION, EnumFacing.EAST), 3);
            world.setBlockState(pos.east(), east.withProperty(CONNECTION, EnumFacing.WEST), 3);
        }

        IBlockState west = world.getBlockState(pos.west());
        if (west.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos, state.withProperty(CONNECTION, EnumFacing.WEST), 3);
            world.setBlockState(pos.west(), west.withProperty(CONNECTION, EnumFacing.EAST), 3);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        IBlockState north = world.getBlockState(pos.north());
        if (north.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos.north(), north.withProperty(CONNECTION, EnumFacing.UP), 3);
        }
        IBlockState south = world.getBlockState(pos.south());
        if (south.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos.south(), south.withProperty(CONNECTION, EnumFacing.UP), 3);
        }
        IBlockState east = world.getBlockState(pos.east());
        if (east.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos.east(), east.withProperty(CONNECTION, EnumFacing.UP), 3);
        }
        IBlockState west = world.getBlockState(pos.west());
        if (west.getBlock().equals(RegisterBlocks.doubleWorkbench))
        {
            world.setBlockState(pos.west(), west.withProperty(CONNECTION, EnumFacing.UP), 3);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        if (world.getBlockState(pos.north()).getBlock().equals(this) &&
                world.getBlockState(pos.north()).getValue(CONNECTION) != EnumFacing.UP)
        {
            return false;
        }
        if (world.getBlockState(pos.south()).getBlock().equals(this) &&
                world.getBlockState(pos.south()).getValue(CONNECTION) != EnumFacing.UP)
        {
            return false;
        }
        if (world.getBlockState(pos.east()).getBlock().equals(this) &&
                world.getBlockState(pos.east()).getValue(CONNECTION) != EnumFacing.UP)
        {
            return false;
        }
        if (world.getBlockState(pos.west()).getBlock().equals(this) &&
                world.getBlockState(pos.west()).getValue(CONNECTION) != EnumFacing.UP)
        {
            return false;
        }

        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
        float hitX, float hitY, float hitZ)
    {
        if (state.getValue(CONNECTION).equals(EnumFacing.UP))
        {
            player.openGui(ModMain.modInstance, BBGuiHandler.SIMPLEWORKBENCH_ID, world,
                           pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        else
        {
            player.openGui(ModMain.modInstance, BBGuiHandler.DOUBLEWORKBENCH_ID, world,
                           pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
        ItemStack stack)
    {
        if (placer instanceof EntityPlayer)
        {
            RegisterAchievements.achievementGet((EntityPlayer)placer, "doubleWorkbench");
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, CONNECTION);
    }
}
