package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class KilnRecipeWrapper
{
    private RecipeElement input;
    private ItemStack output;
    private float xp;

    public KilnRecipeWrapper(RecipeElement input, ItemStack output, float xp) 
    {
	this.input = input;
	this.output = output;
	this.xp = xp;
    }
    
    public void add()
    {
	KilnRecipeHandler.addRecipe(input, output, xp);
    }
    
    public RecipeElement getInput()
    {
	return input;
    }
    
    public ItemStack getOutput()
    {
	return output;
    }
}
