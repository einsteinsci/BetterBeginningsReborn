package net.einsteinsci.betterbeginnings.blocks;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityInfusionRepair;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockInfusionRepairStation extends Block implements IBBName
{
	public BlockInfusionRepairStation()
	{
		super(Material.ROCK);

		setCreativeTab(ModMain.tabBetterBeginnings);
		setHardness(2.0f);
		setResistance(6000);
	}

	@Override
	public String getName()
	{
		return "infusion_repair_station";
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) 
	{
		return true;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
	    	TileEntity te = world.getTileEntity(pos);
	    	if(te != null)
	    	{
	    	    ((TileEntityInfusionRepair) te).activate(world, pos, state, player, hand, heldItem, side, hitZ, hitZ, hitZ);
	    	    return true;
	    	}
		return false;
	}

	// Drop stuff everywhere
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileEntityInfusionRepair)
		{
			Util.dropInventory(world, pos, CapUtils.getItemHandler(tileentity));
			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityInfusionRepair();
	}
}
