package net.einsteinsci.betterbeginnings.register;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.config.json.AdvancedCraftingConfig;
import net.einsteinsci.betterbeginnings.config.json.BrickOvenConfig;
import net.einsteinsci.betterbeginnings.config.json.CampfireConfig;
import net.einsteinsci.betterbeginnings.config.json.KilnConfig;
import net.einsteinsci.betterbeginnings.config.json.SmelterConfig;
import net.einsteinsci.betterbeginnings.items.ItemCharredMeat;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterRecipes
{
    public static void addShapelessRecipes()
    {
    	//TODO Reimplement as .json recipes
	/*// Sticks from Saplings & Knife
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.STICK), "treeSapling", "itemKnife"));

	// Bone Shard
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.boneShard, 2),
		Items.BONE, "itemKnife"));

	// Leather Strip
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.leatherStrip, 3),
		Items.LEATHER, "itemKnife"));

	// Leather Strip (shortcut from rabbit hide)
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.leatherStrip),
		Items.RABBIT_HIDE, "itemKnife"));


	// Leather Strip (shortcut from rabbit hide)
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.leatherStrip),
		Items.RABBIT_HIDE, "itemKnife"));

	// Bonemeal from Bone Shard (a bit more rewarding)
	GameRegistry.addShapelessRecipe(new ItemStack(Items.DYE, 2, 15), RegisterItems.boneShard);

	// Iron Nugget
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.ironNugget, 9), "ingotIron"));

	// String
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(Items.STRING, 4),
		new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
		"itemKnife"));

	// Twine
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.twine, 2), Blocks.VINE, "itemKnife"));
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.twine),
		new ItemStack(Blocks.TALLGRASS, 1, 1), "itemKnife"));
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.twine),
		new ItemStack(Blocks.TALLGRASS, 1, 2), "itemKnife"));

	// Roasting Stick
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.roastingStick),
		"stickWood", "stickWood", "itemKnife"));

	if (BBConfig.moduleCampfire)
	{
	    // Campfire
	    GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterBlocks.campfire),
		    "stickWood", "stickWood", "stickWood", "itemKindling"));
	}

	// Sugar Cane Strips
	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(RegisterItems.sugarCaneStrips, 2),
		Items.REEDS, "itemKnife"));

	if(BBConfig.moduleFurnaces) GameRegistry.addShapelessRecipe(new ItemStack(Items.FURNACE_MINECART), Items.MINECART, RegisterBlocks.kiln);*/
    }

    public static void addFurnaceRecipes()
    {
	addKilnRecipes();
	addBrickOvenRecipes();
	addSmelterRecipes();
	addCampfireRecipes();
    }

    private static void addKilnRecipes()
    {
	KilnConfig.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.35f);
	KilnConfig.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.1f);
	KilnConfig.addRecipe("cobblestone", new ItemStack(Blocks.STONE), 0.1f);
	KilnConfig.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0), new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f);
	KilnConfig.addRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, 2), 0.1f);
	KilnConfig.addRecipe("logWood", new ItemStack(Items.COAL, 1, 1), 0.15f);
	KilnConfig.addRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1f);
	KilnConfig.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.1f);
	KilnConfig.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1f);

	KilnConfig.addRecipe(Items.BEEF, new ItemStack(RegisterItems.charredMeat), 0.1f);
	KilnConfig.addRecipe(Items.PORKCHOP, new ItemStack(RegisterItems.charredMeat), 0.1f);
	KilnConfig.addRecipe(Items.CHICKEN, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_CHICKEN), 0.1f);
	KilnConfig.addRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_FISH), 0.1f);
	KilnConfig.addRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_FISH), 0.1f);
	KilnConfig.addRecipe(Items.RABBIT, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_RABBIT), 0.1f);
	KilnConfig.addRecipe(Items.MUTTON, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_MUTTON), 0.1f);
    }

    private static void addBrickOvenRecipes()
    {
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.GOLDEN_APPLE), "GGG", "GAG", "GGG", 'G',
		Items.GOLD_INGOT, 'A', Items.APPLE);
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.GOLDEN_APPLE, 1, 1), "###", "#A#", "###", '#',
		Blocks.GOLD_BLOCK, 'A', Items.APPLE);
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.CAKE), "MMM", "SES", "WWW", 'M', Items.MILK_BUCKET,
		'S', Items.SUGAR, 'E', Items.EGG, 'W', "cropWheat");
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.BREAD, 2), "WWW", 'W', "cropWheat");
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.COOKIE, 8), "WCW", 'W', "cropWheat", 'C',
		new ItemStack(Items.DYE, 1, 3)); // Cocoa bean
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.RABBIT_STEW), " R ", "CPM", " B ",
		'R', Items.COOKED_RABBIT, 'C', "cropCarrot", 'P', "cropPotato",
		'M', Blocks.BROWN_MUSHROOM, 'B', Items.BOWL);
	BrickOvenConfig.addShapedRecipe(new ItemStack(Items.RABBIT_STEW), " R ", "CPM", " B ",
		'R', Items.COOKED_RABBIT, 'C', "cropCarrot", 'P', "cropPotato",
		'M', Blocks.RED_MUSHROOM, 'B', Items.BOWL);
	BrickOvenConfig.addShapedRecipe(new ItemStack(RegisterItems.marshmallow, 3), " S ", "SSS", " S ",
		'S', Items.SUGAR);

	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.MUSHROOM_STEW), Blocks.BROWN_MUSHROOM,
		Blocks.RED_MUSHROOM, Items.BOWL);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_BEEF), Items.BEEF);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_PORKCHOP), Items.PORKCHOP);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_CHICKEN), Items.CHICKEN);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_FISH), Items.FISH);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_FISH, 1, 1),
		new ItemStack(Items.FISH, 1, 1));
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_RABBIT), Items.RABBIT);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.COOKED_MUTTON), Items.MUTTON);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.BAKED_POTATO), "cropPotato");
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), Items.EGG, Items.SUGAR,
		Blocks.PUMPKIN);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), Items.SPIDER_EYE,
		Items.SUGAR, Blocks.BROWN_MUSHROOM);
	BrickOvenConfig.addShapelessRecipe(new ItemStack(Items.MAGMA_CREAM),
		Items.SLIME_BALL, Items.BLAZE_POWDER);
    }

    private static void addSmelterRecipes()
    {
	// Vanilla Ore Recipes (keep the result vanilla to prevent weirdness)
	SmelterConfig.addRecipe("oreIron", new ItemStack(Items.IRON_INGOT), 0.7f, 1, 1);
	SmelterConfig.addRecipe("oreGold", new ItemStack(Items.GOLD_INGOT), 1.0f, 2, 1);

	// Modded Ore Recipes
	RegisterHelper.registerSmelterConfigOreRecipe("oreCopper", "ingotCopper", 0.6f, 1, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreTin", "ingotTin", 0.6f, 1, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreAluminum", "ingotAluminum", 0.8f, 1, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreSilver", "ingotSilver", 1.0f, 1, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreLead", "ingotLead", 0.6f, 1, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("orePlatinum", "ingotPlatinum", 1.0f, 2, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreNickel", "ingotNickel", 0.8f, 1, 1);

	// Recipes that might be better suited in Kiln only
	if (BBConfig.canSmelterDoKilnStuff)
	{
	    SmelterConfig.addRecipe(new ItemStack(Blocks.SAND, 1, 0),
		    new ItemStack(Blocks.GLASS), 0.1f, 1, 0);
	    SmelterConfig.addRecipe(new ItemStack(Blocks.SAND, 1, 1),
		    new ItemStack(Blocks.STAINED_GLASS, 1, 1), 0.1f, 1, 0); // Red sand makes orange stained glass.
	    SmelterConfig.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.25f, 1, 1);
	    SmelterConfig.addRecipe(Blocks.STONEBRICK, new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f, 1, 0);

	    SmelterConfig.addRecipe("cobblestone", new ItemStack(Blocks.STONE), 0.1f, 0, 0);
	    SmelterConfig.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.3f, 0, 0);
	    SmelterConfig.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.35f, 0, 0);
	    SmelterConfig.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1),
		    new ItemStack(Blocks.SPONGE, 1, 0), 0.1f, 0, 0);
	    SmelterConfig.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0),
		    new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f, 0, 0);
	}

	// Silk touch recipes
	SmelterConfig.addRecipe("oreCoal", new ItemStack(Items.COAL, 1), 0.25f, 2, 1);
	SmelterConfig.addRecipe("oreQuartz", new ItemStack(Items.QUARTZ, 2), 0.4f, 2, 2);
	SmelterConfig.addRecipe("oreLapis", new ItemStack(Items.DYE, 8, 4), 0.5f, 2, 3);
	SmelterConfig.addRecipe("oreRedstone", new ItemStack(Items.REDSTONE, 4), 0.8f, 2, 2);
	SmelterConfig.addRecipe("oreDiamond", new ItemStack(Items.DIAMOND, 1), 1.0f, 3, 1);
	SmelterConfig.addRecipe("oreEmerald", new ItemStack(Items.EMERALD, 1), 1.0f, 3, 1);

	// Silk touch recipes (modded)
	RegisterHelper.registerSmelterConfigOreRecipe("oreRuby", "gemRuby", 0.8f, 2, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreSapphire", "gemSapphire", 0.8f, 2, 1);
	RegisterHelper.registerSmelterConfigOreRecipe("oreOlivine", "gemOlivine", 0.8f, 2, 1);
    }

    private static void addCampfireRecipes()
    {
	CampfireConfig.addRecipe("logWood", new ItemStack(Items.COAL, 1, 1), 0.15f);
	CampfireConfig.addRecipe(new ItemStack(Blocks.SPONGE, 1, 1), new ItemStack(Blocks.SPONGE, 1, 0), 0.1f);

	CampfireConfig.addRecipe(RegisterItems.roastingStickRawMallow,
		new ItemStack(RegisterItems.roastingStickCookedMallow), 0.5f);

	CampfireConfig.addRecipe(Items.BEEF, new ItemStack(RegisterItems.charredMeat), 0.1f);
	CampfireConfig.addRecipe(Items.PORKCHOP, new ItemStack(RegisterItems.charredMeat), 0.1f);
	CampfireConfig.addRecipe(Items.CHICKEN, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_CHICKEN), 0.1f);
	CampfireConfig.addRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_FISH), 0.1f);
	CampfireConfig.addRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_FISH), 0.1f);
	CampfireConfig.addRecipe(Items.RABBIT, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_RABBIT), 0.1f);
	CampfireConfig.addRecipe(Items.MUTTON, new ItemStack(RegisterItems.charredMeat, 1,
		ItemCharredMeat.META_MUTTON), 0.1f);

	if (BBConfig.canCampfireDoAllKilnStuff)
	{
	    CampfireConfig.addRecipe(Items.CLAY_BALL, new ItemStack(Items.BRICK), 0.35f);
	    CampfireConfig.addRecipe(Blocks.CLAY, new ItemStack(Blocks.HARDENED_CLAY), 0.1f);
	    CampfireConfig.addRecipe("cobblestone", new ItemStack(Blocks.STONE), 0.1f);
	    CampfireConfig.addRecipe(new ItemStack(Blocks.STONEBRICK, 1, 0),
		    new ItemStack(Blocks.STONEBRICK, 1, 2), 0.1f);
	    CampfireConfig.addRecipe(Blocks.CACTUS, new ItemStack(Items.DYE, 1, 2), 0.1f);
	    CampfireConfig.addRecipe(Blocks.SAND, new ItemStack(Blocks.GLASS), 0.1f);
	    CampfireConfig.addRecipe(Blocks.NETHERRACK, new ItemStack(Items.NETHERBRICK), 0.1f);
	}

	CampfireConfig.addPanRecipe(Items.BEEF, new ItemStack(Items.COOKED_BEEF), 0.1f);
	CampfireConfig.addPanRecipe(Items.PORKCHOP, new ItemStack(Items.COOKED_PORKCHOP), 0.1f);
	CampfireConfig.addPanRecipe(Items.CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.1f);
	CampfireConfig.addPanRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.COOKED_FISH, 1, 0), 0.1f);
	CampfireConfig.addPanRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), 0.1f);
	CampfireConfig.addPanRecipe(Items.RABBIT, new ItemStack(Items.COOKED_RABBIT), 0.1f);
	CampfireConfig.addPanRecipe(Items.MUTTON, new ItemStack(Items.COOKED_MUTTON), 0.1f);
    }

    public static void addAdvancedRecipes()
    {
	if(!BBConfig.moduleAdvancedCrafting) return;
	// region advancedCraftingForLotsOfThings
	if (BBConfig.advancedCraftingForLotsOfThings)
	{
	    // Wooden Doors
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.OAK_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 0));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.SPRUCE_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 1));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BIRCH_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 2));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.JUNGLE_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 3));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.ACACIA_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 4));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DARK_OAK_DOOR, 3),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "##", "##",
		    '#', new ItemStack(Blocks.PLANKS, 1, 5));

	    // Iron Door
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_DOOR),
		    new Object[] {"nuggetIron", 2},
		    "II", "II", "II",
		    'I', "ingotIron");
	    // Fence Gates
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.OAK_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 0));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.SPRUCE_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 1));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.BIRCH_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 2));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.JUNGLE_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 3));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.ACACIA_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 4));
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.DARK_OAK_FENCE_GATE),
		    new Object[] {"itemString", 4, new ItemStack(RegisterItems.leatherStrip, 2)},
		    "/#/",
		    "/#/",
		    '/', "stickWood",
		    '#', new ItemStack(Blocks.PLANKS, 1, 5));

	    // Trapdoor
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TRAPDOOR),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "###", "###",
		    '#', "plankWood");
	    // Chest. Yep, you need iron before you can make a chest. If you absolutely must store stuff before you have
	    // iron, use your kiln (provided it isn't kiln-able ;D).
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.CHEST),
		    new Object[] {"nuggetIron", 3, new ItemStack(RegisterItems.leatherStrip, 1)},
		    "###",
		    "# #",
		    "###",
		    '#', "plankWood");

	    if (BBConfig.anyStringForTraps)
	    {
		// Trapped Chest
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TRAPPED_CHEST),
			new Object[]{"itemString", 2, "dustRedstone", 2},
			"C", "H",
			'C', Blocks.CHEST,
			'H', Blocks.TRIPWIRE_HOOK);
		// Tripwire Hook
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK),
			new Object[]{"itemString", 1, "dustRedstone", 1},
			"I", "/", "#",
			'I', "ingotIron",
			'/', "stickWood",
			'#', "plankWood");
	    }
	    else
	    {
		// Trapped Chest
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TRAPPED_CHEST),
			new Object[]{new ItemStack(RegisterItems.thread, 2), "dustRedstone", 2},
			"C", "H",
			'C', Blocks.CHEST,
			'H', Blocks.TRIPWIRE_HOOK);
		// Tripwire Hook
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TRIPWIRE_HOOK),
			new Object[]{new ItemStack(RegisterItems.thread, 1), "dustRedstone", 1},
			"I", "/", "#",
			'I', "ingotIron",
			'/', "stickWood",
			'#', "plankWood");
	    }
	    // Piston
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.PISTON),
		    new Object[] {"nuggetIron", 2},
		    "###", "CIC", "CRC",
		    '#', "plankWood",
		    'I', "ingotIron",
		    'C', "cobblestone",
		    'R', "dustRedstone");
	    // Dispenser
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.DISPENSER),
		    new Object[] {"nuggetIron", 2},
		    "###", "#B#", "#R#",
		    '#', "cobblestone",
		    'B', new ItemStack(Items.BOW, 1, 0),
		    'R', "dustRedstone");
	    // Note Block
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.NOTEBLOCK),
		    new Object[] {"itemString", 2},
		    "###", "#R#", "###",
		    '#', "plankWood",
		    'R', "dustRedstone");
	    // Gold Rail
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.GOLDEN_RAIL, 6),
		    new Object[] {"nuggetGold", 2},
		    "G G", "G/G", "GRG",
		    'G', "ingotGold",
		    '/', "stickWood",
		    'R', "dustRedstone");
	    // Detector Rail
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.DETECTOR_RAIL, 6),
		    new Object[] {"nuggetIron", 2, "stickWood", 4},
		    "I I", "I_I", "IRI",
		    'I', "ingotIron",
		    '_', Blocks.STONE_PRESSURE_PLATE,
		    'R', "dustRedstone");
	    // TNT
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.TNT, 2),
		    new Object[] {"itemString", 3},
		    "G#G", "#G#", "G#G",
		    'G', Items.GUNPOWDER,
		    '#', Blocks.SAND);
	    // Bookshelf
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.BOOKSHELF),
		    new Object[] {"nuggetIron", 1},
		    "###", "BBB", "###",
		    '#', "plankWood",
		    'B', Items.BOOK);
	    // Ladder
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.LADDER, 4),
		    new Object[] {"itemString", 1},
		    "/ /", "///", "/ /",
		    '/', "stickWood");
	    // Rail
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.RAIL, 16),
		    new Object[] {"nuggetIron", 1},
		    "I I", "I/I", "I I",
		    'I', "ingotIron",
		    '/', "stickWood");
	    // Enchanting Table
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.ENCHANTING_TABLE),
		    new Object[] {new ItemStack(Items.LEATHER), "dyeRed", 2, "gemLapis", 4},
		    " B ", "D#D", "###",
		    'B', Items.BOOK,
		    'D', "gemDiamond",
		    '#', Blocks.OBSIDIAN);
	    // Beacon
	    ItemStack awkwardPotion = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD);
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.BEACON),
		    new Object[] {"blockDiamond", 1,
			    new ItemStack(Items.BLAZE_ROD, 4),
			    awkwardPotion}, //Awkward potion
		    "+++", "+S+", "###",
		    '+', "blockGlassColorless",
		    'S', Items.NETHER_STAR,
		    '#', Blocks.OBSIDIAN);
	    // Anvil
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.ANVIL), new Object[] {"nuggetIron", 4},
		    "###", " I ", "III",
		    '#', "blockIron",
		    'I', "ingotIron");
	    // Hopper
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.HOPPER, 4), new Object[] {"nuggetIron", 2,
		    new ItemStack(Blocks.STONE_PRESSURE_PLATE, 1)},
		    "I I", "I#I", " I ",
		    'I', "ingotIron",
		    '#', Blocks.CHEST);
	    // Activator Rail
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.ACTIVATOR_RAIL, 6),
		    new Object[] {"nuggetIron", 4},
		    "I/I", "IiI", "I/I",
		    'I', "ingotIron",
		    '/', "stickWood",
		    'i', Blocks.REDSTONE_TORCH);
	    // Dropper
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.DROPPER),
		    new Object[] {"nuggetIron", 2},
		    "###", "# #", "#R#",
		    '#', "cobblestone",
		    'R', "dustRedstone");
	    // Minecart
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.MINECART),
		    new Object[] {"nuggetIron", 2,
			    new ItemStack(RegisterItems.leatherStrip, 1)},
		    "I I", "III",
		    'I', "ingotIron");
	    // Compass
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.COMPASS),
		    new Object[] {"nuggetIron", 3,
			    new ItemStack(Items.POTIONITEM, 1, 0)}, //Water Bottle
		    " I ", "IRI", " I ",
		    'I', "ingotIron",
		    'R', "dustRedstone");
	    // Clock
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.CLOCK),
		    new Object[] {"nuggetGold", 3, "dyeBlack", 1},
		    " G ", "GRG", " G ",
		    'G', "ingotGold",
		    'R', "dustRedstone");
	    // Bed
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BED), new Object[] {"nuggetIron", 2},
		    "***", "###",
		    '*', new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE),
		    '#', "plankWood");
	    // Brewing Stand
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BREWING_STAND),
		    new Object[] {"nuggetGold", 1, "nuggetIron", 3},
		    " / ", "###",
		    '/', Items.BLAZE_ROD,
		    '#', "cobblestone");
	    // Cauldron
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.CAULDRON), new Object[] {"nuggetIron", 3},
		    "I I", "I I", "III",
		    'I', "ingotIron");

	    // Jukebox
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.JUKEBOX),
		    new Object[] {"nuggetGold", 1, "itemString", 2},
		    "###", "#D#", "###",
		    '#', "plankWood",
		    'D', "gemDiamond");

	    // Redstone Lamp
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.REDSTONE_LAMP),
		    new Object[] {"dustRedstone", 2},
		    " R ", "R#R", " R ",
		    'R', "dustRedstone",
		    '#', "glowstone");

	    // Ender Chest
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.ENDER_CHEST),
		    new Object[] {new ItemStack(Items.BLAZE_POWDER, 4)},
		    "###", "#E#", "###",
		    '#', Blocks.OBSIDIAN,
		    'E', Items.ENDER_EYE);

	    // Weighted Pressure Plate (Iron)
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE),
		    new Object[] {"dustRedstone", 1}, "II", 'I', "ingotIron");

	    // Weighted Pressure Plate (Gold)
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE),
		    new Object[] {"dustRedstone", 1}, "GG", 'G', "ingotGold");

	    // Daylight Sensor
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.DAYLIGHT_DETECTOR),
		    new Object[] {"dustRedstone", 2},
		    "###", "QQQ", "---",
		    '#', "blockGlassColorless",
		    'Q', "gemQuartz",
		    '-', Blocks.WOODEN_SLAB);

	    // Iron Trapdoor
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Blocks.IRON_TRAPDOOR),
		    new Object[] {"nuggetIron", 2},
		    "II", "II",
		    'I', "ingotIron");

	    // Item Frame
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.ITEM_FRAME),
		    new Object[] {"itemString", 1},
		    "///", "/L/", "///",
		    '/', "stickWood",
		    'L', Items.LEATHER);

	    // Comparator
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.COMPARATOR),
		    new Object[] {"dustRedstone", 1},
		    " i ", "iQi", "###",
		    'i', Blocks.REDSTONE_TORCH,
		    'Q', Items.QUARTZ,
		    '#', "stone");

	    // Armor Stand
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.ARMOR_STAND),
		    new Object[] {"nuggetIron", 2},
		    "///", " / ", "/-/",
		    '/', "stickWood",
		    '-', new ItemStack(Blocks.STONE_SLAB, 1, 0));
	}
	// endregion

	// Fishing rod
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.FISHING_ROD),
		new Object[] {new ItemStack(RegisterItems.ironNugget)},
		"  /", " /s", "/ s",
		'/', "stickWood",
		's', "itemString");

	// Shears
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.SHEARS),
		new Object[] {"nuggetIron", 1},
		" I", "I ",
		'I', "ingotIron");

	// region Tools & Armor

	// Leather armor
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.LEATHER_HELMET),
		new Object[] {"itemString", 3,
			new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE)},
		"LLL",
		"L L",
		'L', Items.LEATHER);
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.LEATHER_CHESTPLATE),
		new Object[] {"itemString", 2,
			new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE)},
		"L L",
		"LLL",
		"LLL",
		'L', Items.LEATHER);
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.LEATHER_LEGGINGS),
		new Object[] {"itemString", 4,
			new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)},
		"LLL",
		"L L",
		"L L",
		'L', Items.LEATHER);
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.LEATHER_BOOTS),
		new Object[] {"itemString", 4,
			new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE)},
		"L L", "L L",
		'L', Items.LEATHER);

	if (BBConfig.allowStringAsToolBinding)
	{
	    //Stone
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_PICKAXE),
		    new Object[] {"itemStringTough", 4},
		    "###", " / ", " / ",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_SWORD),
		    new Object[] {"itemStringTough", 4},
		    "#", "#", "/",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_SHOVEL),
		    new Object[] {"itemStringTough", 2},
		    "#", "/", "/",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_AXE),
		    new Object[] {"itemStringTough", 4},
		    "##", "#/", " /",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_AXE), true,
		    new Object[] {"itemStringTough", 4},
		    "##", "/#", "/ ",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_HOE),
		    new Object[] {"itemStringTough", 2},
		    "##", " /", " /",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_HOE), true,
		    new Object[] {"itemStringTough", 2},
		    "##", "/ ", "/ ",
		    '#', "stone",
		    '/', "stickWood");

	    //Iron
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_PICKAXE),
		    new Object[] {"itemStringTough", 4},
		    "III", " / ", " / ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_SWORD),
		    new Object[] {"itemStringTough", 4},
		    "I", "I", "/",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_SHOVEL),
		    new Object[] {"itemStringTough", 2},
		    "I", "/", "/",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_AXE),
		    new Object[] {"itemStringTough", 4},
		    "II", "I/", " /",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_AXE), true,
		    new Object[] {"itemStringTough", 4},
		    "II", "/I", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_HOE),
		    new Object[] {"itemStringTough", 2},
		    "II", " /", " /",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_HOE), true,
		    new Object[] {"itemStringTough", 2},
		    "II", "/ ", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife), true,
		    new Object[] {"itemStringTough", 4},
		    " I", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife),
		    new Object[] {"itemStringTough", 4},
		    "I ", " /",
		    'I', "ingotIron",
		    '/', "stickWood");

	    //Gold
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_PICKAXE),
		    new Object[] {"itemStringTough", 6},
		    "III", " / ", " / ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SWORD),
		    new Object[] {"itemStringTough", 4},
		    "I", "I", "/",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SHOVEL),
		    new Object[] {"itemStringTough", 4},
		    "I", "/", "/",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE),
		    new Object[] {"itemStringTough", 4},
		    "II", "I/", " /",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE), true,
		    new Object[] {"itemStringTough", 4},
		    "II", "/I", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE),
		    new Object[] {"itemStringTough", 2},
		    "II", " /", " /",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE), true,
		    new Object[] {"itemStringTough", 2},
		    "II", "/ ", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife), true,
		    new Object[] {"itemStringTough", 4},
		    " I", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife),
		    new Object[] {"itemStringTough", 4},
		    "I ", " /",
		    'I', "ingotGold",
		    '/', "stickWood");

	    //Diamond
	    if (BBConfig.requireBlazePowderForDiamondPick)
	    {
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
			new Object[] {"dustRedstone", 5, new ItemStack(Items.BLAZE_POWDER, 3),
				"itemStringTough", 4},
			"DDD", " / ", " / ",
			'D', "gemDiamond",
			'/', "stickWood");
	    }
	    else
	    {
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
			new Object[] {"dustRedstone", 5, "itemStringTough", 4},
			"DDD", " / ", " / ",
			'D', "gemDiamond",
			'/', "stickWood");
	    }
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SWORD),
		    new Object[] {"dustRedstone", 4,
			    new ItemStack(Items.BLAZE_POWDER, 5),
			    "itemStringTough", 4},
		    "D", "D", "/",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SHOVEL),
		    new Object[] {"dustRedstone", 2,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    "itemStringTough", 4},
		    "D", "/", "/",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE),
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 2),
			    "itemStringTough", 4},
		    "DD", "D/", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE), true,
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 2),
			    "itemStringTough", 4},
		    "DD", "/D", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE),
		    new Object[] {"dustRedstone", 1,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    "itemStringTough", 2},
		    "DD", " /", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE), true,
		    new Object[] {"dustRedstone", 1,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    "itemStringTough", 2},
		    "DD", "/ ", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife), true,
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 3),
			    "itemStringTough", 4},
		    " D", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife),
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 3),
			    "itemStringTough", 4},
		    "D ", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");

	    //Misc
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.bonePickaxe),
		    new Object[] {"itemStringTough", 4},
		    "sBs", " / ", " / ",
		    's', RegisterItems.boneShard,
		    'B', Items.BONE,
		    '/', "stickWood");

	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BOW),
		    new Object[] {"itemStringTough", 6},
		    " /s", "/ s", " /s",
		    's', "itemString",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BOW), true,
		    new Object[] {"itemStringTough", 6},
		    "s/ ", "s /", "s/ ",
		    's', "itemString",
		    '/', "stickWood");
	}
	else
	{
	    // Stone Tools/Weapons
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_PICKAXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "###", " / ", " / ",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_SWORD),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "#", "#", "/",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_SHOVEL),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "#", "/", "/",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_AXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "#/", " /",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_AXE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "##", "/#", "/ ",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_HOE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "##", " /", " /",
		    '#', "stone",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.STONE_HOE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "##", "/ ", "/ ",
		    '#', "stone",
		    '/', "stickWood");

	    // Iron Tools/Weapons
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_PICKAXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 3)},
		    "III", " / ", " / ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_SWORD),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I", "I", "/",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_SHOVEL),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I", "/", "/",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_AXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "II", "I/", " /",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_AXE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "II", "/I", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_HOE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "II", " /", " /",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_HOE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "II", "/ ", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    " I", "/ ",
		    'I', "ingotIron",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.ironKnife),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I ", " /",
		    'I', "ingotIron",
		    '/', "stickWood");

	    //Gold Weapons/Tools
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_PICKAXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 3)},
		    "III", " / ", " / ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SWORD),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I", "I", "/",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_SHOVEL),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I", "/", "/",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "II", "I/", " /",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_AXE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "II", "/I", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "II", " /", " /",
		    'I', Items.GOLD_INGOT,
		    '/', Items.STICK);
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HOE), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 1)},
		    "II", "/ ", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    " I", "/ ",
		    'I', "ingotGold",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.goldKnife),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "I ", " /",
		    'I', "ingotGold",
		    '/', "stickWood");

	    // Diamond Tools/Weapons
	    if (BBConfig.requireBlazePowderForDiamondPick)
	    {
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
			new Object[] {"dustRedstone", 5, new ItemStack(Items.BLAZE_POWDER, 3),
				new ItemStack(RegisterItems.leatherStrip, 3)},
			"DDD", " / ", " / ",
			'D', "gemDiamond",
			'/', "stickWood");
	    }
	    else
	    {
		AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_PICKAXE),
			new Object[] {"dustRedstone", 5,
				new ItemStack(RegisterItems.leatherStrip, 3)},
			"DDD", " / ", " / ",
			'D', "gemDiamond",
			'/', "stickWood");
	    }
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SWORD),
		    new Object[] {"dustRedstone", 4,
			    new ItemStack(Items.BLAZE_POWDER, 5),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    "D", "D", "/",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_SHOVEL),
		    new Object[] {"dustRedstone", 2,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    "D", "/", "/",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE),
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 2),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    "DD", "D/", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_AXE), true,
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 2),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    "DD", "/D", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE),
		    new Object[] {"dustRedstone", 1,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    new ItemStack(RegisterItems.leatherStrip, 1)},
		    "DD", " /", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HOE), true,
		    new Object[] {"dustRedstone", 1,
			    new ItemStack(Items.BLAZE_POWDER, 1),
			    new ItemStack(RegisterItems.leatherStrip, 1)},
		    "DD", "/ ", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife), true,
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 3),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    " D", "/ ",
		    'D', "gemDiamond",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.diamondKnife),
		    new Object[] {"dustRedstone", 3,
			    new ItemStack(Items.BLAZE_POWDER, 3),
			    new ItemStack(RegisterItems.leatherStrip, 2)},
		    "D ", " /",
		    'D', "gemDiamond",
		    '/', "stickWood");

	    //Misc
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.bonePickaxe),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 2)},
		    "sBs", " / ", " / ",
		    's', RegisterItems.boneShard,
		    'B', Items.BONE,
		    '/', "stickWood");

	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BOW),
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 3)},
		    " /s", "/ s", " /s",
		    's', "itemString",
		    '/', "stickWood");
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BOW), true,
		    new Object[] {new ItemStack(RegisterItems.leatherStrip, 3)},
		    "s/ ", "s /", "s/ ",
		    's', "itemString",
		    '/', "stickWood");
	}

	// Iron armor
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_HELMET),
		new Object[] {"nuggetIron", 2,
			new ItemStack(RegisterItems.leatherStrip, 3),
			new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE)},
		"III", "I I",
		'I', "ingotIron");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_CHESTPLATE),
		new Object[] {"nuggetIron", 6,
			new ItemStack(RegisterItems.leatherStrip, 2),
			new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE)},
		"I I", "III", "III",
		'I', "ingotIron");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_LEGGINGS),
		new Object[] {"nuggetIron", 4,
			new ItemStack(RegisterItems.leatherStrip, 4),
			new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)},
		"III", "I I", "I I",
		'I', "ingotIron");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.IRON_BOOTS),
		new Object[] {"nuggetIron", 3,
			new ItemStack(RegisterItems.leatherStrip, 2),
			new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE)},
		"I I", "I I",
		'I', "ingotIron");

	//Gold armor
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_HELMET),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 3),
			"nuggetGold", 2,
			new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE)},
		"III", "I I",
		'I', "ingotGold");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 2),
			"nuggetGold", 6,
			new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE)},
		"I I",
		"III",
		"III",
		'I', "ingotGold");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_LEGGINGS),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 4),
			"nuggetGold", 4,
			new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)},
		"III",
		"I I",
		"I I",
		'I', "ingotGold");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.GOLDEN_BOOTS),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 2),
			"nuggetGold", 3,
			new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE)},
		"I I", "I I",
		'I', "ingotGold");

	// Diamond armor
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_HELMET),
		new Object[] {"dustRedstone", 3,
			new ItemStack(Items.BLAZE_POWDER, 2),
			new ItemStack(RegisterItems.leatherStrip, 3),
			new ItemStack(Blocks.WOOL, 2, OreDictionary.WILDCARD_VALUE)},
		"DDD", "D D",
		'D', "gemDiamond");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE),
		new Object[] {"dustRedstone", 4,
			new ItemStack(Items.BLAZE_POWDER, 6),
			new ItemStack(RegisterItems.leatherStrip, 2),
			new ItemStack(Blocks.WOOL, 4, OreDictionary.WILDCARD_VALUE)},
		"D D", "DDD", "DDD",
		'D', "gemDiamond");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_LEGGINGS),
		new Object[] {"dustRedstone", 4,
			new ItemStack(Items.BLAZE_POWDER, 3),
			new ItemStack(RegisterItems.leatherStrip, 4),
			new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE)},
		"DDD", "D D", "D D",
		'D', "gemDiamond");
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.DIAMOND_BOOTS),
		new Object[] {"dustRedstone", 3,
			new ItemStack(Items.BLAZE_POWDER, 3),
			new ItemStack(RegisterItems.leatherStrip, 2),
			new ItemStack(Blocks.WOOL, 3, OreDictionary.WILDCARD_VALUE)},
		"D D", "D D",
		'D', "gemDiamond");

	// endregion Tools & Armor

	if (BBConfig.moduleInfusionRepair)
	{
	    // Repair Infusion Station
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterBlocks.infusionRepairStation),
		    new Object[]{
			    "dustRedstone", 32, "gemLapis", 32,
			    new ItemStack(Items.BLAZE_POWDER, 16)
	    },
		    "DID", "OSO", "OOO",
		    'D', "gemDiamond",
		    'I', "blockIron",
		    'S', Blocks.BOOKSHELF,
		    'O', Blocks.OBSIDIAN);

	    // Infusion Scroll
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.infusionScroll),
		    new Object[] { "gemDiamond", 2, "stickWood", 2 },
		    "PPP", "RPR", "PPP",
		    'P', Items.PAPER,
		    'R', "dustRedstone");
	}

	if (BBConfig.moduleFurnaces)
	{
	    // Obsidian Kiln
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterBlocks.obsidianKiln),
		    new Object[]{"dustRedstone", 16},
		    "ROR", "OKO", "ROR",
		    'R', "dustRedstone",
		    'O', Blocks.OBSIDIAN,
		    'K', RegisterBlocks.kiln);

	    // Nether Brick Oven
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterBlocks.netherBrickOven),
		    new Object[]{
			    new ItemStack(Items.BLAZE_POWDER, 4),
			    new ItemStack(Blocks.OBSIDIAN, 1)
	    },
		    "NNN", "NBN", "NGN",
		    'N', Blocks.NETHER_BRICK,
		    'G', "blockGlassColorless",
		    'B', RegisterBlocks.brickOven);

	    // Ender Smelter
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterBlocks.enderSmelter),
		    new Object[]{new ItemStack(Items.ENDER_PEARL, 4), "nuggetIron", 4},
		    "#E#", "#S#", "#E#",
		    '#', Blocks.END_STONE,
		    'S', RegisterBlocks.smelter,
		    'E', Items.ENDER_EYE);

	    /*if (Loader.isModLoaded("enderio") || Loader.isModLoaded("buildcraft") ||
				Loader.isModLoaded("cofhcore") || Loader.isModLoaded("immersiveengineering"))
			{
				AdvancedCraftingHandler.addAdvancedRecipe(new ItemStack(RegisterBlocks.redstoneKiln),
					new Object[]{ "dustRedstone", 32, "nuggetGold", 16, new ItemStack(Items.BLAZE_POWDER, 10) },
					"DED", "/K/", "DED",
					'K', RegisterBlocks.obsidianKiln,
					'/', Items.BLAZE_ROD,
					'E', Items.ENDER_EYE,
					'D', Items.DIAMOND);
			}*/
	}

	// Rock Hammer
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.rockHammer),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 2), "nuggetIron", 2},
		"I#I", " / ", " / ",
		'I', "ingotIron",
		'#', "stone",
		'/', "stickWood");

	if (BBConfig.moduleCampfire)
	{
	    // Pan
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.pan),
		    new Object[]{"nuggetIron", 2,},
		    "/  ", " II",
		    '/', "stickWood",
		    'I', "ingotIron");
	}

	if (BBConfig.netherlessBlazePowderRecipe)
	{
	    AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(Items.BLAZE_POWDER, 4),
		    new Object[]{new ItemStack(Items.GUNPOWDER, 8), new ItemStack(Items.FLINT, 8)},
		    "CRC", "RfR", "CRC",
		    'C', "blockCoal",
		    'R', "blockRedstone",
		    'f', Items.FLINT_AND_STEEL);
	}

	//Wicker Shield
	AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterItems.wickerShield),
		new Object[] {new ItemStack(RegisterItems.leatherStrip, 4), new ItemStack(Items.STICK, 2)},
		"WWW",
		"WWW",
		" W ",
		'W', RegisterItems.sugarCaneStrips);

	/*AdvancedCraftingConfig.addAdvancedRecipe(new ItemStack(RegisterBlocks.wickerBasket),
				new Object[] {"itemStringTough", 4},
				"###",
				"# #", 
				"###",
				'#', RegisterItems.sugarCaneStrips);*/
    }

    public static void addShapedRecipes()
    {
    	//TODO Reimplement as .json recipes
	/*// Rotisserie
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.rotisserie), "/s", "s/",
		'/', RegisterItems.roastingStick, 's', "itemStringTough"));
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.rotisserie), "s/", "/s",
		'/', RegisterItems.roastingStick, 's', "itemStringTough"));

	// Knife
	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.flintKnife), "F ", " F", 'F', Items.FLINT);
	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.flintKnife), " F", "F ", 'F', Items.FLINT);

	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.boneKnife), " S", "/ ", 'S', RegisterItems.boneShard,
		'/', Items.BONE);
	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.boneKnife), "S ", " /", 'S', RegisterItems.boneShard,
		'/', Items.BONE);

	// Thread
	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.thread), "ss", "ss", 's', RegisterItems.silk);

	// Cloth
	GameRegistry.addShapedRecipe(new ItemStack(RegisterItems.cloth, 4), "ttt", "ttt", 't', RegisterItems.thread);

	// Lead (ore dictionary)
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.LEAD), "ss ", "s* ", "  s",
		's', "itemString", '*', Items.SLIME_BALL));

	// Flint Hatchet
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.flintHatchet), "FL", " /",
		'F', Items.FLINT, 'L', RegisterItems.leatherStrip, '/', "stickWood"));
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.flintHatchet), "LF", "/ ",
		'F', Items.FLINT, 'L', RegisterItems.leatherStrip, '/', "stickWood"));

	// Noob Wood Sword
	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.noobWoodSword), "#", "#", "/",
		'#', "plankWood", '/', "stickWood"));

	if (BBConfig.moduleFurnaces)
	{
	    // Kiln
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.kiln),
		    "###", "# #", "###", '#', "cobblestone"));

	    // Brick Oven
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.brickOven), "BBB", "# #", "###",
		    '#', Blocks.BRICK_BLOCK, 'B', "ingotBrick"));

	    // Smelter
	    GameRegistry.addShapedRecipe(new ItemStack(RegisterBlocks.smelter), "###", "#C#", "###",
		    '#', new ItemStack(Blocks.STONEBRICK, 1, 0), 'C', new ItemStack(Items.COAL, 1, 1));

	    // Vanilla Furnace
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.FURNACE), "###", "# #", "---",
		    '#', "cobblestone", '-', new ItemStack(Blocks.STONE_SLAB, 1, 3))); // cobblestone slab
	}

	// Gravel->Flint
	GameRegistry.addShapedRecipe(new ItemStack(Items.FLINT), "##", "##", '#', Blocks.GRAVEL);

	// Iron Nugget->Ingot
	GameRegistry.addShapedRecipe(new ItemStack(Items.IRON_INGOT), "***", "***", "***", '*',
		RegisterItems.ironNugget);

	if (BBConfig.canMakeChainArmor)
	{
	    // Chain Armor
	    GameRegistry.addShapedRecipe(new ItemStack(Items.CHAINMAIL_HELMET), "***", "* *", '*',
		    RegisterItems.chain);
	    GameRegistry.addShapedRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE), "* *", "***", "***", '*',
		    RegisterItems.chain);
	    GameRegistry.addShapedRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS), "***", "* *", "* *", '*',
		    RegisterItems.chain);
	    GameRegistry.addShapedRecipe(new ItemStack(Items.CHAINMAIL_BOOTS), "* *", "* *", '*',
		    RegisterItems.chain);
	}

	if(BBConfig.moduleAdvancedCrafting)
	{
	    // Workbench
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.doubleWorkbench), "##", "##", '#',
		    "plankWood"));

	    // Craft vanilla benches into BB workbenches
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.doubleWorkbench, 2), "##",
		    '#', "craftingTableWood"));

	    // Craft BB workbenches into vanilla ones
	    GameRegistry.addShapedRecipe(new ItemStack(Blocks.CRAFTING_TABLE), "#", '#', RegisterBlocks.doubleWorkbench);
	}

	if (BBConfig.moduleCampfire) // campfire recipe is shapeless now
	{
	    // Fire Bow
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.fireBow),
		    "//", "/s",
		    '/', "stickWood",
		    's', "itemString"));
	}

	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.chain), 
		" --",
		"- -",
		"-- ",
		'-', "nuggetIron"));

	GameRegistry.addShapedRecipe(new ItemStack(RegisterBlocks.wickerBasket), 
		"# #",
		"# #",
		"###", '#', RegisterItems.sugarCaneStrips);

	//Vanilla recipes for BB items/blocks that normally have ACT recipes
	if(!BBConfig.moduleAdvancedCrafting)
	{
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.wickerShield), 
		    "S/S",
		    "SLS",
		    "S/S", 'S', RegisterItems.sugarCaneStrips, 'L', RegisterItems.leatherStrip, '/', "stickWood"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.ironKnife), 
		    "  I",
		    " S ",
		    "/  ", 'S', "itemStringTough", 'I', "ingotIron", '/', "stickWood"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.goldKnife), 
		    "  G",
		    " S ",
		    "/  ", 'S', "itemStringTough", 'G', "ingotGold", '/', "stickWood"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.diamondKnife), 
		    "  D",
		    " S ",
		    "/  ", 'S', "itemStringTough", 'D', "gemDiamond", '/', "stickWood"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.bonePickaxe),
		    "sBs", " S ", " / ",
		    's', RegisterItems.boneShard,
		    'B', Items.BONE,
		    '/', "stickWood",
		    'S', "itemStringTough"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.rockHammer),
		    "I#I", " L ", " / ",
		    'I', "ingotIron",
		    '#', "stone",
		    '/', "stickWood",
		    'L', RegisterItems.leatherStrip));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.pan),
		    "/  ", " II",
		    'I', "ingotIron",
		    '/', "stickWood"));
	    if(BBConfig.moduleInfusionRepair)
		{GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterItems.infusionScroll),
		    "PDP",
		    "RPR", 
		    "PDP",
		    'P', Items.PAPER,
		    'R', "dustRedstone",
		    'D', "gemDiamond"));
	    GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.infusionRepairStation),
		    "DID", "OSO", "OOO",
		    'D', "gemDiamond",
		    'I', "blockIron",
		    'S', Blocks.BOOKSHELF,
		    'O', Blocks.OBSIDIAN));
		}
	    if(BBConfig.moduleFurnaces)
	    {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.obsidianKiln),
			    "ROR", "OKO", "ROR",
			    'R', "dustRedstone",
			    'O', Blocks.OBSIDIAN,
			    'K', RegisterBlocks.kiln));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.netherBrickOven),
			    "NNN", "PBP", "NON",
			    'N', Blocks.NETHER_BRICK,
			    'B', RegisterBlocks.brickOven,
			    'O', Blocks.OBSIDIAN,
			    'P', Items.BLAZE_POWDER));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RegisterBlocks.enderSmelter),
			    "#E#", "PSP", "#E#",
			    '#', Blocks.END_STONE,
			    'S', RegisterBlocks.smelter,
			    'E', Items.ENDER_EYE,
			    'P', Items.ENDER_PEARL));
	    }
	}*/
    }
}