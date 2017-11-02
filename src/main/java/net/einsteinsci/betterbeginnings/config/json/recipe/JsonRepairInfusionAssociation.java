package net.einsteinsci.betterbeginnings.config.json.recipe;

import net.einsteinsci.betterbeginnings.config.json.JsonLoadedItemStack;
import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.einsteinsci.betterbeginnings.util.InfusionRepairUtil;
import net.minecraft.util.ResourceLocation;

public class JsonRepairInfusionAssociation
{
	private String enchantmentID;
	private JsonLoadedItemStack associatedItem;

	public JsonRepairInfusionAssociation(ResourceLocation enchID, RecipeElement stack)
	{
		enchantmentID = enchID.toString();

		if (stack instanceof OreRecipeElement)
		{
			associatedItem = JsonLoadedItemStack.makeOreDictionary(((OreRecipeElement) stack).getOreDictionaryEntry());
		}
		else
		{
			associatedItem = new JsonLoadedItemStack(((StackRecipeElement) stack).getStack());
		}
	}

	public void register()
	{
		if (associatedItem.isOreDictionary())
		{
			InfusionRepairUtil.registerEnchantment(new ResourceLocation(enchantmentID), new OreRecipeElement(associatedItem.getItemName()));
		}
		else
		{
			InfusionRepairUtil.registerEnchantment(new ResourceLocation(enchantmentID), new StackRecipeElement(associatedItem.getFirstItemStackOrNull()));
		}
	}

	public String getEnchantmentID()
	{
		return enchantmentID;
	}

	public JsonLoadedItemStack getAssociatedItem()
	{
		return associatedItem;
	}
}
