package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class ItemBonePickaxe extends ItemPickaxe implements IBBName
{
	public ItemBonePickaxe()
	{
		super(ToolMaterial.WOOD);
		
		setCreativeTab(ModMain.tabBetterBeginnings);
	}

	@Override
	public String getName()
	{
		return "bone_pickaxe";
	}

	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockstate)
	{
		return toolMaterial.getHarvestLevel();
	}

	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		Set<String> res = new HashSet<>();

		res.add("pickaxe");

		return res;
	}
}
