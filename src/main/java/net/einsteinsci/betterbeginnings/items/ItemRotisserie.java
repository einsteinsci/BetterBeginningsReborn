package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemRotisserie extends Item implements ICampfireUtensil
{
    public ItemRotisserie()
    {
        super();
        setMaxDamage(4);
        setMaxStackSize(1);
        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public float getCampfireSpeedModifier()
    {
        return 0.5f;
    }

    @Override
    public boolean doCookingDamage(ItemStack stack)
    {
        int damage = stack.getItemDamage();
        stack.setItemDamage(damage + 1);

        return stack.getItemDamage() >= stack.getMaxDamage();
    }

    @Override
    public String getName()
    {
        return "rotisserie";
    }
}
