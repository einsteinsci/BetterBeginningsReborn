package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.entity.EntityMinecartKiln;
import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.*;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = ModMain.MODID)
public class RegisterEntities 
{
	private static int nextId = 0;

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> e)
	{
		registerEntity(e.getRegistry(), EntityThrownKnife.class, "thrownKnife", 64, 10, true);
		registerEntity(e.getRegistry(), EntityMinecartKiln.class, "kilnCart", 80, 3, true);
	}

	private static void registerEntity(IForgeRegistry<EntityEntry> entityRegistry, Class<? extends Entity> entityClass, String entityName
			, int range, int updateFrequency, boolean sendVelocityUpdates)
	{
		entityRegistry.register(
		EntityEntryBuilder.create()
			.entity(entityClass)
			.id(new ResourceLocation(ModMain.MODID, entityName), nextId++)
			.name(entityName)
			.tracker(range, updateFrequency, sendVelocityUpdates)
			.build());
	}
}
