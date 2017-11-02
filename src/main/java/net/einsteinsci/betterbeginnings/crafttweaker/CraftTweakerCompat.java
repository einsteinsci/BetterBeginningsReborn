package net.einsteinsci.betterbeginnings.crafttweaker;

import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.MineTweakerImplementationAPI.ReloadEvent;
import minetweaker.util.IEventHandler;
import net.einsteinsci.betterbeginnings.register.FuelRegistry;
public class CraftTweakerCompat 
{
	public static void register() 
	{
		MineTweakerAPI.registerClass(KilnTweaker.class);
		MineTweakerAPI.registerClass(OvenTweaker.class);
		MineTweakerAPI.registerClass(SmelterTweaker.class);
		MineTweakerAPI.registerClass(AdvancedCraftingTweaker.class);
		MineTweakerAPI.registerClass(CampfireTweaker.class);
		MineTweakerAPI.registerClass(FuelTweaker.class);
		MineTweakerImplementationAPI.onReloadEvent(new IEventHandler<ReloadEvent>() 
		{
		    @Override
		    public void handle(ReloadEvent paramT) 
		    {
			FuelRegistry.reset();
		    }
		});
		//MineTweakerAPI.registerClass(InfusionRepairTweaker.class);
		//MineTweakerAPI.registerClass(RockHammerTweaker.class);
	}
}
