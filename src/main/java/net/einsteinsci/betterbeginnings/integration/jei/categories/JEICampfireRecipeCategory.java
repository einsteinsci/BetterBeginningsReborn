package net.einsteinsci.betterbeginnings.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.wrappers.JEICampfireRecipeWrapper;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class JEICampfireRecipeCategory implements IRecipeCategory<JEICampfireRecipeWrapper> {
    private final String localisedName;
    private final IDrawable background;
    private final IDrawableAnimated campfire;
    private final IDrawableAnimated arrow;

    public JEICampfireRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation campfireLoc = new ResourceLocation(ModMain.MODID, "textures/gui/container/campfire.png");
        localisedName = I18n.format(ModMain.MODID + ".campfireCategory.name");
        background = guiHelper.createDrawable(campfireLoc, 24, 4, 121, 76);

        IDrawableStatic staticFlame = guiHelper.createDrawable(campfireLoc, 176, 0, 13, 21);
        campfire = guiHelper.createAnimatedDrawable(staticFlame, 300, StartDirection.TOP, true);

        IDrawableStatic staticArrow = guiHelper.createDrawable(campfireLoc, 176, 22, 23, 16);
        arrow = guiHelper.createAnimatedDrawable(staticArrow, 200, StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return JEICategoryUIDs.CAMPFIRE;
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
    public void setRecipe(IRecipeLayout recipeLayout, JEICampfireRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        guiStacks.init(TileEntityCampfire.SLOT_INPUT, true, 33, 7);
        guiStacks.init(TileEntityCampfire.SLOT_UTENSIL, true, 7, 30);
        guiStacks.init(TileEntityCampfire.SLOT_OUTPUT, false, 93, 29);
        guiStacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        campfire.draw(minecraft, 34, 28);
        arrow.draw(minecraft, 58, 29);
    }
}
