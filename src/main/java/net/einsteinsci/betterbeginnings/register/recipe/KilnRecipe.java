package net.einsteinsci.betterbeginnings.register.recipe;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class KilnRecipe {
    private RecipeElement input;
    private ItemStack output;
    private float xp;

    public KilnRecipe(RecipeElement input, ItemStack output, float xp) {
        this.input = input;
        this.output = output;
        this.xp = xp;
    }

    public RecipeElement getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public float getXP() { return xp; }
}
