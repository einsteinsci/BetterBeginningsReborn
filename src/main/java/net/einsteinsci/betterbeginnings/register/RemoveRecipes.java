package net.einsteinsci.betterbeginnings.register;

import java.util.*;
import java.util.Map.Entry;

import org.apache.logging.log4j.Level;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryModifiable;

public class RemoveRecipes
{
	static List<ItemStack> customRemovedCraftingRecipes = new ArrayList<>();
	static List<ItemStack> customRemovedFurnaceRecipes = new ArrayList<>();

	public static void remove()
	{
		List<Item> removedRecipes = new ArrayList<>();

		// Be sure to get the correct quantity and damage

		// Replaced/Obsolete Items
		removedRecipes.add(Items.WOODEN_SWORD);

		if (BBConfig.moduleFurnaces)
		{
			removedRecipes.add(Item.getItemFromBlock(Blocks.FURNACE));
			removedRecipes.add(Items.FURNACE_MINECART);
		}

		removedRecipes.add(Item.getItemFromBlock(Blocks.CRAFTING_TABLE));

		if (BBConfig.removeWoodToolRecipes)
		{
			removedRecipes.add(Items.WOODEN_PICKAXE);
			removedRecipes.add(Items.WOODEN_AXE);
			removedRecipes.add(Items.WOODEN_SHOVEL);
			removedRecipes.add(Items.WOODEN_HOE);
		}

		// Advanced Crafting
		if (BBConfig.forceAdvancedCrafting)
		{
			if (BBConfig.advancedCraftingForLotsOfThings)
			{
				removedRecipes.add(Items.OAK_DOOR);
				removedRecipes.add(Items.SPRUCE_DOOR);
				removedRecipes.add(Items.BIRCH_DOOR);
				removedRecipes.add(Items.JUNGLE_DOOR);
				removedRecipes.add(Items.ACACIA_DOOR);
				removedRecipes.add(Items.DARK_OAK_DOOR);
				removedRecipes.add(Items.IRON_DOOR);
				removedRecipes.add(Item.getItemFromBlock(Blocks.OAK_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.SPRUCE_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.BIRCH_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.JUNGLE_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.ACACIA_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.DARK_OAK_FENCE_GATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.TRAPDOOR));
				removedRecipes.add(Item.getItemFromBlock(Blocks.CHEST));
				removedRecipes.add(Item.getItemFromBlock(Blocks.TRAPPED_CHEST));
				removedRecipes.add(Item.getItemFromBlock(Blocks.PISTON));
				removedRecipes.add(Item.getItemFromBlock(Blocks.TRIPWIRE_HOOK));
				removedRecipes.add(Item.getItemFromBlock(Blocks.DISPENSER));
				removedRecipes.add(Item.getItemFromBlock(Blocks.NOTEBLOCK));
				removedRecipes.add(Item.getItemFromBlock(Blocks.GOLDEN_RAIL));
				removedRecipes.add(Item.getItemFromBlock(Blocks.DETECTOR_RAIL));
				removedRecipes.add(Item.getItemFromBlock(Blocks.TNT));
				removedRecipes.add(Item.getItemFromBlock(Blocks.BOOKSHELF));
				removedRecipes.add(Item.getItemFromBlock(Blocks.LADDER));
				removedRecipes.add(Item.getItemFromBlock(Blocks.RAIL));
				removedRecipes.add(Item.getItemFromBlock(Blocks.ENCHANTING_TABLE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.BEACON));
				removedRecipes.add(Item.getItemFromBlock(Blocks.ANVIL));
				removedRecipes.add(Item.getItemFromBlock(Blocks.HOPPER));
				removedRecipes.add(Item.getItemFromBlock(Blocks.ACTIVATOR_RAIL));
				removedRecipes.add(Item.getItemFromBlock(Blocks.DROPPER));
				removedRecipes.add(Item.getItemFromBlock(Blocks.JUKEBOX));
				removedRecipes.add(Item.getItemFromBlock(Blocks.REDSTONE_LAMP));
				removedRecipes.add(Item.getItemFromBlock(Blocks.ENDER_CHEST));
				removedRecipes.add(Item.getItemFromBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE));
				removedRecipes.add(Item.getItemFromBlock(Blocks.DAYLIGHT_DETECTOR));
				removedRecipes.add(Item.getItemFromBlock(Blocks.IRON_TRAPDOOR));
				removedRecipes.add(Items.MINECART);
				removedRecipes.add(Items.COMPASS);
				removedRecipes.add(Items.CLOCK);
				removedRecipes.add(Items.BED);
				removedRecipes.add(Items.BREWING_STAND);
				removedRecipes.add(Items.CAULDRON);
				removedRecipes.add(Items.ITEM_FRAME);
				removedRecipes.add(Items.COMPARATOR);
				removedRecipes.add(Items.ARMOR_STAND);
			}

			removedRecipes.add(Items.BOW);
			removedRecipes.add(Items.FISHING_ROD);
			removedRecipes.add(Items.SHEARS);

			removedRecipes.add(Items.LEATHER_HELMET);
			removedRecipes.add(Items.LEATHER_CHESTPLATE);
			removedRecipes.add(Items.LEATHER_LEGGINGS);
			removedRecipes.add(Items.LEATHER_BOOTS);

			removedRecipes.add(Items.STONE_PICKAXE);
			removedRecipes.add(Items.STONE_SWORD);
			removedRecipes.add(Items.STONE_AXE);
			removedRecipes.add(Items.STONE_SHOVEL);
			removedRecipes.add(Items.STONE_HOE);

			removedRecipes.add(Items.IRON_PICKAXE);
			removedRecipes.add(Items.IRON_SWORD);
			removedRecipes.add(Items.IRON_AXE);
			removedRecipes.add(Items.IRON_SHOVEL);
			removedRecipes.add(Items.IRON_HOE);

			removedRecipes.add(Items.IRON_HELMET);
			removedRecipes.add(Items.IRON_CHESTPLATE);
			removedRecipes.add(Items.IRON_LEGGINGS);
			removedRecipes.add(Items.IRON_BOOTS);

			removedRecipes.add(Items.DIAMOND_SWORD);
			removedRecipes.add(Items.DIAMOND_PICKAXE);
			removedRecipes.add(Items.DIAMOND_AXE);
			removedRecipes.add(Items.DIAMOND_SHOVEL);
			removedRecipes.add(Items.DIAMOND_HOE);

			removedRecipes.add(Items.DIAMOND_HELMET);
			removedRecipes.add(Items.DIAMOND_CHESTPLATE);
			removedRecipes.add(Items.DIAMOND_LEGGINGS);
			removedRecipes.add(Items.DIAMOND_BOOTS);

			removedRecipes.add(Items.GOLDEN_PICKAXE);
			removedRecipes.add(Items.GOLDEN_SWORD);
			removedRecipes.add(Items.GOLDEN_AXE);
			removedRecipes.add(Items.GOLDEN_SHOVEL);
			removedRecipes.add(Items.GOLDEN_HOE);

			removedRecipes.add(Items.GOLDEN_HELMET);
			removedRecipes.add(Items.GOLDEN_CHESTPLATE);
			removedRecipes.add(Items.GOLDEN_LEGGINGS);
			removedRecipes.add(Items.GOLDEN_BOOTS);
		}

