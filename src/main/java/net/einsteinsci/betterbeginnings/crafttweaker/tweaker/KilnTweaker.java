package net.einsteinsci.betterbeginnings.crafttweaker.tweaker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.common.*;
import net.einsteinsci.betterbeginnings.register.recipe.*;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.betterbeginnings.Kiln")
public class KilnTweaker {
    private static final String NAME = "Kiln";

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp) {
        CraftTweakerAPI.apply(new AddKilnRecipe(input, output, xp));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        addRecipe(output, input, 0.1F);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient input) {
        CraftTweakerAPI.apply(new RemoveKilnRecipe(input, output));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output) {
        CraftTweakerAPI.apply(new RemoveKilnOutput(output));
    }

    private static class AddKilnRecipe extends AddRemoveAction {
        RecipeElement input;
        ItemStack output;
        float xp;

        KilnRecipeWrapper wrapper;

        private AddKilnRecipe(IIngredient input, IItemStack output, float xp) {
            super(ActionType.ADD, NAME);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
            this.output = CraftTweakerMC.getItemStack(output);
            this.xp = xp;
        }

        @Override
        public void apply() {
            KilnRecipeHandler.addRecipe(input, output, xp);
            wrapper = new KilnRecipeWrapper(input, output, xp);
            //CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(wrapper);
        }

        @Override
        public String recipeToString() {
            return input + " -> " + output;
        }
    }

    private static class RemoveKilnRecipe extends AddRemoveAction {
        RecipeElement input;
        ItemStack output;
        float xp;

        private RemoveKilnRecipe(IIngredient input, IItemStack output) {
            super(ActionType.REMOVE, NAME);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
            this.output = CraftTweakerMC.getItemStack(output);
            this.xp = KilnRecipeHandler.instance().giveExperience(this.output);
        }

        @Override
        public void apply() {
            KilnRecipeHandler.removeRecipe(input, output);
            //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new KilnRecipeWrapper(input, output, xp));
        }

        @Override
        public String recipeToString() {
            return input + " -> " + output;
        }
    }

    private static class RemoveKilnOutput extends RemoveOutputAction {
        ItemStack targetOutput;

        List<KilnRecipeWrapper> removedRecipes = Lists.newArrayList();

        private RemoveKilnOutput(IItemStack output) {
            super(NAME);
            this.targetOutput = CraftTweakerMC.getItemStack(output);
        }

        @Override
        public void apply() {
            Map.Entry<RecipeElement, ItemStack> recipe = null;
            for (Iterator<Map.Entry<RecipeElement, ItemStack>> iter = KilnRecipeHandler.getSmeltingList().entrySet().iterator(); iter.hasNext(); ) {
                recipe = iter.next();
                if (ItemStack.areItemsEqual(targetOutput, recipe.getValue()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getValue())) {
                    KilnRecipeWrapper wrapper = new KilnRecipeWrapper(recipe.getKey(), recipe.getValue(), KilnRecipeHandler.instance().giveExperience(recipe.getValue()));
                    removedRecipes.add(wrapper);
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(wrapper);
                    iter.remove();
                }
            }
        }

        @Override
        public String recipeToString() {
            return targetOutput.toString();
        }
    }
}
