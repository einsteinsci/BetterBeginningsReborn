package net.einsteinsci.betterbeginnings.crafttweaker.tweaker;

import com.google.common.collect.Lists;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.common.AddRemoveAction;
import net.einsteinsci.betterbeginnings.crafttweaker.common.CraftTweakerUtil;
import net.einsteinsci.betterbeginnings.crafttweaker.common.RemoveOutputAction;
import net.einsteinsci.betterbeginnings.register.recipe.CampfirePanRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.CampfireRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        CampfireRecipeWrapper wrapper;

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
            if (isPan) {
                CampfirePanRecipeHandler.addRecipe(input, output, xp);
            } else {
                CampfireRecipeHandler.addRecipe(input, output, xp);
            }
            wrapper = new CampfireRecipeWrapper(input, output, xp, isPan);
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
            if (isPan) {
                CampfirePanRecipeHandler.removeRecipe(input, output);
                xp = CampfirePanRecipeHandler.instance().giveExperience(output);
            } else {
                CampfireRecipeHandler.removeRecipe(input, output);
                xp = CampfireRecipeHandler.instance().giveExperience(output);
            }
            //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(new CampfireRecipeWrapper(input, output, xp, isPan));
        }

        @Override
        public String recipeToString() {
            return input + " -> " + output;
        }
    }

    private static class RemoveCampfireOutput extends RemoveOutputAction {
        ItemStack targetOutput;

        List<CampfireRecipeWrapper> removedRecipes = Lists.newArrayList();

        private RemoveCampfireOutput(IItemStack output) {
            super(NAME);
            this.targetOutput = CraftTweakerMC.getItemStack(output);
        }

        @Override
        public void apply() {
            Map.Entry<RecipeElement, ItemStack> recipe = null;
            for (Iterator<Map.Entry<RecipeElement, ItemStack>> iter = CampfireRecipeHandler.getSmeltingList().entrySet().iterator(); iter.hasNext(); ) {
                recipe = iter.next();
                if (ItemStack.areItemsEqual(targetOutput, recipe.getValue()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getValue())) {
                    CampfireRecipeWrapper wrapper = new CampfireRecipeWrapper(recipe.getKey(), recipe.getValue(), CampfireRecipeHandler.instance().giveExperience(recipe.getValue()), false);
                    removedRecipes.add(wrapper);
                    //CraftTweakerAPI.getIjeiRecipeRegistry().removeRecipe(wrapper);
                    iter.remove();
                }
            }
            for (Iterator<Map.Entry<RecipeElement, ItemStack>> iter = CampfirePanRecipeHandler.getSmeltingList().entrySet().iterator(); iter.hasNext(); ) {
                recipe = iter.next();
                if (ItemStack.areItemsEqual(targetOutput, recipe.getValue()) && ItemStack.areItemStackTagsEqual(targetOutput, recipe.getValue())) {
                    CampfireRecipeWrapper wrapper = new CampfireRecipeWrapper(recipe.getKey(), recipe.getValue(), CampfirePanRecipeHandler.instance().giveExperience(recipe.getValue()), true);
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
