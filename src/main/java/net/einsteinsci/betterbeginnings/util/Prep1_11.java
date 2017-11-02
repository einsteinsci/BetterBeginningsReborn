package net.einsteinsci.betterbeginnings.util;

import net.minecraft.item.ItemStack;

public class Prep1_11
{
    //Prep for 1.11 ItemStack changes
    public static boolean isEmpty(ItemStack stack)
    {
	return stack == null;
    }

    public static boolean isValid(ItemStack stack)
    {
	return stack != null;
    }

    public static ItemStack getEmptyStack()
    {
	return null;
    }
}
