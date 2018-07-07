package net.einsteinsci.betterbeginnings.blocks;

import java.util.Random;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityEnderSmelter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnderSmelter extends BlockSpecializedFurnace
{
    public BlockEnderSmelter(boolean lit)
    {
        super(Material.ROCK);

        if (lit)
        {
            setLightLevel(0.875F);
        }
        else
        {
            setLightLevel(0.0F);
            setCreativeTab(ModMain.tabBetterBeginnings);
        }

        setHardness(1.5f);
        setResistance(10.0f);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

        isLit = lit;
    }

    @Override
    public String getName()
    {
        return isLit ? "ender_smelter_lit" : "ender_smelter";
    }

    public static void updateBlockState(boolean lit, World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        TileEntity tileEntity = world.getTileEntity(pos);

        if (lit)
        {
            world.setBlockState(pos, RegisterBlocks.enderSmelterLit.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING)), 3);
        }
        else
        {
            world.setBlockState(pos, RegisterBlocks.enderSmelter.getDefaultState()
                    .withProperty(FACING, state.getValue(FACING)), 3);
        }

        if (tileEntity != null)
        {
            tileEntity.validate();
            world.setTileEntity(pos, tileEntity);
        }
    }

    @Override
    public EnumParticleTypes getFlameParticle()
    {
        return EnumParticleTypes.PORTAL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3)
    {
        return Item.getItemFromBlock(RegisterBlocks.enderSmelter);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side,
        float hitX, float hitY, float hitZ)
    {
        player.openGui(ModMain.modInstance, BBGuiHandler.ENDERSMELTER_ID, world,
                       pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityEnderSmelter();
    }
}
