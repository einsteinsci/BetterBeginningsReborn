package net.einsteinsci.betterbeginnings.integration.crafttweaker.tweaker;

import com.google.common.collect.Lists;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.einsteinsci.betterbeginnings.integration.crafttweaker.common.AddRemoveAction;
import net.einsteinsci.betterbeginnings.integration.crafttweaker.common.CraftTweakerUtil;
import net.einsteinsci.betterbeginnings.integration.crafttweaker.common.RemoveOutputAction;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;

@ZenClass("mods.betterbeginnings.Campfire")
public class CampfireTweaker {
    private static final String NAME = "Campfire";

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input, float xp) {
        CraftTweakerAPI.apply(new AddCampfireRecipe(input, output, xp));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient input) {
        addRecipe(output, input, 0.2F);
    }

    @ZenMethod
    public static void addPanRecipe(IItemStack output, IIngredient input, float xp) {
        CraftTweakerAPI.apply(new AddCampfireRecipe(input, output, xp).setPan(true));
    }

    @ZenMethod
    public static void addPanRecipe(IItemStack output, IIngredient input) {
        addPanRecipe(output, input, 0.3F);
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output, IIngredient input) {
        CraftTweakerAPI.apply(new RemoveCampfireRecipe(input, output));
    }

    @ZenMethod
    public static void removePanRecipe(IItemStack output, IIngredient input) {
        CraftTweakerAPI.apply(new RemoveCampfireRecipe(input, output).setPan(true));
    }

    @ZenMethod
    public static void removeOutput(IItemStack output) {
        CraftTweakerAPI.apply(new RemoveCampfireOutput(output));
    }

    private static class AddCampfireRecipe extends AddRemoveAction {
        RecipeElement input;
        ItemStack output;
        float xp;
        boolean isPan;

        CampfireRecipe wrapper;

        private AddCampfireRecipe(IIngredient input, IItemStack output, float xp) {
            super(ActionType.ADD, NAME);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
            this.output = CraftTweakerMC.getItemStack(output);
            this.xp = xp;
        }

        public AddCampfireRecipe setPan(boolean isPan) {
            this.isPan = isPan;
            return this;
        }

        @Override
        public void apply() {
            CampfireRecipeHandler.addRecipe(input, output, xp, isPan);
            wrapper = new CampfireRecipe(input, output, xp, isPan);
            //CraftTweakerAPI.getIjeiRecipeRegistry().addRecipe(wrapper);
        }

        @Override
        public String recipeToString() {
            return input + " -> " + output;
        }
    }

    private static class RemoveCampfireRecipe extends AddRemoveAction {
        RecipeElement input;
        ItemStack output;
        float xp;

        boolean isPan;

        private RemoveCampfireRecipe(IIngredient input, IItemStack output) {
            super(ActionType.REMOVE, NAME);
            this.input = CraftTweakerUtil.convertToRecipeElement(input);
            this.output = CraftTweakerMC.getItemStack(output);
        }

        public RemoveCampfireRecipe setPan(boolean isPan) {
            this.isPan = isPan;
            return this;
        }

        @Override
        public void apply() {
            CampfireRecipeHandler.removeRecipe(input, output, isPan);
            xp = CampfireRecipeHandler.instance().giveExperience(output, isPan);
            //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new CampfireRecipe(input, output, xp, isPan));
        }

        @Override
        public String recipeToString() {
            return input + " -> " + output;
        }
    }

    private static class RemoveCampfireOutput extends RemoveOutputAction {
        ItemStack targetOutput;

        List<CampfireRecipe> removedRecipes = Lists.newArrayList();

        private RemoveCampfireOutput(IItemStack output) {
            super(NAME);
            this.targetOutput = CraftTweakerMC.getItemStack(output);
        }

        @Override
        public void apply() {
            CampfireRecipe recipe = null;
            for (Iterator<CampfireRecipe> iter = CampfireRecipeHandler.getRecipes().iterator(); iter.hasNext(); ) {
                recipe = iter.next();
                if (ItemStack.areItemsEqual(targetOutput, recipe.getOutput()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getOutput())) {
                    removedRecipes.add(recipe);
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
