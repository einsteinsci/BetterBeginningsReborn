package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class CampfireRecipe {
    private RecipeElement input;
    private ItemStack output;
    private float xp;
    private boolean requiresPan;

    public CampfireRecipe(RecipeElement input, ItemStack output, float xp, boolean requiresPan) {
        this.input = input;
        this.output = output;
        this.xp = xp;
        this.requiresPan = requiresPan;
    }

    public RecipeElement getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public float getXp() {
        return xp;
    }

    public boolean requiresPan() {
        return requiresPan;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof CampfireRecipe) {
            CampfireRecipe recipeWrapper = (CampfireRecipe) obj;
            return input.equals(recipeWrapper.input);
        }
        return false;
    }

    public boolean equalByInput(ItemStack input, boolean requiresPan) {
        return (this.input.matches(input) && (this.requiresPan && requiresPan || !this.requiresPan));
    }

    @Override
    public int hashCode() {
        return input.hashCode();
    }
}
