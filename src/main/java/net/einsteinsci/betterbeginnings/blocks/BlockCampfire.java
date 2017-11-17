package net.einsteinsci.betterbeginnings.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.items.ItemFireBow;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockCampfire extends Block implements IBBName
{
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.15F, 0.0F, 0.15F, 0.85F, 0.5F, 0.85F);
    private static boolean isAlteringLit;
    private final boolean isLit; // strange why...

    public BlockCampfire(boolean lit)
    {
        super(Material.WOOD);
        setResistance(3.0F);
        setHardness(0.5F);

        if (!lit)
        {
            setLightLevel(0.0f);
            setCreativeTab(ModMain.tabBetterBeginnings);
        }
        else
        {
            setLightLevel(0.875f);
        }

        isLit = lit;
    }

    @Override
    public String getName()
    {
        return isLit ? "campfire_lit" : "campfire";
    }

    public static void updateBlockState(boolean lit, World world, BlockPos pos)
    {
        TileEntity tileEntity = world.getTileEntity(pos);
        isAlteringLit = true;

        if (lit)
        {
            world.setBlockState(pos, RegisterBlocks.campfireLit.getDefaultState(), 3);
        }
        else
        {
            world.setBlockState(pos, RegisterBlocks.campfire.getDefaultState(), 3);
        }

        isAlteringLit = false;

        if (tileEntity != null)
        {
            tileEntity.validate();
            world.setTileEntity(pos, tileEntity);
        }
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        TileEntityCampfire tile = (TileEntityCampfire)world.getTileEntity(pos);
        
        if (tile.campfireState == TileEntityCampfire.STATE_BURNING)
        {
            for (int i = 0; i < 3; i++)
            {
                double ry = rand.nextDouble() * 0.5 + 0.15;
                double rx = rand.nextDouble() * 0.4 + 0.3;
                double rz = rand.nextDouble() * 0.4 + 0.3;

                double vx = rand.nextDouble() * 0.02 - 0.01;
                double vy = rand.nextDouble() * 0.05 + 0.03;
                double vz = rand.nextDouble() * 0.02 - 0.01;
                world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                                    vx, vy, vz);
            }
        }
        else if (tile.campfireState == TileEntityCampfire.STATE_DECAYING)
        {
            for (int i = 0; i < 2; i++)
            {
                double ry = rand.nextDouble() * 0.5 + 0.15;
                double rx = rand.nextDouble() * 0.4 + 0.3;
                double rz = rand.nextDouble() * 0.4 + 0.3;

                double vx = rand.nextDouble() * 0.02 - 0.01;
                double vy = rand.nextDouble() * 0.05 + 0.03;
                double vz = rand.nextDouble() * 0.02 - 0.01;
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                                    vx, vy, vz);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int par3)
    {
        return Item.getItemFromBlock(RegisterBlocks.campfire);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return world.getBlockState(pos).getBlock().isReplaceable(world, pos) &&
            world.isSideSolid(pos.offset(EnumFacing.DOWN), EnumFacing.UP);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        
        if (heldItem != ItemStack.EMPTY)
        {
            Item item = heldItem.getItem();

            if (item instanceof ItemFlintAndSteel || item instanceof ItemFireBow)
            {
                return false;
            }
        }

        player.openGui(ModMain.modInstance, BBGuiHandler.CAMPFIRE_ID, world, pos.getX(), pos.getY(), pos.getZ());

        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    // Drop stuff everywhere
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        if (!isAlteringLit)
        {
            TileEntity tileentity = world.getTileEntity(pos);

            if (tileentity instanceof TileEntityCampfire)
            {
                IItemHandler itemHandler = CapUtils.getItemHandler(tileentity);
                Util.dropInventory(world, pos, itemHandler);
                world.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(world, pos, state);
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileEntityCampfire();
    }
}
