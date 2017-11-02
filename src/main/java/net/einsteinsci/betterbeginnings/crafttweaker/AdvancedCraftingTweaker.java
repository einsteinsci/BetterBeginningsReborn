package net.einsteinsci.betterbeginnings.crafttweaker;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.util.*;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.betterbeginnings.AdvancedCrafting")
public class AdvancedCraftingTweaker
{
    private static final String NAME = "Advanced Crafting";

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[][] inputs, IIngredient[] materials, boolean hide)
    {
	MineTweakerAPI.apply(new AddAdvancedRecipe(output, inputs, materials, hide));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient[][] inputs, IIngredient[] materials)
    {
	addRecipe(output, inputs, materials, false);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient[][] inputs, IIngredient[] materials)
    {
	MineTweakerAPI.apply(new RemoveAdvancedRecipe(output, inputs, materials));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output)
    {
	MineTweakerAPI.apply(new RemoveACTOutput(output));
    }

    private static class AddAdvancedRecipe extends AddRemoveAction
    {
	private ItemStack output; 
	private RecipeElement[] inputs;
	private RecipeElement[] materials;
	private boolean hide;
	private int width;
	private int height;

	private AdvancedRecipe addedRecipe;

	public AddAdvancedRecipe(IItemStack output, IIngredient[][] inputs, IIngredient[] materials, boolean hide)
	{
	    super(ActionType.ADD, NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	    this.inputs = CraftTweakerUtil.convertToRecipeElements1d(inputs);
	    this.materials = CraftTweakerUtil.convertToRecipeElements(materials);
	    this.hide = hide;
	    this.width = CraftTweakerUtil.computeWidth(inputs);
	    this.height = CraftTweakerUtil.computeHeight(inputs);
	}

	@Override
	public void apply()
	{
	    addedRecipe = new AdvancedRecipe(width, height, inputs, output, materials, hide);
	    AdvancedCraftingHandler.getRecipeList().add(addedRecipe);
	    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(addedRecipe);
	}

	@Override
	public void undo()
	{
	    AdvancedCraftingHandler.getRecipeList().remove(addedRecipe);
	    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(addedRecipe);
	}

	@Override
	public String recipeToString()
	{
	    return "[" + ArrayUtils.toString(inputs) + ", " + ArrayUtils.toString(materials) + "] -> " + output;
	}
    }

    private static class RemoveAdvancedRecipe extends AddRemoveAction
    {
	private ItemStack output; 
	private RecipeElement[] inputs;
	private RecipeElement[] materials;

	private AdvancedRecipe removedRecipe;

	public RemoveAdvancedRecipe(IItemStack output, IIngredient[][] inputs, IIngredient[] materials)
	{
	    super(ActionType.REMOVE, NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	    this.inputs = CraftTweakerUtil.convertToRecipeElements1d(inputs);
	    this.materials = CraftTweakerUtil.convertToRecipeElements(materials);
	}

	@Override
	public void apply()
	{
	    for(Iterator<AdvancedRecipe> iter = AdvancedCraftingHandler.getRecipeList().iterator(); iter.hasNext();)
	    {
		AdvancedRecipe recipe = iter.next();

		boolean inputsMatch = true;
		for(int i = 0; i < inputs.length; i++)
		{
		    if(inputs[i] != null)
		    {
			if(!inputs[i].equals(recipe.recipeItems[i]))
			{
			    inputsMatch = false;
			    break;
			}
		    }
		    else if(recipe.recipeItems[i] != null)
		    {
			inputsMatch = false;
			break;
		    }
		}

		boolean materialsMatch = true;
		for(int m = 0; m < materials.length; m++)
		{
		    if(!ArrayUtils.contains(recipe.getNeededMaterials(), materials[m]))
		    {
			materialsMatch = false;
			break;
		    }
		}
		if(inputsMatch && materialsMatch && ItemStack.areItemStacksEqual(output, recipe.getRecipeOutput()))
		{
		    removedRecipe = recipe;
		    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
		    iter.remove();
		    break;
		}
	    }
	}

	@Override
	public void undo()
	{
	    if(removedRecipe != null)
	    {
		AdvancedCraftingHandler.getRecipeList().add(removedRecipe);
		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(removedRecipe);
	    }
	}

	@Override
	public String recipeToString()
	{
	    return "[" + ArrayUtils.toString(inputs) + ", " + ArrayUtils.toString(materials) + "] -> " + output;
	}
    }

    private static class RemoveACTOutput extends RemoveOutputAction
    {
	private ItemStack output;

	private List<AdvancedRecipe> removedRecipes = Lists.newArrayList();

	public RemoveACTOutput(IItemStack output)
	{
	    super(NAME);
	    this.output = MineTweakerMC.getItemStack(output);
	}

	@Override
	public void apply()
	{
	    for(Iterator<AdvancedRecipe> iter = AdvancedCraftingHandler.getRecipeList().iterator(); iter.hasNext();)
	    {
		AdvancedRecipe recipe = iter.next();
		if(ItemStack.areItemsEqual(output, recipe.getRecipeOutput()) && ItemStack.areItemStackTagsEqual(output, recipe.getRecipeOutput()))
		{
		    removedRecipes.add(recipe);
		    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
		    iter.remove();
		}
	    }
	    
	}

	@Override
	public void undo()
	{
	    for(AdvancedRecipe recipe : removedRecipes)
	    {
		AdvancedCraftingHandler.getRecipeList().add(recipe);
		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
	    }
	}

	@Override
	public String recipeToString()
	{
	    return output.toString();
	}
    }
}
