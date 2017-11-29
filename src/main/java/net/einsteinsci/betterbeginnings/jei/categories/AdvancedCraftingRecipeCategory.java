/*package net.einsteinsci.betterbeginnings.jei.categories;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.jei.wrappers.AdvancedRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AdvancedCraftingRecipeCategory extends BlankRecipeCategory<AdvancedRecipeWrapper> 
{
	public static final int OUTPUT = 0;
	public static final int GRID_START = 1;
	public static final int SIDE_MAT_START = 10;
	private static final int CRAFTING_GRID_OFFSET_X = 34;
	private static final int CRAFTING_GRID_OFFSET_Y = 13;
	private static final int CRAFTING_SLOT_SPACING = 18;
	
	private final String localisedName;
	private final ICraftingGridHelper craftingGridHelper;
	private final IDrawable background;
	
	public AdvancedCraftingRecipeCategory(IGuiHelper guiHelper) 
	{
		craftingGridHelper = guiHelper.createCraftingGridHelper(GRID_START, OUTPUT);
		ResourceLocation ovenLoc = new ResourceLocation(ModMain.MODID, "textures/gui/container/doubleWorkbench_MC18.png");
		localisedName = I18n.format(ModMain.MODID + ".advCraftingCategory.name");
		background = guiHelper.createDrawable(ovenLoc, 20, 3, 156, 77);
	}
	
	@Override
	public String getUid() 
	{
		return CategoryUIDs.ADV_CRAFTING;
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
	public void setRecipe(IRecipeLayout recipeLayout, AdvancedRecipeWrapper recipeWrapper, IIngredients ingredients) 
	{
		IGuiItemStackGroup guiStacks = recipeLayout.getItemStacks();
		guiStacks.init(OUTPUT, false, 128, 30);
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 3; x++)
			{
				guiStacks.init(GRID_START + x + (3 * y), true, CRAFTING_GRID_OFFSET_X + CRAFTING_SLOT_SPACING * x, CRAFTING_GRID_OFFSET_Y + CRAFTING_SLOT_SPACING * y);
			}
		}
		for(int s = 0; s < 4; s++)
		{
			guiStacks.init(SIDE_MAT_START + s, true, 7, 3 + CRAFTING_SLOT_SPACING * s);
		}
		craftingGridHelper.setInputStacks(guiStacks, recipeWrapper.getBaseMaterials(), recipeWrapper.getWidth(), recipeWrapper.getHeight());
		List<List<ItemStack>> additionalMaterials = recipeWrapper.getAdditionalMaterials(); 
		for(int addS = 0; addS < additionalMaterials.size(); addS++)
		{
			guiStacks.set(SIDE_MAT_START + addS, additionalMaterials.get(addS));
		}
		craftingGridHelper.setOutput(guiStacks, ingredients.getOutputs(ItemStack.class));
	}
	
	@Override
	public void drawAnimations(Minecraft minecraft) 
	{
	}
}*/
