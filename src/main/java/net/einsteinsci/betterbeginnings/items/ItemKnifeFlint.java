package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;

public class ItemKnifeFlint extends ItemKnife
{
	public ItemKnifeFlint()
	{
		super(ToolMaterial.WOOD);
		setCreativeTab(ModMain.tabBetterBeginnings);
	}

	@Override
	public String getName()
	{
		return "flint_knife";
	}
}
