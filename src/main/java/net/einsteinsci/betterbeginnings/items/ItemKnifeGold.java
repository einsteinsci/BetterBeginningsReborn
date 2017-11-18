package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;

public class ItemKnifeGold extends ItemKnife
{
    public ItemKnifeGold()
    {
        super(ToolMaterial.GOLD);
        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public String getName()
    {
        return "gold_knife";
    }
}
