package net.einsteinsci.betterbeginnings.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelter;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BlockSmelter extends BlockSpecializedFurnace
{
	public BlockSmelter(boolean lit)
	{
		super(Material.ROCK);

		if (lit)
		{
			setLightLevel(0.875F);
		}
		else
		{
			setLightLevel(0F);
			setCreativeTab(ModMain.tabBetterBeginnings);
		}

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(1.5f);
		setResistance(10.0f);

		isLit = lit;
	}

	@Override
	public String getName()
	{
		return isLit ? "smelter_lit" : "smelter";
	}

	public static void updateBlockState(boolean lit, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		TileEntity tileEntity = world.getTileEntity(pos);

		if (lit)
		{
			world.setBlockState(pos, RegisterBlocks.smelterLit.getDefaultState()
					.withProperty(FACING, state.getValue(FACING)), 3);
		}
		else
		{
			world.setBlockState(pos, RegisterBlocks.smelter.getDefaultState()
					.withProperty(FACING, state.getValue(FACING)), 3);
		}

		if (tileEntity != null)
		{
			tileEntity.validate();
			world.setTileEntity(pos, tileEntity);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int par3)
	{
		return Item.getItemFromBlock(RegisterBlocks.smelter);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		player.openGui(ModMain.modInstance, BBGuiHandler.SMELTER_ID, world, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntitySmelter();
	}
}
