package net.einsteinsci.betterbeginnings.integration.jei.wrappers;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class JEIAdvancedRecipeWrapper implements IRecipeWrapper {
    AdvancedRecipe recipe;

    public JEIAdvancedRecipeWrapper(AdvancedRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> recipeInputs = Lists.newArrayList();
        recipeInputs.addAll(getBaseMaterials());
        recipeInputs.addAll(getAdditionalMaterials());
        ingredients.setInputLists(ItemStack.class, recipeInputs);
        ingredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
    }

    public List<List<ItemStack>> getBaseMaterials() {
        List<List<ItemStack>> baseMaterials = Lists.newArrayList();
        for (RecipeElement element : recipe.recipeItems) {
            if (element == null) {
                baseMaterials.add(Collections.EMPTY_LIST);
                continue;
            }
            baseMaterials.add(element.getValidItems());
        }
        return baseMaterials;
    }

    public List<List<ItemStack>> getAdditionalMaterials() {
        List<List<ItemStack>> additionalMaterials = Lists.newArrayList();
        for (RecipeElement element : recipe.getNeededMaterials()) {
            if (element == null) {
                additionalMaterials.add(Collections.EMPTY_LIST);
                continue;
            }
            additionalMaterials.add(element.getValidItems());
        }
        return additionalMaterials;
    }

    public int getWidth() {
        return recipe.recipeWidth;
    }

    public int getHeight() {
        return recipe.recipeHeight;
    }

    public AdvancedRecipe getRecipe(){
        return recipe;
    }
}
