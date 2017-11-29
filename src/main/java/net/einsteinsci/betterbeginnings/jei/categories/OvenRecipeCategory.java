/*package net.einsteinsci.betterbeginnings.jei.categories;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.gui.IDrawableAnimated.StartDirection;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.wrappers.IOvenRecipeWrapper;
import net.einsteinsci.betterbeginnings.jei.wrappers.ShapedOvenRecipeWrapper;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityOvenBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class OvenRecipeCategory extends BlankRecipeCategory<IOvenRecipeWrapper> 
{
	private static final int CRAFTING_GRID_OFFSET = 7;
	private static final int CRAFTING_SLOT_OFFSET = 18;
	
	private final String localisedName;
	private final ICraftingGridHelper craftingGridHelper;
	private final IDrawable background;
	private final IDrawableAnimated flame;
	private final IDrawableAnimated arrow;
	
	public OvenRecipeCategory(IGuiHelper guiHelper) 
	{
		craftingGridHelper = guiHelper.createCraftingGridHelper(TileEntityOvenBase.INPUTSTART, TileEntityOvenBase.OUTPUT);
		ResourceLocation ovenLoc = new ResourceLocation(ModMain.MODID, "textures/gui/container/brickOven.png");
		localisedName = I18n.format(ModMain.MODID + ".ovenCategory.name");
		background = guiHelper.createDrawable(ovenLoc, 22, 9, 130, 72);
		
		IDrawableStatic staticFlame = guiHelper.createDrawable(ovenLoc, 176, 0, 14, 14); 
		flame = guiHelper.createAnimatedDrawable(staticFlame, 300, StartDirection.TOP, true);
		
		IDrawableStatic staticArrow = guiHelper.createDrawable(ovenLoc, 176, 14, 24, 17); 
		arrow = guiHelper.createAnimatedDrawable(staticArrow, 200, StartDirection.LEFT, false);
	}
	
	@Override
	public String getUid() 
	{
		return CategoryUIDs.OVEN;
	}

	@Override
	public String getTitle() 
	{
		return localisedName;
	}

	@Override
	public IDrawable getBackground() 
	{
		return background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IOvenRecipeWrapper recipeWrapper, IIngredients ingredients) 
	{
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		guiStacks.init(TileEntityOvenBase.OUTPUT, false, 100, 10);
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				guiStacks.init(TileEntityOvenBase.INPUTSTART + x + (3 * y), true, CRAFTING_GRID_OFFSET + CRAFTING_SLOT_OFFSET * x, CRAFTING_GRID_OFFSET + CRAFTING_SLOT_OFFSET * y);
			}
		}
		if(recipeWrapper instanceof ShapedOvenRecipeWrapper)
		{
			ShapedOvenRecipeWrapper shapedWrapper = (ShapedOvenRecipeWrapper) recipeWrapper;
			craftingGridHelper.setInputStacks(guiStacks, ingredients.getInputs(ItemStack.class), shapedWrapper.getWidth(), shapedWrapper.getHeight());
		}
		else
		{
			craftingGridHelper.setInputStacks(guiStacks, ingredients.getInputs(ItemStack.class));
		}
		craftingGridHelper.setOutput(guiStacks, ingredients.getOutputs(ItemStack.class));
	}
	
	@Override
	public void drawAnimations(Minecraft minecraft) 
	{
		flame.draw(minecraft, 70, 32);
		arrow.draw(minecraft, 67, 11);
	}
}*/
