package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.potion.PotionEffect;

public class ItemMarshmallowCooked extends ItemFood implements IBBName
{
    public ItemMarshmallowCooked()
    {
        super(5, 6.0f, false);
        setCreativeTab(ModMain.tabBetterBeginnings);
        setPotionEffect(new PotionEffect(MobEffects.SPEED, 15, 0), 0.8f);
    }

    @Override
    public String getName()
    {
        return "marshmallow_cooked";
    }
}
