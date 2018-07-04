package net.einsteinsci.betterbeginnings.integration.jei.wrappers;


import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipe;
import net.minecraft.item.ItemStack;

import java.util.List;

public class JEICampfireRecipeWrapper implements IRecipeWrapper {
    static final List<ItemStack> UTENSIL_LIST = Lists.newArrayList(new ItemStack(RegisterItems.pan), new ItemStack(RegisterItems.rotisserie));

    List<List<ItemStack>> inputs = Lists.newArrayList();
    ItemStack output;

    public JEICampfireRecipeWrapper(CampfireRecipe recipe) {
        inputs.add(recipe.getInput().getValidItems());
        this.output = recipe.getOutput();
        if (recipe.requiresPan()) inputs.add(UTENSIL_LIST);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputLists(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, output);
    }
}