		// Food that should be cooked in ovens
		if (BBConfig.removeCraftedFoodRecipes && BBConfig.moduleFurnaces)
		{
			removedRecipes.add(Items.BREAD);
			removedRecipes.add(Items.CAKE);
			removedRecipes.add(Items.COOKIE);
			removedRecipes.add(Items.FERMENTED_SPIDER_EYE);
			removedRecipes.add(Items.GOLDEN_APPLE);
			removedRecipes.add(Items.MAGMA_CREAM);
			removedRecipes.add(Items.PUMPKIN_PIE);
			removedRecipes.add(Items.RABBIT_STEW);
		}

		for(Entry<ResourceLocation, IRecipe> entry : ForgeRegistries.RECIPES.getEntries())
		{
			IRecipe recipe = entry.getValue();
			ItemStack result = recipe.getRecipeOutput();

			if (result != null)
			{
				Item item = result.getItem();
				//TODO Figure out how to do this in 1.12
				/*if (item == Item.getItemFromBlock(Blocks.CRAFTING_TABLE) &&
					recipe.getRecipeSize() != 4)
				{
					continue;
				}*/

				if (item != null && removedRecipes.contains(item))
				{
					removeRecipe(entry.getKey());
					continue;
				}

				boolean removeMe = false;
				for (ItemStack customRemoved : customRemovedCraftingRecipes)
				{
					if (Util.areItemStacksEqualIgnoreSize(customRemoved, result))
					{
						LogUtil.logDebug(Level.INFO, "Custom removed crafting recipe for " + result.getDisplayName());

						removeMe = true;
						break;
					}
				}

				if (removeMe)
				{
					removeRecipe(entry.getKey());
				}
			}
		}
	}
	
	private static void removeRecipe(ResourceLocation identifier)
	{
		((IForgeRegistryModifiable<IRecipe>) ForgeRegistries.RECIPES).remove(identifier);
	}

	public static void removeFurnaceRecipes()
	{
		List<ItemStack> vanillaSmeltingOutputs = new ArrayList<>();

		vanillaSmeltingOutputs.add(new ItemStack(Items.BAKED_POTATO));
		vanillaSmeltingOutputs.add(new ItemStack(Items.BRICK));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COAL)); //charcoal, coal silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_BEEF));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_CHICKEN));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_FISH));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_PORKCHOP));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_RABBIT));
		vanillaSmeltingOutputs.add(new ItemStack(Items.COOKED_MUTTON));
		vanillaSmeltingOutputs.add(new ItemStack(Items.DIAMOND)); //silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Items.DYE)); //Cactus green, lapis silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Items.EMERALD)); //silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Items.GOLD_INGOT));
		vanillaSmeltingOutputs.add(new ItemStack(Items.IRON_INGOT));
		vanillaSmeltingOutputs.add(new ItemStack(Items.NETHERBRICK));
		vanillaSmeltingOutputs.add(new ItemStack(Items.QUARTZ)); //silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Items.REDSTONE)); //silk-touched
		vanillaSmeltingOutputs.add(new ItemStack(Blocks.GLASS));
		vanillaSmeltingOutputs.add(new ItemStack(Blocks.HARDENED_CLAY));
		vanillaSmeltingOutputs.add(new ItemStack(Blocks.SPONGE)); //1.8
		vanillaSmeltingOutputs.add(new ItemStack(Blocks.STONE));
		vanillaSmeltingOutputs.add(new ItemStack(Blocks.STONEBRICK)); //1.8

		if (BBConfig.removeSmeltingRecipes)
		{
			// Remove ALL THE THINGS!
			Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
			Iterator<Map.Entry<ItemStack, ItemStack>> iterator = recipes.entrySet().iterator();

			while (iterator.hasNext())
			{
				Map.Entry<ItemStack, ItemStack> entry = iterator.next();
				ItemStack result = (ItemStack)entry.getValue();
				boolean isVanilla = false;
				for (ItemStack vanilla : vanillaSmeltingOutputs)
				{
					if (vanilla.getItem() == result.getItem())
					{
						isVanilla = true;
						break;
					}
				}

				if (!BBConfig.removeOnlyVanillaSmeltingRecipes || isVanilla)
				{
					iterator.remove();
				}
			}
		}

		Map<ItemStack, ItemStack> recipes = FurnaceRecipes.instance().getSmeltingList();
		Iterator<Map.Entry<ItemStack, ItemStack>> iterator = recipes.entrySet().iterator();

		while (iterator.hasNext())
		{
			Map.Entry<ItemStack, ItemStack> entry = iterator.next();
			ItemStack result = (ItemStack)entry.getValue();
			boolean removeMe = false;
			for (ItemStack customRemoved : customRemovedFurnaceRecipes)
			{
				if (Util.areItemStacksEqualIgnoreSize(customRemoved, result))
				{
					removeMe = true;
					break;
				}
			}

			if (removeMe)
			{
				iterator.remove();
			}
		}
	}

	public static List<ItemStack> getCustomRemovedCraftingRecipes()
	{
		return customRemovedCraftingRecipes;
	}

	public static List<ItemStack> getCustomRemovedFurnaceRecipes()
	{
		return customRemovedFurnaceRecipes;
	}
}