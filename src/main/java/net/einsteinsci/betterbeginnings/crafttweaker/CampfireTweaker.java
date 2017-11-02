package net.einsteinsci.betterbeginnings.crafttweaker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.util.AddRemoveAction;
import net.einsteinsci.betterbeginnings.crafttweaker.util.CraftTweakerUtil;
import net.einsteinsci.betterbeginnings.crafttweaker.util.RemoveOutputAction;
import net.einsteinsci.betterbeginnings.register.recipe.CampfirePanRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.betterbeginnings.Campfire")
public class CampfireTweaker
{
    private static final String NAME = "Campfire";
    
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp)
    {
	MineTweakerAPI.apply(new AddCampfireRecipe(input, output, xp));
    }
    
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input)
    {
	addRecipe(output, input, 0.2F);
    }

    @ZenMethod
    public static void addPanRecipe(IItemStack output, IIngredient input, float xp)
    {
	MineTweakerAPI.apply(new AddCampfireRecipe(input, output, xp).setPan(true));
    }

    @ZenMethod
    public static void addPanRecipe(IItemStack output, IIngredient input)
    {
	addPanRecipe(output, input, 0.3F);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient input)
    {
	MineTweakerAPI.apply(new RemoveCampfireRecipe(input, output));
    }	

    @ZenMethod
    public static void removePanRecipe(IItemStack output, IIngredient input)
    {
	MineTweakerAPI.apply(new RemoveCampfireRecipe(input, output).setPan(true));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output)
    {
	MineTweakerAPI.apply(new RemoveCampfireOutput(output));
    }

    private static class AddCampfireRecipe extends AddRemoveAction
    {
	RecipeElement input; 
	ItemStack output; 
	float xp;
	boolean isPan;
	
	CampfireRecipeWrapper wrapper;

	private AddCampfireRecipe(IIngredient input, IItemStack output, float xp)
	{
	    super(ActionType.ADD, NAME);
	    this.input = CraftTweakerUtil.convertToRecipeElement(input);
	    this.output = MineTweakerMC.getItemStack(output);
	    this.xp = xp;
	}

	public AddCampfireRecipe setPan(boolean isPan)
	{
	    this.isPan = isPan;
	    return this;
	}

	@Override
	public void apply()
	{
	    if(isPan)
	    {
		CampfirePanRecipeHandler.addRecipe(input, output, xp);
	    }
	    else
	    {
		CampfireRecipeHandler.addRecipe(input, output, xp);
	    }
	    wrapper = new CampfireRecipeWrapper(input, output, xp, isPan);
	    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(wrapper);
	}

	@Override
	public void undo()
	{
	    if(isPan)
	    {
		CampfirePanRecipeHandler.removeRecipe(input, output);
	    }
	    else
	    {
		CampfireRecipeHandler.removeRecipe(input, output);
	    }
	    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(wrapper);
	}

	@Override
	public String recipeToString()
	{
	    return input + " -> " + output;
	}
    }
    
    private static class RemoveCampfireRecipe extends AddRemoveAction
    {
	RecipeElement input; 
	ItemStack output; 
	float xp;
	
	boolean isPan;

	private RemoveCampfireRecipe(IIngredient input, IItemStack output)
	{
	    super(ActionType.REMOVE, NAME);
	    this.input = CraftTweakerUtil.convertToRecipeElement(input);
	    this.output = MineTweakerMC.getItemStack(output);
	}

	public RemoveCampfireRecipe setPan(boolean isPan)
	{
	    this.isPan = isPan;
	    return this;
	}

	@Override
	public void apply()
	{
	    if(isPan)
	    {
		CampfirePanRecipeHandler.removeRecipe(input, output);
		xp = CampfirePanRecipeHandler.instance().giveExperience(output);
	    }
	    else
	    {
		CampfireRecipeHandler.removeRecipe(input, output);
		xp = CampfireRecipeHandler.instance().giveExperience(output);
	    }
	    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new CampfireRecipeWrapper(input, output, xp, isPan));
	}

	@Override
	public void undo()
	{
	    if(isPan)
	    {
		CampfirePanRecipeHandler.addRecipe(input, output, xp);
	    }
	    else
	    {
		CampfireRecipeHandler.addRecipe(input, output, xp);
	    }
	    MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(new CampfireRecipeWrapper(input, output, xp, isPan));
	}

	@Override
	public String recipeToString()
	{
	    return input + " -> " + output;
	}
    }
    
    private static class RemoveCampfireOutput extends RemoveOutputAction
    {
	ItemStack targetOutput; 
	
	List<CampfireRecipeWrapper> removedRecipes = Lists.newArrayList();

	private RemoveCampfireOutput(IItemStack output)
	{
	    super(NAME);
	    this.targetOutput = MineTweakerMC.getItemStack(output);
	}

	@Override
	public void apply()
	{   
	    Map.Entry<RecipeElement, ItemStack> recipe = null;
	    for(Iterator<Map.Entry<RecipeElement, ItemStack>> iter = CampfireRecipeHandler.getSmeltingList().entrySet().iterator(); iter.hasNext();)
	    {
		recipe = iter.next();
		if(ItemStack.areItemsEqual(targetOutput, recipe.getValue()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getValue()))
		{
		    CampfireRecipeWrapper wrapper = new CampfireRecipeWrapper(recipe.getKey(), recipe.getValue(), CampfireRecipeHandler.instance().giveExperience(recipe.getValue()), false); 
		    removedRecipes.add(wrapper);
		    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(wrapper);
		    iter.remove();
		}
	    }
	    for(Iterator<Map.Entry<RecipeElement, ItemStack>> iter = CampfirePanRecipeHandler.getSmeltingList().entrySet().iterator(); iter.hasNext();)
	    {
		recipe = iter.next();
		if(ItemStack.areItemsEqual(targetOutput, recipe.getValue()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getValue()))
		{
		    CampfireRecipeWrapper wrapper = new CampfireRecipeWrapper(recipe.getKey(), recipe.getValue(), CampfirePanRecipeHandler.instance().giveExperience(recipe.getValue()), true);
		    removedRecipes.add(wrapper);
		    MineTweakerAPI.getIjeiRecipeRegistry().removeRecipe(wrapper);
		    iter.remove();
		}
	    }
	}
	
	@Override
	public void undo()
	{
	    for(CampfireRecipeWrapper recipe : removedRecipes)
	    {
		recipe.add();
		MineTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
	    }
	}

	@Override
	public String recipeToString()
	{
	    return targetOutput.toString();
	}
    }
}
