package net.einsteinsci.betterbeginnings.crafttweaker.util;

import java.lang.reflect.Field;

import crafttweaker.api.item.*;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.oredict.IOreDictEntry;
import crafttweaker.api.oredict.IngredientOreDict;
import net.einsteinsci.betterbeginnings.register.recipe.elements.*;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class CraftTweakerUtil {
    private static Field ingredientStack$ingredient;

    static {
        ingredientStack$ingredient = ReflectionHelper.findField(IngredientStack.class, "ingredient");
        ingredientStack$ingredient.setAccessible(true);
    }

    public static RecipeElement convertToRecipeElement(IIngredient ingredient) {
        if (ingredient instanceof IItemStack || ingredient instanceof IngredientItem) {
            return new StackRecipeElement(MineTweakerMC.getItemStack(ingredient));
        }

        //Oredict entries
        if (ingredient instanceof IOreDictEntry) {
            IOreDictEntry oreEntry = (IOreDictEntry) ingredient;
            return new OreRecipeElement(oreEntry.getName(), oreEntry.getAmount());
        }

        if (ingredient instanceof IngredientOreDict) {
            IngredientOreDict oreEntry = (IngredientOreDict) ingredient;
            if (oreEntry.getInternal() instanceof String)
                return new OreRecipeElement((String) oreEntry.getInternal(), oreEntry.getAmount());
        }

        //Ugh
        if (ingredient instanceof IngredientStack) {
            IngredientStack ingredientStack = (IngredientStack) ingredient;
            try {
                IIngredient internalIngredient = (IIngredient) ingredientStack$ingredient.get(ingredientStack);
                if (internalIngredient instanceof IItemStack || internalIngredient instanceof IngredientItem) {
                    StackRecipeElement recElement = new StackRecipeElement(CraftTweakerMC.getItemStack(internalIngredient));
                    recElement.setStackSize(ingredientStack.getAmount());
                    return recElement;
                }

                //Oredict entries
                if (internalIngredient instanceof IOreDictEntry) {
                    IOreDictEntry oreEntry = (IOreDictEntry) internalIngredient;
                    return new OreRecipeElement(oreEntry.getName(), ingredientStack.getAmount());
                }

                if (internalIngredient instanceof IngredientOreDict) {
                    IngredientOreDict oreEntry = (IngredientOreDict) internalIngredient;
                    if (oreEntry.getInternal() instanceof String)
                        return new OreRecipeElement((String) oreEntry.getInternal(), ingredientStack.getAmount());
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static RecipeElement[] convertToRecipeElements(IIngredient[] ingredients) {
        RecipeElement[] ret = new RecipeElement[ingredients.length];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = convertToRecipeElement(ingredients[i]);
        }
        return ret;
    }

    public static RecipeElement[][] convertToRecipeElements(IIngredient[][] ingredients) {
        RecipeElement[][] ret = new RecipeElement[ingredients.length][ingredients[0].length];
        for (int i = 0; i < ingredients.length; i++) {
            for (int j = 0; j < ingredients[i].length; j++) {
                ret[i][j] = convertToRecipeElement(ingredients[i][j]);
            }
        }
        return ret;

    }

    public static RecipeElement[] convertToRecipeElements1d(IIngredient[][] ingredients) {
        RecipeElement[] ret = new RecipeElement[ingredients.length * ingredients[0].length];
        for (int c = 0; c < ingredients.length; c++) {
            for (int r = 0; r < ingredients[c].length; r++) {
                ret[c * ingredients[c].length + r] = convertToRecipeElement(ingredients[c][r]);
            }
        }
        return ret;

    }

    public static <T> int computeWidth(T[][] inputs) {
        int width = 0;
        for (int c = 0; c < inputs.length; c++) {
            int rowWidth = inputs[c].length;
            ;
            for (int r = inputs[c].length - 1; r >= 0; r--) {
                if (inputs[c][r] == null) rowWidth--;
                else break;
            }
            if (rowWidth > width) width = rowWidth;
        }
        return width;
    }

    public static <T> int computeHeight(T[][] inputs) {
        int height = inputs.length;
        for (int c = inputs.length - 1; c >= 0; c--) {
            if (isRowEmpty(inputs, c)) height--;
            else break;
        }
        return height;
    }

    private static <T> boolean isRowEmpty(T[][] inputs, int row) {
        for (int e = 0; e < inputs[row].length; e++) {
            if (inputs[row][e] != null) return false;
        }
        return true;
    }

    public static RecipeElement[] to1dArray(RecipeElement[][] inputs) {
        RecipeElement[] ret = new RecipeElement[inputs.length * inputs[0].length];
        for (int c = 0; c < inputs.length; c++) {
            for (int r = 0; r < inputs[c].length; r++) {
                ret[c * inputs[c].length + r] = inputs[c][r];
            }
        }
        return ret;
    }
}
