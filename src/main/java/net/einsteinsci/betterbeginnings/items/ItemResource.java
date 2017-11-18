package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.item.Item;

public class ItemResource extends Item implements IBBName
{	
    private String name;
    
    public ItemResource()
    {
        setCreativeTab(ModMain.tabBetterBeginnings);
    }
    
    @Override
    public String getName()
    {
        return name;
    }
    
    public ItemResource setName(String name)
    {
        this.name = name;
        return this;
    }
}
