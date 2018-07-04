package net.einsteinsci.betterbeginnings.blocks;

import java.util.Random;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.einsteinsci.betterbeginnings.util.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockNetherBrickOven extends BlockSpecializedFurnace {
	static final FluidStack MAX_LAVA = new FluidStack(FluidRegistry.LAVA, Integer.MAX_VALUE);

	public BlockNetherBrickOven(boolean lit) {
		super(Material.ROCK);

		if (lit) {
			setLightLevel(0.875F);
		} else {
			setLightLevel(0F);
			setCreativeTab(ModMain.tabBetterBeginnings);
		}

		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		setHardness(4.0f);
		setResistance(10.0f);

		isLit = lit;
	}

	@Override
	public String getName() {
		return isLit ? "nether_brick_oven_lit" : "nether_brick_oven";
	}

	public static void updateBlockState(boolean lit, World world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		TileEntity tileEntity = world.getTileEntity(pos);

		if (lit) {
			world.setBlockState(pos,
					RegisterBlocks.netherBrickOvenLit.getDefaultState().withProperty(FACING, state.getValue(FACING)),
					3);
		} else {
			world.setBlockState(pos,
					RegisterBlocks.netherBrickOven.getDefaultState().withProperty(FACING, state.getValue(FACING)), 3);
		}

		if (tileEntity != null) {
			tileEntity.validate();
			world.setTileEntity(pos, tileEntity);
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int par3) {
		return Item.getItemFromBlock(RegisterBlocks.netherBrickOven);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack heldItem = player.getHeldItem(hand);

		if (!heldItem.isEmpty() && CapUtils.hasFluidHandler(heldItem)) {
			IFluidHandler lavaTank = CapUtils.getFluidHandler(world.getTileEntity(pos));
			if (Util.isEmptyFluidContainer(heldItem)) {
				FluidUtil.tryFillContainerAndStow(heldItem, lavaTank, CapUtils.getItemHandler(player), 1000, player, true);
			} else {
				FluidUtil.tryEmptyContainerAndStow(heldItem, lavaTank, CapUtils.getItemHandler(player), 1000, player, true);
			}
		} else
			player.openGui(ModMain.modInstance, BBGuiHandler.NETHERBRICKOVEN_ID, world, pos.getX(), pos.getY(),
					pos.getZ());
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityNetherBrickOven();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World world, BlockPos pos) {
		IFluidHandler tank = CapUtils.getFluidHandler(world.getTileEntity(pos));
		FluidStack containedLava = tank.drain(MAX_LAVA, false);
		if (containedLava == null)
			return 0;
		return MathHelper.floor(containedLava.amount / 500.0F);
	}
}
