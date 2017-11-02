package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class SmelterRecipe
{
	private ItemStack outputStack;
	private RecipeElement inputStack;
	private float experienceGiven;
	// Booster items consumed per smelting operation
	private int boostersNeeded;
	// Bonus items given per level of boost upon smelting
	private int bonusPerBoost;

	public SmelterRecipe(ItemStack output, RecipeElement input, float experience, int boosters, int bonus)
	{
		outputStack = output;
		inputStack = input;
		experienceGiven = experience;
		boostersNeeded = boosters;
		bonusPerBoost = bonus;
	}

	public ItemStack getOutput()
	{
		return outputStack;
	}

	public RecipeElement getInput()
	{
		return inputStack;
	}

	public float getExperience()
	{
		return experienceGiven;
	}

	public int getBoosters()
	{
		return boostersNeeded;
	}

	public int getBonusPerBoost()
	{
		return bonusPerBoost;
	}
}
