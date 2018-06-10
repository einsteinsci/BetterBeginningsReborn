package net.einsteinsci.betterbeginnings.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import net.einsteinsci.betterbeginnings.crafttweaker.tweaker.*;

public class CraftTweakerPlugIn {

    public static void init() {
        CraftTweakerAPI.registerClass(SmelterTweaker.class);
        CraftTweakerAPI.registerClass(KilnTweaker.class);
        CraftTweakerAPI.registerClass(OvenTweaker.class);
        CraftTweakerAPI.registerClass(CampfireTweaker.class);
        CraftTweakerAPI.registerClass(AdvancedCraftingTweaker.class);
        //MineTweakerAPI.registerClass(InfusionRepairTweaker.class);
        //MineTweakerAPI.registerClass(RockHammerTweaker.class);
    }
}
