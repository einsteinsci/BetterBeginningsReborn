package net.einsteinsci.betterbeginnings.crafttweaker;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.util.*;
import net.einsteinsci.betterbeginnings.register.recipe.*;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.betterbeginnings.Smelter")
public class SmelterTweaker
{
    private static final String NAME = "Smelter";

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp, int boosters, int bonusPerBooster)
    {
	MineTweakerAPI.apply(new AddSmelterRecipe(output, input, xp, boosters, bonusPerBooster));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp)
    {
	addRecipe(output, input, xp, 1, 1);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input)
    {
	addRecipe(output, input, 0.4F);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient input)
    {
	MineTweakerAPI.apply(new RemoveSmelterRecipe(output, input));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output)
    {
	MineTweakerAPI.apply(new RemoveSmelterOutput(output));
    }

    private static class AddSmelterRecipe extends AddRemoveAction
    {
	private ItemStack output;
	private RecipeElement input;
	private float xp;
	private int boosters;
	private int bonus;

	private SmelterRecipe recipe;

	public AddSmelterRecipe(IItemStack output, IIngredient input, float xp, int boosters, int bonusPerBooster)
	{
	    super(ActionType.ADD, NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	    this.input = CraftTweakerUtil.convertToRecipeElement(input);
	    this.xp = xp;
	    this.boosters = boosters;
	    this.bonus = bonusPerBooster;
	}

	@Override
	public void apply()
	{
	    recipe = SmelterRecipeHandler.addRecipe(input, output, xp, boosters, bonus);
	    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
	}

	@Override
	public void undo()
	{
	    SmelterRecipeHandler.getRecipes().remove(recipe);
	    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
	}

	@Override
	public String recipeToString()
	{
	    return String.format("%s + %d booster -> %s", input, boosters, output);
	}

    }

    private static class RemoveSmelterRecipe extends AddRemoveAction
    {

	private ItemStack output;
	private RecipeElement input;
	private SmelterRecipe recipe;

	public RemoveSmelterRecipe(IItemStack output, IIngredient input)
	{
	    super(ActionType.REMOVE, NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	    this.input = CraftTweakerUtil.convertToRecipeElement(input);
	}

	@Override
	public void apply()
	{
	    recipe = SmelterRecipeHandler.removeRecipe(input, output);
	    if(recipe != null)
		MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
	}

	@Override
	public void undo()
	{
	    if(recipe != null)
	    {
		SmelterRecipeHandler.getRecipes().add(recipe);
		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
	    }
	}

	@Override
	public String recipeToString()
	{
	    return String.format("%s -> %s", input, output);
	}

    }

    private static class RemoveSmelterOutput extends RemoveOutputAction
    {
	private ItemStack output;

	private List<SmelterRecipe> removedRecipes;

	public RemoveSmelterOutput(IItemStack output)
	{
	    super(NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	}

	@Override
	public void apply()
	{
	    removedRecipes = Lists.newArrayList();
	    for(Iterator<SmelterRecipe> iter = SmelterRecipeHandler.getRecipes().iterator(); iter.hasNext();)
	    {
		SmelterRecipe recipe = iter.next();
		if(ItemStack.areItemsEqual(output, recipe.getOutput()) && ItemStack.areItemStackTagsEqual(output, recipe.getOutput()))
		{
		    iter.remove();
		    removedRecipes.add(recipe);
		    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
		}
	    }
	}

	@Override
	public void undo()
	{
	    for(SmelterRecipe r : removedRecipes)
	    {
		SmelterRecipeHandler.getRecipes().add(r);
		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(r);
	    }
	    removedRecipes.clear();
	}

	@Override
	public String recipeToString()
	{
	    return output.toString();
	}
    }
}
