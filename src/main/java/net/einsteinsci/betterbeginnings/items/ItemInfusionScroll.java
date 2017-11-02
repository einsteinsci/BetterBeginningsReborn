package net.einsteinsci.betterbeginnings.items;

import java.util.Stack;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityInfusionRepair;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemInfusionScroll extends Item implements IBBName
{
    public ItemInfusionScroll()
    {
	super();
	setCreativeTab(ModMain.tabBetterBeginnings);
	setMaxStackSize(1);
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
	return true;
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos,
	    EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) 
    {
	TileEntity te = world.getTileEntity(pos);
	if(te instanceof TileEntityInfusionRepair)
	{
	    Stack<RecipeElement> pendingIngredients = ((TileEntityInfusionRepair)te).getPendingIngredients();
	    if(!pendingIngredients.isEmpty())
	    {
		RecipeElement element = pendingIngredients.peek();
		player.sendMessage(new TextComponentString(element.toFriendlyString()));
	    }
	}
	return EnumActionResult.SUCCESS;
    }

    @Override
    public String getName()
    {
	return "infusion_scroll";
    }
}
