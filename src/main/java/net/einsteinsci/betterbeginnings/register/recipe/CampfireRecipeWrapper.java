package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class CampfireRecipeWrapper
{
    private RecipeElement input;
    private ItemStack output;
    private float xp;
    private boolean requiresPan;

    public CampfireRecipeWrapper(RecipeElement input, ItemStack output, float xp, boolean requiresPan) 
    {
	this.input = input;
	this.output = output;
	this.xp = xp;
	this.requiresPan = requiresPan;
    }
    
    public void add()
    {
	if(requiresPan)
	    CampfirePanRecipeHandler.addRecipe(input, output, xp);
	else
	    CampfireRecipeHandler.addRecipe(input, output, xp);
    }
    
    public RecipeElement getInput()
    {
	return input;
    }
    
    public ItemStack getOutput()
    {
	return output;
    }
    
    public float getXp()
    {
	return xp;
    }
    
    public boolean requiresPan()
    {
	return requiresPan;
    }
    
    @Override
    public boolean equals(Object obj)
    {
	if(this == obj) return true;
	if(obj instanceof CampfireRecipeWrapper)
	{
	    CampfireRecipeWrapper recipeWrapper = (CampfireRecipeWrapper) obj;
	    return input.equals(recipeWrapper.input);
	}
	return false;
    }
    
    @Override
    public int hashCode()
    {
        return input.hashCode();
    }
}
