package net.einsteinsci.betterbeginnings.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.wrappers.JEIKilnRecipeWrapper;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityKilnBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class JEIKilnRecipeCategory implements IRecipeCategory<JEIKilnRecipeWrapper> {
    private final String localisedName;
    private final IDrawable background;
    private final IDrawableAnimated flame;
    private final IDrawableAnimated arrow;

    public JEIKilnRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation kilnLoc = new ResourceLocation(ModMain.MODID, "textures/gui/container/kiln.png");
        localisedName = I18n.format(ModMain.MODID + ".kilnCategory.name");
        background = guiHelper.createDrawable(kilnLoc, 55, 16, 82, 54);

        IDrawableStatic staticFlame = guiHelper.createDrawable(kilnLoc, 176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(staticFlame, 300, StartDirection.TOP, true);

        IDrawableStatic staticArrow = guiHelper.createDrawable(kilnLoc, 176, 14, 24, 17);
        arrow = guiHelper.createAnimatedDrawable(staticArrow, 200, StartDirection.LEFT, false);
    }

    @Override
    public String getUid() {
        return JEICategoryUIDs.KILN;
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
    public void setRecipe(IRecipeLayout recipeLayout, JEIKilnRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
        guiStacks.init(TileEntityKilnBase.SLOT_INPUT, true, 0, 0);
        guiStacks.init(TileEntityKilnBase.SLOT_OUTPUT, false, 60, 18);
        guiStacks.set(ingredients);
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        flame.draw(minecraft, 2, 20);
        arrow.draw(minecraft, 24, 18);
    }
}
