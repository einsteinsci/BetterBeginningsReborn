package net.einsteinsci.betterbeginnings.integration.jei.wrappers;


import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.IBrickOvenRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEIOvenRecipeWrapper implements IRecipeWrapper {
    IBrickOvenRecipe recipe;

    public JEIOvenRecipeWrapper(IBrickOvenRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> recipeInputs = Lists.newArrayList();
        for (RecipeElement element : recipe.getInputs())
            recipeInputs.add(element.getValidItems());
        ingredients.setInputLists(ItemStack.class, recipeInputs);
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }

    public IBrickOvenRecipe getRecipe() { return this.recipe; }
}
