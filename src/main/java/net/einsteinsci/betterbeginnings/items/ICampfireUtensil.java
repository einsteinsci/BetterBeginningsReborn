package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.item.ItemStack;

public interface ICampfireUtensil extends IBBName
{
	float getCampfireSpeedModifier();

	boolean doCookingDamage(ItemStack stack);
}
