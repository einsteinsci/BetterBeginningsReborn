package net.einsteinsci.betterbeginnings.integration.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.integration.jei.wrappers.JEISmelterRecipeWrapper;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class JEISmelterRecipeCategory implements IRecipeCategory<JEISmelterRecipeWrapper> {
    private final String localisedName;
    private final IDrawable background;
    private final IDrawableAnimated flame;
    private final IDrawableAnimated arrow;

    public JEISmelterRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation smelterLoc = new ResourceLocation(ModMain.MODID, "textures/gui/container/smelter.png");
        localisedName = I18n.format(ModMain.MODID + ".smelterCategory.name");
        background = guiHelper.createDrawable(smelterLoc, 38, 9, 110, 67);

        IDrawableStatic staticFlame = guiHelper.createDrawable(smelterLoc, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(staticFlame, 300, StartDirection.TOP, true);

        IDrawableStatic staticArrow = guiHelper.createDrawable(smelterLoc, 176, 14, 24, 17);
        arrow = guiHelper.createAnimatedDrawable(staticArrow, 200, StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return JEICategoryUIDs.SMELTER;
    }

    @Override
    public String getTitle() {
        return localisedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return "BetterBeginnings";
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, JEISmelterRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        guiStacks.init(TileEntitySmelterBase.INPUT, true, 7, 7);
        guiStacks.init(TileEntitySmelterBase.BOOSTER, true, 27, 7);
        guiStacks.init(TileEntitySmelterBase.OUTPUT, false, 76, 24);
        guiStacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        flame.draw(minecraft, 18, 27);
        arrow.draw(minecraft, 41, 25);
    }
}
