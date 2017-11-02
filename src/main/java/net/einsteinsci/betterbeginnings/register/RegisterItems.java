package net.einsteinsci.betterbeginnings.register;

import java.util.ArrayList;
import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.items.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

public class RegisterItems
{
    public static final ToolMaterial NOOBWOOD = EnumHelper.addToolMaterial("NOOBWOOD", 0, 60, 2.0f, -4, 35);

    public static final ItemNoobWoodSword noobWoodSword = new ItemNoobWoodSword(NOOBWOOD);
    public static final ItemKnife flintKnife = new ItemKnifeFlint();
    public static final ItemKnife boneKnife = new ItemKnifeBone();
    public static final ItemKnife ironKnife = new ItemKnifeIron();
    public static final ItemKnife diamondKnife = new ItemKnifeDiamond();
    public static final ItemKnife goldKnife = new ItemKnifeGold();
    public static final ItemFlintHatchet flintHatchet = new ItemFlintHatchet();
    public static final ItemBonePickaxe bonePickaxe = new ItemBonePickaxe();
    public static final ItemResource boneShard = new ItemResource().setName("bone_shard");
    public static final ItemTestItem testItem = new ItemTestItem();
    public static final ItemResource  silk = new ItemResource().setName("silk");
    public static final ItemResource thread = new ItemResource().setName("thread");
    public static final ItemResource cloth = new ItemResource().setName("cloth");
    public static final ItemResource leatherStrip = new ItemResource().setName("leather_strip");
    public static final ItemResource ironNugget = new ItemResource().setName("iron_nugget");
    public static final ItemCharredMeat charredMeat = new ItemCharredMeat();
    public static final ItemFireBow fireBow = new ItemFireBow();
    public static final ItemMarshmallow marshmallow = new ItemMarshmallow();
    public static final ItemMarshmallowCooked marshmallowCooked = new ItemMarshmallowCooked();
    public static final ItemRoastingStick roastingStick = new ItemRoastingStick();
    public static final ItemRoastingStickMallow roastingStickRawMallow = new ItemRoastingStickMallow(false);
    public static final ItemRoastingStickMallow roastingStickCookedMallow = new ItemRoastingStickMallow(true);
    public static final ItemResource twine = new ItemResource().setName("twine");
    public static final ItemRockHammer rockHammer = new ItemRockHammer(ToolMaterial.IRON);
    public static final ItemPan pan = new ItemPan();
    public static final ItemRotisserie rotisserie = new ItemRotisserie();
    public static final ItemInfusionScroll infusionScroll = new ItemInfusionScroll();
    public static final ItemWickerShield wickerShield = new ItemWickerShield();
    public static final ItemResource sugarCaneStrips = new ItemResource().setName("sugar_cane_strips");
    public static final ItemResource chain = new ItemResource().setName("chain");

    public static final List<Item> allItems = new ArrayList<>();

    public static void register()
    {
	RegisterHelper.registerItem(flintKnife);
	RegisterHelper.registerItem(boneKnife);
	RegisterHelper.registerItem(ironKnife);
	RegisterHelper.registerItem(goldKnife);
	RegisterHelper.registerItem(diamondKnife);

	RegisterHelper.registerItem(flintHatchet);
	RegisterHelper.registerItem(bonePickaxe);

	RegisterHelper.registerItem(boneShard);
	RegisterHelper.registerItem(testItem);
	RegisterHelper.registerItem(silk);
	RegisterHelper.registerItem(thread);
	RegisterHelper.registerItem(cloth);
	RegisterHelper.registerItem(twine);
	RegisterHelper.registerItem(leatherStrip);
	RegisterHelper.registerItem(ironNugget);
	RegisterHelper.registerItem(fireBow);
	RegisterHelper.registerItem(rockHammer);
	RegisterHelper.registerItem(pan);
	RegisterHelper.registerItem(rotisserie);
	RegisterHelper.registerItem(infusionScroll);
	RegisterHelper.registerItem(sugarCaneStrips);
	RegisterHelper.registerItem(chain);

	RegisterHelper.registerItem(charredMeat);

	RegisterHelper.registerItem(marshmallow);
	RegisterHelper.registerItem(roastingStick);
	RegisterHelper.registerItem(marshmallowCooked);
	RegisterHelper.registerItem(roastingStickCookedMallow);
	RegisterHelper.registerItem(roastingStickRawMallow);

	RegisterHelper.registerItem(noobWoodSword);
	RegisterHelper.registerItem(wickerShield);

	oreDictRegistry();
    }

    public static void oreDictRegistry()
    {
	OreDictionary.registerOre("nuggetIron", ironNugget);

	OreDictionary.registerOre("itemKnife", new ItemStack(flintKnife, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKnife", new ItemStack(boneKnife, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKnife", new ItemStack(ironKnife, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKnife", new ItemStack(goldKnife, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKnife", new ItemStack(diamondKnife, 1, OreDictionary.WILDCARD_VALUE));

	OreDictionary.registerOre("itemString", new ItemStack(Items.STRING));
	OreDictionary.registerOre("itemString", new ItemStack(thread));
	OreDictionary.registerOre("itemString", new ItemStack(twine));

	OreDictionary.registerOre("itemStringTough", new ItemStack(Items.STRING));
	OreDictionary.registerOre("itemStringTough", new ItemStack(twine));

	// Don't forget to wildcard
	OreDictionary.registerOre("itemKindling", new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKindling", new ItemStack(Blocks.LEAVES, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKindling", new ItemStack(Blocks.LEAVES2, 1, OreDictionary.WILDCARD_VALUE));
	OreDictionary.registerOre("itemKindling", new ItemStack(Blocks.VINE));
	OreDictionary.registerOre("itemKindling", new ItemStack(Blocks.TALLGRASS, 1, OreDictionary.WILDCARD_VALUE));
    }

    public static void tweakVanilla()
    {
	((ItemFood) Items.BEEF).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 20, 0), 20);
	((ItemFood) Items.PORKCHOP).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 25, 0), 25);
	((ItemFood) Items.FISH).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 1), 60); // Both fish types here
	((ItemFood) Items.MUTTON).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 20, 0), 25);
	((ItemFood) Items.RABBIT).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 25, 0), 30);

	if(BBConfig.makeStuffStackable)
	{
	    // Let's face it, the vanilla stack sizes for these suck.
	    Items.MINECART.setMaxStackSize(16);
	    // Strangely enough the oak one doesn't change name.
	    Items.OAK_DOOR.setMaxStackSize(16);
	    Items.SPRUCE_DOOR.setMaxStackSize(16);
	    Items.BIRCH_DOOR.setMaxStackSize(16);
	    Items.ACACIA_DOOR.setMaxStackSize(16);
	    Items.DARK_OAK_DOOR.setMaxStackSize(16);
	    Items.IRON_DOOR.setMaxStackSize(16);
	}
	
	if(BBConfig.moduleFurnaces) Items.FURNACE_MINECART.setUnlocalizedName(ModMain.MODID + ".kilnCart");
    }
}
