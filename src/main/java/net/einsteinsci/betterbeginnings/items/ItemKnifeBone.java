package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;

public class ItemKnifeBone extends ItemKnife
{
    public ItemKnifeBone()
    {
        super(ToolMaterial.STONE);
        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public String getName()
    {
        return "bone_knife";
    }
}
