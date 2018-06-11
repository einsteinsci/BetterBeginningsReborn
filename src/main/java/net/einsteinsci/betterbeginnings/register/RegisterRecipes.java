package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.items.ItemCharredMeat;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.einsteinsci.betterbeginnings.register.recipe.BrickOvenRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.InfusionRepairRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.elements.OreRecipeElement;
import net.einsteinsci.betterbeginnings.register.recipe.elements.StackRecipeElement;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterRecipes {
	public static void register() {
		addKilnRecipes();
		addBrickOvenRecipes();
		addSmelterRecipes();
		addCampfireRecipes();
		addInfusionRepairRecipes();
		addAdvancedRecipes();
	}

	private static void addKilnRecipes() {
		KilnRecipeHandler.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.35f);
		KilnRecipeHandler.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.1f);
		KilnRecipeHandler.addRecipe(Blocks.COBBLESTONE, new ItemStack(Blocks.STONE), 0.1f);
		KilnRecipeHandler.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f);
		KilnRecipeHandler.addRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, 2), 0.1f);
		KilnRecipeHandler.addRecipe("logWood", new ItemStack(Items.COAL, 1, 1), 0.15f);
		KilnRecipeHandler.addRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1f);
		KilnRecipeHandler.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.1f);
		KilnRecipeHandler.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1f);

		KilnRecipeHandler.addRecipe(Items.BEEF, new ItemStack(RegisterItems.charredMeat), 0.1f);
		KilnRecipeHandler.addRecipe(Items.PORKCHOP, new ItemStack(RegisterItems.charredMeat), 0.1f);
		KilnRecipeHandler.addRecipe(Items.CHICKEN, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_CHICKEN),
				0.1f);
		KilnRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 0),
				new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_FISH), 0.1f);
		KilnRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 1),
				new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_FISH), 0.1f);
		KilnRecipeHandler.addRecipe(Items.RABBIT, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_RABBIT),
				0.1f);
		KilnRecipeHandler.addRecipe(Items.MUTTON, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_MUTTON),
				0.1f);
	}

	private static void addBrickOvenRecipes() {
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.GOLDEN_APPLE), "GGG", "GAG", "GGG", 'G', Items.GOLD_INGOT,
				'A', Items.APPLE);
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), "###", "#A#", "###", '#',
				Blocks.GOLD_BLOCK, 'A', Items.APPLE);
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.CAKE), "MMM", "SES", "WWW", 'M', Items.MILK_BUCKET, 'S',
				Items.SUGAR, 'E', Items.EGG, 'W', "cropWheat");
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.BREAD, 2), "WWW", 'W', "cropWheat");
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.COOKIE, 8), "WCW", 'W', "cropWheat", 'C',
				new ItemStack(Items.DYE, 1, 3)); // Cocoa bean
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.RABBIT_STEW), " R ", "CPM", " B ", 'R', Items.COOKED_RABBIT,
				'C', "cropCarrot", 'P', "cropPotato", 'M', Blocks.BROWN_MUSHROOM, 'B', Items.BOWL);
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(Items.RABBIT_STEW), " R ", "CPM", " B ", 'R', Items.COOKED_RABBIT,
				'C', "cropCarrot", 'P', "cropPotato", 'M', Blocks.RED_MUSHROOM, 'B', Items.BOWL);
		BrickOvenRecipeHandler.addShapedRecipe(new ItemStack(RegisterItems.marshmallow, 3), " S ", "SSS", " S ", 'S',
				Items.SUGAR);

		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.MUSHROOM_STEW), Blocks.BROWN_MUSHROOM,
				Blocks.RED_MUSHROOM, Items.BOWL);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_BEEF), Items.BEEF);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_PORKCHOP), Items.PORKCHOP);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_CHICKEN), Items.CHICKEN);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_FISH), Items.FISH);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_FISH, 1, 1), new ItemStack(Items.FISH, 1, 1));
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_RABBIT), Items.RABBIT);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.COOKED_MUTTON), Items.MUTTON);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.BAKED_POTATO), "cropPotato");
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), Items.EGG, Items.SUGAR, Blocks.PUMPKIN);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), Items.SPIDER_EYE, Items.SUGAR,
				Blocks.BROWN_MUSHROOM);
		BrickOvenRecipeHandler.addShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), Items.SLIME_BALL, Items.BLAZE_POWDER);
	}

	private static void addSmelterRecipes() {
		// Vanilla Ore Recipes (keep the result vanilla to prevent weirdness)
		SmelterRecipeHandler.addRecipe("oreIron", new ItemStack(Items.IRON_INGOT), 0.7f, 1, 1);
		SmelterRecipeHandler.addRecipe("oreGold", new ItemStack(Items.GOLD_INGOT), 1.0f, 2, 1);

		// Modded Ore Recipes
		RegisterHelper.registerSmelterOreRecipe("oreCopper", "ingotCopper", 0.6f, 1, 1);
		RegisterHelper.registerSmelterOreRecipe("oreTin", "ingotTin", 0.6f, 1, 1);
		RegisterHelper.registerSmelterOreRecipe("oreAluminum", "ingotAluminum", 0.8f, 1, 1);
		RegisterHelper.registerSmelterOreRecipe("oreSilver", "ingotSilver", 1.0f, 1, 1);
		RegisterHelper.registerSmelterOreRecipe("oreLead", "ingotLead", 0.6f, 1, 1);
		RegisterHelper.registerSmelterOreRecipe("orePlatinum", "ingotPlatinum", 1.0f, 2, 1);
		RegisterHelper.registerSmelterOreRecipe("oreNickel", "ingotNickel", 0.8f, 1, 1);

		// Recipes that might be better suited in Kiln only
		if (BBConfig.canSmelterDoKilnStuff) {
			SmelterRecipeHandler.addRecipe(new ItemStack(Blocks.SAND, 1, 0), new ItemStack(Blocks.GLASS), 0.1f, 1, 0);
			SmelterRecipeHandler.addRecipe(new ItemStack(Blocks.SAND, 1, 1), new ItemStack(Blocks.STAINED_GLASS, 1, 1), 0.1f,
					1, 0); // Red sand makes orange stained glass.
			SmelterRecipeHandler.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.25f, 1, 1);
			SmelterRecipeHandler.addRecipe(Blocks.STONEBRICK, new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f, 1, 0);

			SmelterRecipeHandler.addRecipe("cobblestone", new ItemStack(Blocks.STONE), 0.1f, 0, 0);
			SmelterRecipeHandler.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3f, 0, 0);
			SmelterRecipeHandler.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35f, 0, 0);
			SmelterRecipeHandler.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.1f, 0, 0);
			SmelterRecipeHandler.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONEBRICK, 1, 2),
					0.1f, 0, 0);
		}

		// Silk touch recipes
		SmelterRecipeHandler.addRecipe("oreCoal", new ItemStack(Items.COAL, 1), 0.25f, 2, 1);
		SmelterRecipeHandler.addRecipe("oreQuartz", new ItemStack(Items.QUARTZ, 2), 0.4f, 2, 2);
		SmelterRecipeHandler.addRecipe("oreLapis", new ItemStack(Items.DYE, 8, 4), 0.5f, 2, 3);
		SmelterRecipeHandler.addRecipe("oreRedstone", new ItemStack(Items.REDSTONE, 4), 0.8f, 2, 2);
		SmelterRecipeHandler.addRecipe("oreDiamond", new ItemStack(Items.DIAMOND, 1), 1.0f, 3, 1);
		SmelterRecipeHandler.addRecipe("oreEmerald", new ItemStack(Items.EMERALD, 1), 1.0f, 3, 1);

		// Silk touch recipes (modded)
		RegisterHelper.registerSmelterOreRecipe("oreRuby", "gemRuby", 0.8f, 2, 1);
		RegisterHelper.registerSmelterOreRecipe("oreSapphire", "gemSapphire", 0.8f, 2, 1);
		RegisterHelper.registerSmelterOreRecipe("oreOlivine", "gemOlivine", 0.8f, 2, 1);
	}

	private static void addCampfireRecipes() {
		CampfireRecipeHandler.addRecipe("logWood", new ItemStack(Items.COAL, 1, 1), 0.15f, false);
		CampfireRecipeHandler.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.1f, false);

		CampfireRecipeHandler.addRecipe(RegisterItems.roastingStickRawMallow,
				new ItemStack(RegisterItems.roastingStickCookedMallow), 0.5f, false);

		CampfireRecipeHandler.addRecipe(Items.BEEF, new ItemStack(RegisterItems.charredMeat), 0.1f, false);
		CampfireRecipeHandler.addRecipe(Items.PORKCHOP, new ItemStack(RegisterItems.charredMeat), 0.1f, false);
		CampfireRecipeHandler.addRecipe(Items.CHICKEN,
				new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_CHICKEN), 0.1f, false);
		CampfireRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 0),
				new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_FISH), 0.1f, false);
		CampfireRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 1),
				new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_FISH), 0.1f, false);
		CampfireRecipeHandler.addRecipe(Items.RABBIT, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_RABBIT),
				0.1f, false);
		CampfireRecipeHandler.addRecipe(Items.MUTTON, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_MUTTON),
				0.1f, false);

		if (BBConfig.canCampfireDoAllKilnStuff) {
			CampfireRecipeHandler.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.35f, false);
			CampfireRecipeHandler.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.1f, false);
			CampfireRecipeHandler.addRecipe("cobblestone", new ItemStack(Blocks.STONE), 0.1f, false);
			CampfireRecipeHandler.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONEBRICK, 1, 2),
					0.1f, false);
			CampfireRecipeHandler.addRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, 2), 0.1f, false);
			CampfireRecipeHandler.addRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1f, false);
			CampfireRecipeHandler.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1f, false);
		}

		CampfireRecipeHandler.addRecipe(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.1f, true);
		CampfireRecipeHandler.addRecipe(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.1f, true);
		CampfireRecipeHandler.addRecipe(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.1f, true);
		CampfireRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.COOKED_FISH, 1, 0), 0.1f, true);
		CampfireRecipeHandler.addRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), 0.1f, true);
		CampfireRecipeHandler.addRecipe(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.1f, true);
		CampfireRecipeHandler.addRecipe(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.1f, true);
	}

	private static void addInfusionRepairRecipes() {
		//Armour Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.PROTECTION, new StackRecipeElement(Blocks.IRON_BARS, 2));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FIRE_PROTECTION, new StackRecipeElement(Items.BUCKET));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FEATHER_FALLING, new StackRecipeElement(Items.FEATHER, 2));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.BLAST_PROTECTION, new OreRecipeElement("cobblestone", 8));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.PROJECTILE_PROTECTION, new StackRecipeElement(Items.SNOWBALL, 4));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.RESPIRATION, new StackRecipeElement(Items.GLASS_BOTTLE));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.AQUA_AFFINITY, new StackRecipeElement(Blocks.CLAY));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.THORNS, new StackRecipeElement(Blocks.CACTUS, 4));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.DEPTH_STRIDER, new StackRecipeElement(Blocks.PRISMARINE));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FROST_WALKER, new StackRecipeElement(Blocks.ICE, 4));
		//TODO1.11 Curse of Binding
		//Sword Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.SHARPNESS, new OreRecipeElement("gemQuartz", 4));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.SMITE, new StackRecipeElement(Blocks.SOUL_SAND, 2));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.BANE_OF_ARTHROPODS, new StackRecipeElement(Items.FERMENTED_SPIDER_EYE));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.KNOCKBACK, new StackRecipeElement(Blocks.PISTON));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FIRE_ASPECT, new StackRecipeElement(Items.BLAZE_POWDER, 2));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.LOOTING, new OreRecipeElement("ingotGold", 2));
		//Pick Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.EFFICIENCY, new StackRecipeElement(Items.SUGAR, 4));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.SILK_TOUCH, new StackRecipeElement(RegisterItems.cloth, 8));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.UNBREAKING, new StackRecipeElement(Blocks.OBSIDIAN));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FORTUNE, new OreRecipeElement("gemLapis", 4));
		//Bow Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.POWER, new StackRecipeElement(RegisterItems.leatherStrip));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.PUNCH, new StackRecipeElement(Items.GUNPOWDER));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.FLAME, new StackRecipeElement(Items.FIRE_CHARGE));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.INFINITY, new StackRecipeElement(Items.ARROW, 16));
		//Fishing Rod Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.LUCK_OF_THE_SEA, new StackRecipeElement(Blocks.WATERLILY));
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.LURE, new StackRecipeElement(Items.FISH, 1, 3));
		//General Enchants
		InfusionRepairRecipeHandler.registerEnchantment(Enchantments.MENDING, new StackRecipeElement(Items.SLIME_BALL, 2));
		//TODO1.11 Curse of Vanishing
	}
	
	private static void addAdvancedRecipes() {
		if (!BBConfig.moduleAdvancedCrafting)
			return;
		// region advancedCraftingForLotsOfThings
		if (BBConfig.advancedCraftingForLotsOfThings) {
			// Wooden Doors
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.OAK_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 0));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.SPRUCE_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 1));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BIRCH_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 2));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.JUNGLE_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 3));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.ACACIA_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 4));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DARK_OAK_DOOR, 3),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "##", "##", '#',
					new ItemStack(Blocks.PLANKS, 1, 5));

			// Iron Door
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_DOOR), new Object[] { "nuggetIron", 2 },
					"II", "II", "II", 'I', "ingotIron");
			// Fence Gates
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.OAK_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 0));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.SPRUCE_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 1));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.BIRCH_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 2));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.JUNGLE_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 3));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.ACACIA_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 4));
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE_GATE),
					new Object[] { "itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2) }, "/#/", "/#/", '/',
					"stickWood", '#', new ItemStack(Blocks.PLANKS, 1, 5));

			// Trapdoor
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TRAPDOOR),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "###", "###", '#', "plankWood");
			// Chest. Yep, you need iron before you can make a chest. If you absolutely must
			// store stuff before you have
			// iron, use your kiln (provided it isn't kiln-able ;D).
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.CHEST),
					new Object[] { "nuggetIron", 3, new ItemStack(RegisterItems.leatherStrip, 1) }, "###", "# #", "###",
					'#', "plankWood");

			if (BBConfig.anyStringForTraps) {
				// Trapped Chest
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TRAPPED_CHEST),
						new Object[] { "itemString", 2, "dustRedstone", 2 }, "C", "H", 'C', Blocks.CHEST, 'H',
						Blocks.TRIPWIRE_HOOK);
				// Tripwire Hook
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK),
						new Object[] { "itemString", 1, "dustRedstone", 1 }, "I", "/", "#", 'I', "ingotIron", '/',
						"stickWood", '#', "plankWood");
			} else {
				// Trapped Chest
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TRAPPED_CHEST),
						new Object[] { new ItemStack(RegisterItems.thread, 2), "dustRedstone", 2 }, "C", "H", 'C',
						Blocks.CHEST, 'H', Blocks.TRIPWIRE_HOOK);
				// Tripwire Hook
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK),
						new Object[] { new ItemStack(RegisterItems.thread, 1), "dustRedstone", 1 }, "I", "/", "#", 'I',
						"ingotIron", '/', "stickWood", '#', "plankWood");
			}
			// Piston
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.PISTON), new Object[] { "nuggetIron", 2 },
					"###", "CIC", "CRC", '#', "plankWood", 'I', "ingotIron", 'C', "cobblestone", 'R', "dustRedstone");
			// Dispenser
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.DISPENSER), new Object[] { "nuggetIron", 2 },
					"###", "#B#", "#R#", '#', "cobblestone", 'B', new ItemStack(Items.BOW, 1, 0), 'R', "dustRedstone");
			// Note Block
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.NOTEBLOCK), new Object[] { "itemString", 2 },
					"###", "#R#", "###", '#', "plankWood", 'R', "dustRedstone");
			// Gold Rail
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.GOLDEN_RAIL, 6),
					new Object[] { "nuggetGold", 2 }, "G G", "G/G", "GRG", 'G', "ingotGold", '/', "stickWood", 'R',
					"dustRedstone");
			// Detector Rail
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.DETECTOR_RAIL, 6),
					new Object[] { "nuggetIron", 2, "stickWood", 4 }, "I I", "I_I", "IRI", 'I', "ingotIron", '_',
					Blocks.STONE_PRESSURE_PLATE, 'R', "dustRedstone");
			// TNT
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.TNT, 2), new Object[] { "itemString", 3 },
					"G#G", "#G#", "G#G", 'G', Items.GUNPOWDER, '#', Blocks.SAND);
			// Bookshelf
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.BOOKSHELF), new Object[] { "nuggetIron", 1 },
					"###", "BBB", "###", '#', "plankWood", 'B', Items.BOOK);
			// Ladder
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.LADDER, 4), new Object[] { "itemString", 1 },
					"/ /", "///", "/ /", '/', "stickWood");
			// Rail
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.RAIL, 16), new Object[] { "nuggetIron", 1 },
					"I I", "I/I", "I I", 'I', "ingotIron", '/', "stickWood");
			// Enchanting Table
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.ENCHANTING_TABLE),
					new Object[] { new ItemStack(Items.LEATHER), "dyeRed", 2, "gemLapis", 4 }, " B ", "D#D", "###", 'B',
					Items.BOOK, 'D', "gemDiamond", '#', Blocks.OBSIDIAN);
			// Beacon
			ItemStack awkwardPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM),
					PotionTypes.AWKWARD);
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.BEACON),
					new Object[] { "blockDiamond", 1, new ItemStack(Items.BLAZE_ROD, 4), awkwardPotion }, // Awkward
																											// potion
					"+++", "+S+", "###", '+', "blockGlassColorless", 'S', Items.NETHER_STAR, '#', Blocks.OBSIDIAN);
			// Anvil
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.ANVIL), new Object[] { "nuggetIron", 4 },
					"###", " I ", "III", '#', "blockIron", 'I', "ingotIron");
			// Hopper
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.HOPPER, 4),
					new Object[] { "nuggetIron", 2, new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1) }, "I I", "I#I",
					" I ", 'I', "ingotIron", '#', Blocks.CHEST);
			// Activator Rail
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL, 6),
					new Object[] { "nuggetIron", 4 }, "I/I", "IiI", "I/I", 'I', "ingotIron", '/', "stickWood", 'i',
					Blocks.REDSTONE_TORCH);
			// Dropper
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.DROPPER), new Object[] { "nuggetIron", 2 },
					"###", "# #", "#R#", '#', "cobblestone", 'R', "dustRedstone");
			// Minecart
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.MINECART),
					new Object[] { "nuggetIron", 2, new ItemStack(RegisterItems.leatherStrip, 1) }, "I I", "III", 'I',
					"ingotIron");
			// Compass
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.COMPASS),
					new Object[] { "nuggetIron", 3, new ItemStack(Items.POTIONITEM, 1, 0) }, // Water Bottle
					" I ", "IRI", " I ", 'I', "ingotIron", 'R', "dustRedstone");
			// Clock
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.CLOCK),
					new Object[] { "nuggetGold", 3, "dyeBlack", 1 }, " G ", "GRG", " G ", 'G', "ingotGold", 'R',
					"dustRedstone");
			// Bed
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BED), new Object[] { "nuggetIron", 2 }, "***",
					"###", '*', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE), '#', "plankWood");
			// Brewing Stand
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BREWING_STAND),
					new Object[] { "nuggetGold", 1, "nuggetIron", 3 }, " / ", "###", '/', Items.BLAZE_ROD, '#',
					"cobblestone");
			// Cauldron
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.CAULDRON), new Object[] { "nuggetIron", 3 },
					"I I", "I I", "III", 'I', "ingotIron");

			// Jukebox
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.JUKEBOX),
					new Object[] { "nuggetGold", 1, "itemString", 2 }, "###", "#D#", "###", '#', "plankWood", 'D',
					"gemDiamond");

			// Redstone Lamp
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.REDSTONE_LAMP),
					new Object[] { "dustRedstone", 2 }, " R ", "R#R", " R ", 'R', "dustRedstone", '#', "glowstone");

			// Ender Chest
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.ENDER_CHEST),
					new Object[] { new ItemStack(Items.BLAZE_POWDER, 4) }, "###", "#E#", "###", '#', Blocks.OBSIDIAN,
					'E', Items.ENDER_EYE);

			// Weighted Pressure Plate (Iron)
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE),
					new Object[] { "dustRedstone", 1 }, "II", 'I', "ingotIron");

			// Weighted Pressure Plate (Gold)
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE),
					new Object[] { "dustRedstone", 1 }, "GG", 'G', "ingotGold");

			// Daylight Sensor
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR),
					new Object[] { "dustRedstone", 2 }, "###", "QQQ", "---", '#', "blockGlassColorless", 'Q',
					"gemQuartz", '-', Blocks.WOODEN_SLAB);

			// Iron Trapdoor
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Blocks.IRON_TRAPDOOR),
					new Object[] { "nuggetIron", 2 }, "II", "II", 'I', "ingotIron");

			// Item Frame
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.ITEM_FRAME), new Object[] { "itemString", 1 },
					"///", "/L/", "///", '/', "stickWood", 'L', Items.LEATHER);

			// Comparator
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.COMPARATOR),
					new Object[] { "dustRedstone", 1 }, " i ", "iQi", "###", 'i', Blocks.REDSTONE_TORCH, 'Q',
					Items.QUARTZ, '#', "stone");

			// Armor Stand
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.ARMOR_STAND), new Object[] { "nuggetIron", 2 },
					"///", " / ", "/-/", '/', "stickWood", '-', new ItemStack(Blocks.STONE_SLAB, 1, 0));
		}
		// endregion

		// Fishing rod
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.FISHING_ROD),
				new Object[] { new ItemStack(Items.IRON_NUGGET) }, "  /", " /s", "/ s", '/', "stickWood", 's',
				"itemString");

		// Shears
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.SHEARS), new Object[] { "nuggetIron", 1 }, " I",
				"I ", 'I', "ingotIron");

		// region Tools & Armor

		// Leather armor
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.LEATHER_HELMET),
				new Object[] { "itemString", 3, new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE) }, "LLL",
				"L L", 'L', Items.LEATHER);
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.LEATHER_CHESTPLATE),
				new Object[] { "itemString", 2, new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE) }, "L L",
				"LLL", "LLL", 'L', Items.LEATHER);
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.LEATHER_LEGGINGS),
				new Object[] { "itemString", 4, new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) }, "LLL",
				"L L", "L L", 'L', Items.LEATHER);
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.LEATHER_BOOTS),
				new Object[] { "itemString", 4, new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE) }, "L L",
				"L L", 'L', Items.LEATHER);

		if (BBConfig.allowStringAsToolBinding) {
			// Stone
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_PICKAXE),
					new Object[] { "itemStringTough", 4 }, "###", " / ", " / ", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_SWORD),
					new Object[] { "itemStringTough", 4 }, "#", "#", "/", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_SHOVEL),
					new Object[] { "itemStringTough", 2 }, "#", "/", "/", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_AXE),
					new Object[] { "itemStringTough", 4 }, "##", "#/", " /", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_AXE), true,
					new Object[] { "itemStringTough", 4 }, "##", "/#", "/ ", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_HOE),
					new Object[] { "itemStringTough", 2 }, "##", " /", " /", '#', "stone", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_HOE), true,
					new Object[] { "itemStringTough", 2 }, "##", "/ ", "/ ", '#', "stone", '/', "stickWood");

			// Iron
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_PICKAXE),
					new Object[] { "itemStringTough", 4 }, "III", " / ", " / ", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_SWORD),
					new Object[] { "itemStringTough", 4 }, "I", "I", "/", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_SHOVEL),
					new Object[] { "itemStringTough", 2 }, "I", "/", "/", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_AXE),
					new Object[] { "itemStringTough", 4 }, "II", "I/", " /", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_AXE), true,
					new Object[] { "itemStringTough", 4 }, "II", "/I", "/ ", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_HOE),
					new Object[] { "itemStringTough", 2 }, "II", " /", " /", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_HOE), true,
					new Object[] { "itemStringTough", 2 }, "II", "/ ", "/ ", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife), true,
					new Object[] { "itemStringTough", 4 }, " I", "/ ", 'I', "ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife),
					new Object[] { "itemStringTough", 4 }, "I ", " /", 'I', "ingotIron", '/', "stickWood");

			// Gold
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_PICKAXE),
					new Object[] { "itemStringTough", 6 }, "III", " / ", " / ", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SWORD),
					new Object[] { "itemStringTough", 4 }, "I", "I", "/", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SHOVEL),
					new Object[] { "itemStringTough", 4 }, "I", "/", "/", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE),
					new Object[] { "itemStringTough", 4 }, "II", "I/", " /", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE), true,
					new Object[] { "itemStringTough", 4 }, "II", "/I", "/ ", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE),
					new Object[] { "itemStringTough", 2 }, "II", " /", " /", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE), true,
					new Object[] { "itemStringTough", 2 }, "II", "/ ", "/ ", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife), true,
					new Object[] { "itemStringTough", 4 }, " I", "/ ", 'I', "ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife),
					new Object[] { "itemStringTough", 4 }, "I ", " /", 'I', "ingotGold", '/', "stickWood");

			// Diamond
			if (BBConfig.requireBlazePowderForDiamondPick) {
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
						new Object[] { "dustRedstone", 5, new ItemStack(Items.BLAZE_POWDER, 3), "itemStringTough", 4 },
						"DDD", " / ", " / ", 'D', "gemDiamond", '/', "stickWood");
			} else {
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
						new Object[] { "dustRedstone", 5, "itemStringTough", 4 }, "DDD", " / ", " / ", 'D',
						"gemDiamond", '/', "stickWood");
			}
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SWORD),
					new Object[] { "dustRedstone", 4, new ItemStack(Items.BLAZE_POWDER, 5), "itemStringTough", 4 }, "D",
					"D", "/", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SHOVEL),
					new Object[] { "dustRedstone", 2, new ItemStack(Items.BLAZE_POWDER, 1), "itemStringTough", 4 }, "D",
					"/", "/", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE),
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 2), "itemStringTough", 4 },
					"DD", "D/", " /", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE), true,
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 2), "itemStringTough", 4 },
					"DD", "/D", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE),
					new Object[] { "dustRedstone", 1, new ItemStack(Items.BLAZE_POWDER, 1), "itemStringTough", 2 },
					"DD", " /", " /", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE), true,
					new Object[] { "dustRedstone", 1, new ItemStack(Items.BLAZE_POWDER, 1), "itemStringTough", 2 },
					"DD", "/ ", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife), true,
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 3), "itemStringTough", 4 },
					" D", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife),
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 3), "itemStringTough", 4 },
					"D ", " /", 'D', "gemDiamond", '/', "stickWood");

			// Misc
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.bonePickaxe),
					new Object[] { "itemStringTough", 4 }, "sBs", " / ", " / ", 's', RegisterItems.boneShard, 'B',
					Items.BONE, '/', "stickWood");

			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BOW), new Object[] { "itemStringTough", 6 },
					" /s", "/ s", " /s", 's', "itemString", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BOW), true,
					new Object[] { "itemStringTough", 6 }, "s/ ", "s /", "s/ ", 's', "itemString", '/', "stickWood");
		} else {
			// Stone Tools/Weapons
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_PICKAXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "###", " / ", " / ", '#', "stone",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_SWORD),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "#", "#", "/", '#', "stone", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_SHOVEL),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "#", "/", "/", '#', "stone", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_AXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "#/", " /", '#', "stone", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_AXE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "##", "/#", "/ ", '#', "stone", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_HOE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "##", " /", " /", '#', "stone", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.STONE_HOE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "##", "/ ", "/ ", '#', "stone", '/',
					"stickWood");

			// Iron Tools/Weapons
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_PICKAXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 3) }, "III", " / ", " / ", 'I',
					"ingotIron", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_SWORD),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I", "I", "/", 'I', "ingotIron", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_SHOVEL),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I", "/", "/", 'I', "ingotIron", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_AXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "II", "I/", " /", 'I', "ingotIron",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_AXE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "II", "/I", "/ ", 'I', "ingotIron",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_HOE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "II", " /", " /", 'I', "ingotIron",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_HOE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "II", "/ ", "/ ", 'I', "ingotIron",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, " I", "/ ", 'I', "ingotIron", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I ", " /", 'I', "ingotIron", '/',
					"stickWood");

			// Gold Weapons/Tools
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_PICKAXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 3) }, "III", " / ", " / ", 'I',
					"ingotGold", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SWORD),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I", "I", "/", 'I', "ingotGold", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SHOVEL),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I", "/", "/", 'I', "ingotGold", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "II", "I/", " /", 'I', "ingotGold",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "II", "/I", "/ ", 'I', "ingotGold",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "II", " /", " /", 'I',
					Items.GOLD_INGOT, '/', Items.STICK);
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 1) }, "II", "/ ", "/ ", 'I', "ingotGold",
					'/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, " I", "/ ", 'I', "ingotGold", '/',
					"stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "I ", " /", 'I', "ingotGold", '/',
					"stickWood");

			// Diamond Tools/Weapons
			if (BBConfig.requireBlazePowderForDiamondPick) {
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
						new Object[] { "dustRedstone", 5, new ItemStack(Items.BLAZE_POWDER, 3),
								new ItemStack(RegisterItems.leatherStrip, 3) },
						"DDD", " / ", " / ", 'D', "gemDiamond", '/', "stickWood");
			} else {
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
						new Object[] { "dustRedstone", 5, new ItemStack(RegisterItems.leatherStrip, 3) }, "DDD", " / ",
						" / ", 'D', "gemDiamond", '/', "stickWood");
			}
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SWORD),
					new Object[] { "dustRedstone", 4, new ItemStack(Items.BLAZE_POWDER, 5),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					"D", "D", "/", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SHOVEL),
					new Object[] { "dustRedstone", 2, new ItemStack(Items.BLAZE_POWDER, 1),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					"D", "/", "/", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE),
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 2),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					"DD", "D/", " /", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE), true,
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 2),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					"DD", "/D", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE),
					new Object[] { "dustRedstone", 1, new ItemStack(Items.BLAZE_POWDER, 1),
							new ItemStack(RegisterItems.leatherStrip, 1) },
					"DD", " /", " /", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE), true,
					new Object[] { "dustRedstone", 1, new ItemStack(Items.BLAZE_POWDER, 1),
							new ItemStack(RegisterItems.leatherStrip, 1) },
					"DD", "/ ", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife), true,
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 3),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					" D", "/ ", 'D', "gemDiamond", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife),
					new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 3),
							new ItemStack(RegisterItems.leatherStrip, 2) },
					"D ", " /", 'D', "gemDiamond", '/', "stickWood");

			// Misc
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.bonePickaxe),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 2) }, "sBs", " / ", " / ", 's',
					RegisterItems.boneShard, 'B', Items.BONE, '/', "stickWood");

			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BOW),
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 3) }, " /s", "/ s", " /s", 's',
					"itemString", '/', "stickWood");
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BOW), true,
					new Object[] { new ItemStack(RegisterItems.leatherStrip, 3) }, "s/ ", "s /", "s/ ", 's',
					"itemString", '/', "stickWood");
		}

		// Iron armor
		AdvancedCraftingHandler
				.addAdvancedRecipe(new ItemStack(Items.IRON_HELMET),
						new Object[] { "nuggetIron", 2, new ItemStack(RegisterItems.leatherStrip, 3),
								new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE) },
						"III", "I I", 'I', "ingotIron");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_CHESTPLATE),
				new Object[] { "nuggetIron", 6, new ItemStack(RegisterItems.leatherStrip, 2),
						new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE) },
				"I I", "III", "III", 'I', "ingotIron");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.IRON_LEGGINGS),
				new Object[] { "nuggetIron", 4, new ItemStack(RegisterItems.leatherStrip, 4),
						new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) },
				"III", "I I", "I I", 'I', "ingotIron");
		AdvancedCraftingHandler
				.addAdvancedRecipe(new ItemStack(Items.IRON_BOOTS),
						new Object[] { "nuggetIron", 3, new ItemStack(RegisterItems.leatherStrip, 2),
								new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE) },
						"I I", "I I", 'I', "ingotIron");

		// Gold armor
		AdvancedCraftingHandler.addAdvancedRecipe(
				new ItemStack(Items.GOLDEN_HELMET), new Object[] { new ItemStack(RegisterItems.leatherStrip, 3),
						"nuggetGold", 2, new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE) },
				"III", "I I", 'I', "ingotGold");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE),
				new Object[] { new ItemStack(RegisterItems.leatherStrip, 2), "nuggetGold", 6,
						new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE) },
				"I I", "III", "III", 'I', "ingotGold");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.GOLDEN_LEGGINGS),
				new Object[] { new ItemStack(RegisterItems.leatherStrip, 4), "nuggetGold", 4,
						new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) },
				"III", "I I", "I I", 'I', "ingotGold");
		AdvancedCraftingHandler.addAdvancedRecipe(
				new ItemStack(Items.GOLDEN_BOOTS), new Object[] { new ItemStack(RegisterItems.leatherStrip, 2),
						"nuggetGold", 3, new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE) },
				"I I", "I I", 'I', "ingotGold");

		// Diamond armor
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HELMET),
				new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 2),
						new ItemStack(RegisterItems.leatherStrip, 3),
						new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE) },
				"DDD", "D D", 'D', "gemDiamond");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE),
				new Object[] { "dustRedstone", 4, new ItemStack(Items.BLAZE_POWDER, 6),
						new ItemStack(RegisterItems.leatherStrip, 2),
						new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE) },
				"D D", "DDD", "DDD", 'D', "gemDiamond");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_LEGGINGS),
				new Object[] { "dustRedstone", 4, new ItemStack(Items.BLAZE_POWDER, 3),
						new ItemStack(RegisterItems.leatherStrip, 4),
						new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE) },
				"DDD", "D D", "D D", 'D', "gemDiamond");
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.DIAMOND_BOOTS),
				new Object[] { "dustRedstone", 3, new ItemStack(Items.BLAZE_POWDER, 3),
						new ItemStack(RegisterItems.leatherStrip, 2),
						new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE) },
				"D D", "D D", 'D', "gemDiamond");

		// endregion Tools & Armor

		if (BBConfig.moduleInfusionRepair) {
			// Repair Infusion Station
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterBlocks.infusionRepairStation),
					new Object[] { "dustRedstone", 32, "gemLapis", 32, new ItemStack(Items.BLAZE_POWDER, 16) }, "DID",
					"OSO", "OOO", 'D', "gemDiamond", 'I', "blockIron", 'S', Blocks.BOOKSHELF, 'O', Blocks.OBSIDIAN);

			// Infusion Scroll
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.infusionScroll),
					new Object[] { "gemDiamond", 2, "stickWood", 2 }, "PPP", "RPR", "PPP", 'P', Items.PAPER, 'R',
					"dustRedstone");
		}

		if (BBConfig.moduleFurnaces) {
			// Obsidian Kiln
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterBlocks.obsidianKiln),
					new Object[] { "dustRedstone", 16 }, "ROR", "OKO", "ROR", 'R', "dustRedstone", 'O', Blocks.OBSIDIAN,
					'K', RegisterBlocks.kiln);

			// Nether Brick Oven
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterBlocks.netherBrickOven),
					new Object[] { new ItemStack(Items.BLAZE_POWDER, 4), new ItemStack(Blocks.OBSIDIAN, 1) }, "NNN",
					"NBN", "NGN", 'N', Blocks.NETHER_BRICK, 'G', "blockGlassColorless", 'B', RegisterBlocks.brickOven);

			// Ender Smelter
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterBlocks.enderSmelter),
					new Object[] { new ItemStack(Items.ENDER_PEARL, 4), "nuggetIron", 4 }, "#E#", "#S#", "#E#", '#',
					Blocks.END_STONE, 'S', RegisterBlocks.smelter, 'E', Items.ENDER_EYE);

			/*
			 * if (Loader.isModLoaded("enderio") || Loader.isModLoaded("buildcraft") ||
			 * Loader.isModLoaded("cofhcore") || Loader.isModLoaded("immersiveengineering"))
			 * { AdvancedCraftingHandler.addAdvancedRecipe(new
			 * ItemStack(RegisterBlocks.redstoneKiln), new Object[]{ "dustRedstone", 32,
			 * "nuggetGold", 16, new ItemStack(Items.BLAZE_POWDER, 10) }, "DED", "/K/",
			 * "DED", 'K', RegisterBlocks.obsidianKiln, '/', Items.BLAZE_ROD, 'E',
			 * Items.ENDER_EYE, 'D', Items.DIAMOND); }
			 */
		}

		// Rock Hammer
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.rockHammer),
				new Object[] { new ItemStack(RegisterItems.leatherStrip, 2), "nuggetIron", 2 }, "I#I", " / ", " / ",
				'I', "ingotIron", '#', "stone", '/', "stickWood");

		if (BBConfig.moduleCampfire) {
			// Pan
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.pan),
					new Object[] { "nuggetIron", 2, }, "/  ", " II", '/', "stickWood", 'I', "ingotIron");
		}

		if (BBConfig.netherlessBlazePowderRecipe) {
			AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(Items.BLAZE_POWDER, 4),
					new Object[] { new ItemStack(Items.GUNPOWDER, 8), new ItemStack(Items.FLINT, 8) }, "CRC", "RfR",
					"CRC", 'C', "blockCoal", 'R', "blockRedstone", 'f', Items.FLINT_AND_STEEL);
		}

		// Wicker Shield
		AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterItems.wickerShield),
				new Object[] { new ItemStack(RegisterItems.leatherStrip, 4), new ItemStack(Items.STICK, 2) }, "WWW",
				"WWW", " W ", 'W', RegisterItems.sugarCaneStrips);

		/*
		 * AdvancedCraftingConfig.addAdvancedRecipe(new
		 * ItemStack(RegisterBlocks.wickerBasket), new Object[] {"itemStringTough", 4},
		 * "###", "# #", "###", '#', RegisterItems.sugarCaneStrips);
		 */
	}
}