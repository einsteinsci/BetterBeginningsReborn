package net.einsteinsci.betterbeginnings.integration.jei.wrappers;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.KilnRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class JEIKilnRecipeWrapper implements IRecipeWrapper {
    RecipeElement input;
    ItemStack output;

    public JEIKilnRecipeWrapper(KilnRecipe recipe) {
        this.input = recipe.getInput();
        this.output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, input.getValidItems());
        ingredients.setOutput(ItemStack.class, output);
    }
}
