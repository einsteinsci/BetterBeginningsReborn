package net.einsteinsci.betterbeginnings.jei.wrappers;

import java.awt.Color;
import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipe;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class SmelterRecipeWrapper implements IRecipeWrapper
{
	SmelterRecipe recipe;
	List<List<ItemStack>> inputs = Lists.newArrayList();

	public SmelterRecipeWrapper(SmelterRecipe recipe) 
	{
		this.recipe = recipe;
		inputs.add(recipe.getInput().getValidItems());
		inputs.add(getBoosterInputList(recipe.getBoosters()));
	}

	List<ItemStack> getBoosterInputList(final int reqBoosters)
	{
		final List<ItemStack> boosters = Lists.newArrayList();
		for(ItemStack booster : TileEntitySmelterBase.boosterRegistry.keySet())
		{
			ItemStack stack = booster.copy();
			stack.setCount(reqBoosters);
			boosters.add(stack);
		}
		return boosters;
	}

	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, recipe.getOutput());
	}
	
	@Override
	public void drawInfo(Minecraft mc, int recipeWidth, int recipeHeight, int mouseX, int mouseY) 
	{
		mc.fontRenderer.drawSplitString(I18n.format("smelter.jeiBonusText", recipe.getOutput().getCount()),
				75, 48, 48, Color.gray.getRGB());
	}
}
