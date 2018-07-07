package net.einsteinsci.betterbeginnings.jei;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.categories.*;
import net.einsteinsci.betterbeginnings.jei.wrappers.*;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.register.recipe.*;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

@JEIPlugin
public class BetterBeginningsPlugin implements IModPlugin
{
	@Override
	public void register(IModRegistry registry) 
	{
		registerDescriptions(registry);
		blacklistItems(registry.getJeiHelpers().getIngredientBlacklist());

		registry.handleRecipes(KilnRecipe.class, JEIKilnRecipeWrapper::new, CategoryUIDs.KILN);
		registry.addRecipes(KilnRecipeHandler.getRecipes(), CategoryUIDs.KILN);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.kiln), CategoryUIDs.KILN);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.obsidianKiln), CategoryUIDs.KILN);

		registry.handleRecipes(IBrickOvenRecipe.class, JEIOvenRecipeWrapper::new, CategoryUIDs.OVEN);
		registry.addRecipes(BrickOvenRecipeHandler.getRecipeList(), CategoryUIDs.OVEN);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.brickOven), CategoryUIDs.OVEN);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.netherBrickOven), CategoryUIDs.OVEN);

		registry.handleRecipes(SmelterRecipe.class, SmelterRecipeWrapper::new, CategoryUIDs.SMELTER);
		registry.addRecipes(SmelterRecipeHandler.getRecipes(), CategoryUIDs.SMELTER);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.smelter), CategoryUIDs.SMELTER);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.enderSmelter), CategoryUIDs.SMELTER);

		registry.handleRecipes(CampfireRecipe.class, JEICampfireRecipeWrapper::new, CategoryUIDs.CAMPFIRE);
		registry.addRecipes(CampfireRecipeHandler.getRecipes(), CategoryUIDs.CAMPFIRE);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.campfire), CategoryUIDs.CAMPFIRE);

		registry.handleRecipes(AdvancedRecipe.class, AdvancedRecipeWrapper::new, CategoryUIDs.ADV_CRAFTING);
		registry.addRecipes(AdvancedCraftingHandler.getRecipeList(), CategoryUIDs.ADV_CRAFTING);
		registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.doubleWorkbench), CategoryUIDs.ADV_CRAFTING);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(
				new OvenRecipeCategory(guiHelper),
				new KilnRecipeCategory(guiHelper),
				new SmelterRecipeCategory(guiHelper),
				new CampfireRecipeCategory(guiHelper),
				new AdvancedCraftingRecipeCategory(guiHelper));
	}

	void registerDescriptions(IModRegistry registry)
	{
		//Register item descriptions
		/*List<ItemStack> knifeList = Lists.newArrayList(new ItemStack(RegisterItems.flintKnife), new ItemStack(RegisterItems.boneKnife), new ItemStack(RegisterItems.goldKnife), new ItemStack(RegisterItems.ironKnife), new ItemStack(RegisterItems.diamondKnife));
		registry.addIngredientInfo(knifeList, ItemStack::class, getDescriptionKey("knives"));
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
		registerDescription(registry, RegisterBlocks.wickerBasket, 0);*/
	}

	private void blacklistItems(IIngredientBlacklist blacklist) {
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
