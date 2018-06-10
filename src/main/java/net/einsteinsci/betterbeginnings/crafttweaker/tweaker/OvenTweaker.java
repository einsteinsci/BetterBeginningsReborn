package net.einsteinsci.betterbeginnings.crafttweaker.tweaker;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

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

@ZenClass("mods.betterbeginnings.Oven")
public class OvenTweaker {
    private static final String NAME = "Oven";

    @ZenMethod
    public static void addShapedRecipe(IItemStack output, IIngredient[][] inputs) {
        CraftTweakerAPI.apply(new AddShapedOvenRecipe(output, inputs));
    }

    @ZenMethod
    public static void addShapelessRecipe(IItemStack output, IIngredient[] inputs) {
        CraftTweakerAPI.apply(new AddShapelessOvenRecipe(output, inputs));
    }

    @ZenMethod
    public static void removeShapedRecipe(IItemStack output, IIngredient[][] inputs) {
        CraftTweakerAPI.apply(new RemoveShapedOvenRecipe(output, inputs));
    }

    @ZenMethod
    public static void removeShapelessRecipe(IItemStack output, IIngredient[] inputs) {
        CraftTweakerAPI.apply(new RemoveShapelessOvenRecipe(output, inputs));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output) {
        CraftTweakerAPI.apply(new RemoveOvenOutput(output));
    }

    private static class AddShapedOvenRecipe extends AddRemoveAction {
        private ItemStack output;
        private RecipeElement[][] inputs;

        private BrickOvenShapedRecipe addedRecipe;

        public AddShapedOvenRecipe(IItemStack output, IIngredient[][] inputs) {
            super(ActionType.ADD, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.inputs = CraftTweakerUtil.convertToRecipeElements(inputs);
        }

        @Override
        public void apply() {
            addedRecipe = new BrickOvenShapedRecipe(CraftTweakerUtil.computeWidth(inputs), CraftTweakerUtil.computeHeight(inputs), CraftTweakerUtil.to1dArray(inputs), output);
            BrickOvenRecipeHandler.getRecipeList().add(addedRecipe);
            //CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(addedRecipe);
        }

        @Override
        public String recipeToString() {
            return ArrayUtils.toString(inputs) + " -> " + output.toString();
        }
    }

    private static class AddShapelessOvenRecipe extends AddRemoveAction {
        private ItemStack output;
        private RecipeElement[] inputs;

        private BrickOvenShapelessRecipe addedRecipe;

        public AddShapelessOvenRecipe(IItemStack output, IIngredient[] inputs) {
            super(ActionType.ADD, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.inputs = CraftTweakerUtil.convertToRecipeElements(inputs);
        }

        @Override
        public void apply() {
            addedRecipe = new BrickOvenShapelessRecipe(output, inputs);
            BrickOvenRecipeHandler.getRecipeList().add(addedRecipe);
            //CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(addedRecipe);
        }

        @Override
        public String recipeToString() {
            return ArrayUtils.toString(inputs) + " -> " + output.toString();
        }
    }

    private static class RemoveShapedOvenRecipe extends AddRemoveAction {
        private ItemStack output;
        private RecipeElement[] inputs;

        private BrickOvenShapedRecipe removedRecipe;

        public RemoveShapedOvenRecipe(IItemStack output, IIngredient[][] inputs) {
            super(ActionType.REMOVE, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.inputs = CraftTweakerUtil.convertToRecipeElements1d(inputs);
        }

        @Override
        public void apply() {
            for (Iterator<IBrickOvenRecipe> iter = BrickOvenRecipeHandler.getRecipeList().iterator(); iter.hasNext(); ) {
                IBrickOvenRecipe recipe = iter.next();
                if (recipe instanceof BrickOvenShapelessRecipe || recipe.getInputs().length != inputs.length) continue;
                boolean inputsMatch = true;
                for (int e = 0; e < inputs.length; e++) {
                    if (inputs[e] != null) {
                        if (!inputs[e].equals(recipe.getInputs()[e])) {
                            inputsMatch = false;
                            break;
                        }
                    } else if (recipe.getInputs()[e] != null) {
                        inputsMatch = false;
                        break;
                    }
                }
                if (inputsMatch && ItemStack.areItemStacksEqual(output, recipe.getRecipeOutput())) {
                    removedRecipe = (BrickOvenShapedRecipe) recipe;
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
                    iter.remove();
                    break;
                }
            }
        }

        @Override
        public String recipeToString() {
            return ArrayUtils.toString(inputs) + " -> " + output.toString();
        }
    }

    private static class RemoveShapelessOvenRecipe extends AddRemoveAction {
        private ItemStack output;
        private RecipeElement[] inputs;

        private BrickOvenShapelessRecipe removedRecipe;

        public RemoveShapelessOvenRecipe(IItemStack output, IIngredient[] inputs) {
            super(ActionType.REMOVE, NAME);
            this.output = CraftTweakerMC.getItemStack(output);
            this.inputs = CraftTweakerUtil.convertToRecipeElements(inputs);
        }

        @Override
        public void apply() {
            for (Iterator<IBrickOvenRecipe> iter = BrickOvenRecipeHandler.getRecipeList().iterator(); iter.hasNext(); ) {
                IBrickOvenRecipe recipe = iter.next();
                if (recipe instanceof BrickOvenShapedRecipe) continue;
                boolean inputsMatch = true;
                for (int e = 0; e < inputs.length; e++) {
                    if (!ArrayUtils.contains(recipe.getInputs(), inputs[e])) {
                        inputsMatch = false;
                        break;
                    }
                }
                if (inputsMatch) {
                    removedRecipe = (BrickOvenShapelessRecipe) recipe;
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
                    iter.remove();
                    break;
                }
            }
        }

        @Override
        public String recipeToString() {
            return ArrayUtils.toString(inputs) + " -> " + output.toString();
        }
    }

    private static class RemoveOvenOutput extends RemoveOutputAction {
        private ItemStack output;

        private List<IBrickOvenRecipe> removedRecipes = Lists.newArrayList();

        public RemoveOvenOutput(IItemStack output) {
            super(NAME);
            this.output = CraftTweakerMC.getItemStack(output);
        }

        @Override
        public void apply() {
            for (Iterator<IBrickOvenRecipe> iter = BrickOvenRecipeHandler.getRecipeList().iterator(); iter.hasNext(); ) {
                IBrickOvenRecipe recipe = iter.next();
                if (ItemStack.areItemsEqual(output, recipe.getRecipeOutput()) && ItemStack.areItemStackTagsEqual(output, recipe.getRecipeOutput())) {
                    removedRecipes.add(recipe);
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(recipe);
                    iter.remove();
                }
            }
        }

        @Override
        public String recipeToString() {
            return output.toString();
        }
    }
}
