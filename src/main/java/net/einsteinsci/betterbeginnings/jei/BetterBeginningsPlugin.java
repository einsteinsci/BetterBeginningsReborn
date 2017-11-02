package net.einsteinsci.betterbeginnings.jei;



import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.categories.*;
import net.einsteinsci.betterbeginnings.jei.recipehandlers.*;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

@JEIPlugin
public class BetterBeginningsPlugin extends BlankModPlugin 
{
	IJeiHelpers jeiHelpers;
	IGuiHelper guiHelper;
	IIngredientBlacklist blacklist;
	
	@Override
	public void register(IModRegistry registry) 
	{
		jeiHelpers = registry.getJeiHelpers();
		guiHelper = jeiHelpers.getGuiHelper();
		blacklist = jeiHelpers.getIngredientBlacklist();
		
		registerDescriptions(registry);
		registerRecipeCategories(registry);
		registerRecipeHandlers(registry);
		
		registry.addRecipes(KilnRecipeMaker.getKilnRecipes());
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.kiln), CategoryUIDs.KILN);
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.obsidianKiln), CategoryUIDs.KILN);
		
		registry.addRecipes(BrickOvenRecipeHandler.getRecipeList());
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.brickOven), CategoryUIDs.OVEN);
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.netherBrickOven), CategoryUIDs.OVEN);
		
		registry.addRecipes(SmelterRecipeHandler.getRecipes());
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.smelter), CategoryUIDs.SMELTER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.enderSmelter), CategoryUIDs.SMELTER);
		
		registry.addRecipes(CampfireRecipeMaker.getCampfireRecipes());
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.campfire), CategoryUIDs.CAMPFIRE);
		
		registry.addRecipes(AdvancedCraftingHandler.getRecipeList());
		registry.addRecipeCategoryCraftingItem(new ItemStack(RegisterBlocks.doubleWorkbench), CategoryUIDs.ADV_CRAFTING);
	}
	
	void registerRecipeHandlers(IModRegistry registry)
	{
		registry.addRecipeHandlers(new JEIKilnRecipeHandler(), new JEIOvenRecipeHandler(), new JEISmelterRecipeHandler(), new JEICampfireRecipeHandler(), new JEIAdvancedCraftingRecipeHandler());
	}
	
	void registerRecipeCategories(IModRegistry registry)
	{
		registry.addRecipeCategories(new KilnRecipeCategory(guiHelper), new OvenRecipeCategory(guiHelper), new SmelterRecipeCategory(guiHelper), new CampfireRecipeCategory(guiHelper), new AdvancedCraftingRecipeCategory(guiHelper));
	}

	void registerDescriptions(IModRegistry registry)
	{
		//Register item descriptions
		List<ItemStack> knifeList = Lists.newArrayList(new ItemStack(RegisterItems.flintKnife), new ItemStack(RegisterItems.boneKnife), new ItemStack(RegisterItems.goldKnife), new ItemStack(RegisterItems.ironKnife), new ItemStack(RegisterItems.diamondKnife));
		registry.addDescription(knifeList, getDescriptionKey("knives"));
		registerDescription(registry, RegisterItems.infusionScroll, 0);
		registerDescription(registry, RegisterItems.rockHammer, 0);
		registerDescription(registry, RegisterItems.rotisserie, 0);
		registerDescription(registry, RegisterItems.pan, 0);
		registerDescription(registry, RegisterItems.wickerShield, 0);
		//Register block descriptions		
		registerDescription(registry, RegisterBlocks.kiln, 0);
		registerDescription(registry, RegisterBlocks.obsidianKiln, 0);
		registerDescription(registry, RegisterBlocks.brickOven, 0);
		registerDescription(registry, RegisterBlocks.netherBrickOven, 0);
		registerDescription(registry, RegisterBlocks.smelter, 0);
		registerDescription(registry, RegisterBlocks.enderSmelter, 0);
		registerDescription(registry, RegisterBlocks.infusionRepairStation, 0);
		registerDescription(registry, RegisterBlocks.campfire, 0);
		registerDescription(registry, RegisterBlocks.wickerBasket, 0);
		
		//Hide lit kilns, smelters, etc. from JEI
		blackListBlock(blacklist, RegisterBlocks.kilnLit, 0);
		blackListBlock(blacklist, RegisterBlocks.obsidianKilnLit, 0);
		blackListBlock(blacklist, RegisterBlocks.brickOvenLit, 0);
		blackListBlock(blacklist, RegisterBlocks.netherBrickOvenLit, 0);
		blackListBlock(blacklist, RegisterBlocks.smelterLit, 0);
		blackListBlock(blacklist, RegisterBlocks.enderSmelterLit, 0);
		blackListBlock(blacklist, RegisterBlocks.campfireLit, 0);
	}

	void blackListBlock(IIngredientBlacklist blacklist, Block block, int meta)
	{
		blacklist.addIngredientToBlacklist(new ItemStack(block));
	}
	
	void blackListItem(IIngredientBlacklist blacklist, Item item, int meta)
	{
		blacklist.addIngredientToBlacklist(new ItemStack(item));
	}
	
	void registerDescription(IModRegistry registry, Block block, int meta)
	{
		registry.addDescription(new ItemStack(block), getDescriptionKey(block));
	}

	void registerDescription(IModRegistry registry, Item item, int meta)
	{
		registry.addDescription(new ItemStack(item), getDescriptionKey(item));
	}

	String getDescriptionKey(IForgeRegistryEntry<?> itemOrBlock)
	{
		return getDescriptionKey(itemOrBlock.getRegistryName().getResourcePath());
	}

	String getDescriptionKey(String name)
	{
		return ModMain.MODID + "." + name + ".jeiDescription";
	}
}
