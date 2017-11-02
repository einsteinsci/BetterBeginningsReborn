package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;

public class ItemKnifeIron extends ItemKnife
{
	public ItemKnifeIron()
	{
		super(ToolMaterial.IRON);
		setCreativeTab(ModMain.tabBetterBeginnings);
	}

	@Override
	public String getName()
	{
		return "iron_knife";
	}
}
