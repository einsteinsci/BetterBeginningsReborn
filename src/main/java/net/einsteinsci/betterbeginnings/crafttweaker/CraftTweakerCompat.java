package net.einsteinsci.betterbeginnings.crafttweaker;

import crafttweaker.CraftTweakerAPI;
//import net.einsteinsci.betterbeginnings.register.FuelRegistry;
public class CraftTweakerCompat 
{
	public static void register() 
	{
		CraftTweakerAPI.registerClass(KilnTweaker.class);
		CraftTweakerAPI.registerClass(OvenTweaker.class);
		CraftTweakerAPI.registerClass(SmelterTweaker.class);
		CraftTweakerAPI.registerClass(AdvancedCraftingTweaker.class);
		CraftTweakerAPI.registerClass(CampfireTweaker.class);
		//CraftTweakerAPI.registerClass(FuelTweaker.class);
		//MineTweakerAPI.registerClass(InfusionRepairTweaker.class);
		//MineTweakerAPI.registerClass(RockHammerTweaker.class);
	}
}
