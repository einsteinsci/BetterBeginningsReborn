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

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public void register(IModRegistry registry) {


        blacklistItems(registry.getJeiHelpers().getIngredientBlacklist());
        //registerRecipeCategories(registry);
        //registerRecipeHandlers(registry);

        registry.handleRecipes(KilnRecipe.class, JEIKilnRecipeWrapper::new, JEICategoryUIDs.KILN);
        registry.addRecipes(KilnRecipeHandler.getRecipes(), JEICategoryUIDs.KILN);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.kiln), JEICategoryUIDs.KILN);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.obsidianKiln), JEICategoryUIDs.KILN);

        registry.handleRecipes(IBrickOvenRecipe.class, JEIOvenRecipeWrapper::new, JEICategoryUIDs.OVEN);
        registry.addRecipes(BrickOvenRecipeHandler.getRecipeList(), JEICategoryUIDs.OVEN);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.brickOven), JEICategoryUIDs.OVEN);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.netherBrickOven), JEICategoryUIDs.OVEN);

        registry.handleRecipes(SmelterRecipe.class, JEISmelterRecipeWrapper::new, JEICategoryUIDs.SMELTER);
        registry.addRecipes(SmelterRecipeHandler.getRecipes(), JEICategoryUIDs.SMELTER);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.smelter), JEICategoryUIDs.SMELTER);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.enderSmelter), JEICategoryUIDs.SMELTER);

        registry.handleRecipes(CampfireRecipe.class, JEICampfireRecipeWrapper::new, JEICategoryUIDs.CAMPFIRE);
        registry.addRecipes(CampfireRecipeHandler.getRecipes(), JEICategoryUIDs.CAMPFIRE);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.campfire));

        registry.handleRecipes(AdvancedRecipe.class, JEIAdvancedRecipeWrapper::new, JEICategoryUIDs.ADV_CRAFTING);
        registry.addRecipes(AdvancedCraftingHandler.getRecipeList(), JEICategoryUIDs.ADV_CRAFTING);
        registry.addRecipeCatalyst(new ItemStack(RegisterBlocks.doubleWorkbench));
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
                new JEIOvenRecipeCategory(guiHelper),
                new JEIKilnRecipeCategory(guiHelper),
                new JEISmelterRecipeCategory(guiHelper),
                new JEICampfireRecipeCategory(guiHelper),
                new JEIAdvancedCraftingRecipeCategory(guiHelper));
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

    void registerDescriptions(IModRegistry registry) {
        //Register item descriptions
		/*List<ItemStack> knifeList = Lists.newArrayList(new ItemStack(RegisterItems.flintKnife), new ItemStack(RegisterItems.boneKnife), new ItemStack(RegisterItems.goldKnife), new ItemStack(RegisterItems.ironKnife), new ItemStack(RegisterItems.diamondKnife));
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
		;*/
    }

    void blackListBlock(IIngredientBlacklist blacklist, Block block, int meta) {
        blacklist.addIngredientToBlacklist(new ItemStack(block));
    }

    void blackListItem(IIngredientBlacklist blacklist, Item item, int meta) {
        blacklist.addIngredientToBlacklist(new ItemStack(item));
    }

    void registerDescription(IModRegistry registry, Block block, int meta) {
        //registry.addDescription(new ItemStack(block), getDescriptionKey(block));
    }

    void registerDescription(IModRegistry registry, Item item, int meta) {
        //registry.addDescription(new ItemStack(item), getDescriptionKey(item));
    }

    String getDescriptionKey(String name) {
        return ModMain.MODID + "." + name + ".jeiDescription";
    }
}
