package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.item.ItemSword;

//Replaces Vanilla Wood Sword
public class ItemNoobWoodSword extends ItemSword implements IBBName
{
    public ItemNoobWoodSword(ToolMaterial material)
    {
        super(material);
    }

    @Override
    public String getName()
    {
        return "noob_wood_sword";
    }
}
