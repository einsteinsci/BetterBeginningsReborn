package net.einsteinsci.betterbeginnings.client;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.items.ItemCharredMeat;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;

public class RegisterMetaItemRenders
{
	public static void init()
	{
		// region charred meats
		for (int i = 0; i < 6; i++)
		{
			registerItemIconWithMeta(RegisterItems.charredMeat, i, ItemCharredMeat.getSimpleUnlocalizedName(i));
		}

		ResourceLocation[] charredMeatNames = new ResourceLocation[6];
		for (int i = 0; i < 6; i++)
		{
			charredMeatNames[i] = new ResourceLocation(ModMain.MODID, ItemCharredMeat.getSimpleUnlocalizedName(i));
		}
		ModelLoader.registerItemVariants(RegisterItems.charredMeat, charredMeatNames);
		// endregion charred meats
	}

	public static void registerItemIcon(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	public static void registerItemIconWithMeta(Item item, int meta, String filename)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(ModMain.MODID, filename), "inventory"));
	}
}
