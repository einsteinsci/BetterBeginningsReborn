package net.einsteinsci.betterbeginnings.register.recipe;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.minecraft.util.JsonUtils;
import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

public class ConditionFactory implements IConditionFactory
{

	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json)
	{
		boolean value = JsonUtils.getBoolean(json, "value", true);
		String key = JsonUtils.getString(json, "type");

		if (key.equals(ModMain.MODID + ":make_chain_armor"))
		{
			return () -> BBConfig.canMakeChainArmor == value;
		} else if (key.equals(ModMain.MODID + ":module_furnace_enabled"))
		{
			return () -> BBConfig.moduleFurnaces == value;
		} else if (key.equals(ModMain.MODID + ":advanced_crafting_enabled"))
		{
			return () -> BBConfig.moduleAdvancedCrafting == value;
		} else if (key.equals(ModMain.MODID + ":campfire_enabled"))
		{
			return () -> BBConfig.moduleCampfire == value;
		} else if (key.equals(ModMain.MODID + ":infusion_enabled"))
		{
			return () -> BBConfig.moduleInfusionRepair == value;
		}
		return null;
	}

}
