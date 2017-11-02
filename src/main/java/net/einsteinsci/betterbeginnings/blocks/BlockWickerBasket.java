package net.einsteinsci.betterbeginnings.blocks;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.gui.BBGuiHandler;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;

public class BlockWickerBasket extends Block implements IBBName 
{
    public BlockWickerBasket() 
    {
	super(Material.WOOD);
	setCreativeTab(ModMain.tabBetterBeginnings);
	setHardness(1.25F);
	setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
	    EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) 
    {
	player.openGui(ModMain.modInstance, BBGuiHandler.WICKER_BASKET_ID, world, pos.getX(), pos.getY(), pos.getZ());
	return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer,
	    ItemStack stack)
    {
	if (stack.hasDisplayName())
	{
	    TileEntity teBase = world.getTileEntity(pos);
	    if (teBase instanceof TileEntityWickerBasket)
	    {
		TileEntityWickerBasket tileEntity = (TileEntityWickerBasket)teBase;
		tileEntity.setBlockName(stack.getDisplayName());
	    }
	}
    }

    // Drop stuff everywhere
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
	if(!(world.getBlockState(pos).getBlock() instanceof BlockWickerBasket))
	{
	    TileEntity tileentity = world.getTileEntity(pos);

	    if (tileentity instanceof TileEntityWickerBasket)
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
	return new TileEntityWickerBasket();
    }
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public String getName() 
    {
	return "wicker_basket";
    }
}
