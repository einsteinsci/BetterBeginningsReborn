package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.entity.EntityMinecartKiln;
import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class RegisterEntities 
{
	private static int nextId = 0;

	public static void register()
	{
		registerEntity(EntityThrownKnife.class, "thrownKnife");
		registerEntity(EntityMinecartKiln.class, "kilnCart");
	}

	private static void registerEntity(Class<? extends Entity> entityClass, String entityName)
	{
		EntityRegistry.registerModEntity(new ResourceLocation(ModMain.MODID, entityName), entityClass, entityName
				, nextId++, ModMain.modInstance, 80, 1, true);
	}
}
