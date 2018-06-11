package net.einsteinsci.betterbeginnings.crafttweaker.tweaker;

import java.util.Iterator;
import java.util.List;

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

@ZenClass("mods.betterbeginnings.Smelter")
public class SmelterTweaker {
    private static final String NAME = "Smelter";

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp, int boosters, int bonusPerBooster) {
        CraftTweakerAPI.apply(new AddSmelterRecipe(output, input, xp, boosters, bonusPerBooster));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp) {
        addRecipe(output, input, xp, 1, 1);
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        addRecipe(output, input, 0.4F);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient input) {
        CraftTweakerAPI.apply(new RemoveSmelterRecipe(output, input));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output) {
        CraftTweakerAPI.apply(new RemoveSmelterOutput(output));
    }

    private static class AddSmelterRecipe extends AddRemoveAction {
        private ItemStack output;
        private RecipeElement input;
        private float xp;
        private int boosters;
        private int bonus;

        private SmelterRecipe recipe;

        public AddSmelterRecipe(IItemStack output, IIngredient input, float xp, int boosters, int bonusPerBooster) {
            super(ActionType.ADD, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
            this.xp = xp;
            this.boosters = boosters;
            this.bonus = bonusPerBooster;
        }

        @Override
        public void apply() {
            recipe = SmelterRecipeHandler.addRecipe(input, output, xp, boosters, bonus);
            //CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(recipe);
        }

        @Override
        public String recipeToString() {
            return String.format("%s + %d booster -> %s", input, boosters, output);
        }

    }

    private static class RemoveSmelterRecipe extends AddRemoveAction {

        private ItemStack output;
        private RecipeElement input;
        private SmelterRecipe recipe;

        public RemoveSmelterRecipe(IItemStack output, IIngredient input) {
            super(ActionType.REMOVE, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
        }

        @Override
        public void apply() {
            this.recipe = SmelterRecipeHandler.removeRecipe(input, output);
            //if (recipe != null)
            //    CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
        }

        @Override
        public String recipeToString() {
            return String.format("%s -> %s", input, output);
        }

    }

    private static class RemoveSmelterOutput extends RemoveOutputAction {
        private ItemStack output;

        private List<SmelterRecipe> removedRecipes;

        public RemoveSmelterOutput(IItemStack output) {
            super(NAME);
            this.output = CraftTweakerMC.getItemStack(output);
        }

        @Override
        public void apply() {
            removedRecipes = Lists.newArrayList();
            for (Iterator<SmelterRecipe> iter = SmelterRecipeHandler.getRecipes().iterator(); iter.hasNext(); ) {
                SmelterRecipe recipe = iter.next();
                if (ItemStack.areItemsEqual(output, recipe.getOutput()) && ItemStack.areItemStackTagsEqual(output, recipe.getOutput())) {
                    iter.remove();
                    removedRecipes.add(recipe);
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
                }
            }
        }

        @Override
        public String recipeToString() {
            return output.toString();
        }
    }
}
