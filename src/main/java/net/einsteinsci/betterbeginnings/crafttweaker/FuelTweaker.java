package net.einsteinsci.betterbeginnings.crafttweaker;

import java.util.Collections;
import java.util.List;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.minecraft.MineTweakerMC;
import net.einsteinsci.betterbeginnings.crafttweaker.util.CraftTweakerUtil;
import net.einsteinsci.betterbeginnings.register.FuelRegistry;
import net.einsteinsci.betterbeginnings.register.FuelRegistry.FuelConsumerType;
import net.einsteinsci.betterbeginnings.register.recipe.elements.OreRecipeElement;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.betterbeginnings.Fuels")
public class FuelTweaker
{   
    @ZenMethod
    public static void setFuelValue(String consumerName, IIngredient fuel, int fuelValue)
    {
	FuelConsumerType type = FuelConsumerType.valueOf(consumerName);
	if(type == null)
	{
	    MineTweakerAPI.logError(consumerName + " is not a valid fuel consumer name.");
	    return;
	}
	MineTweakerAPI.apply(new SetFuelValue(type, fuel, fuelValue));
    }

    @ZenMethod
    public static int getFuelValue(String consumerName, IIngredient fuel)
    {
	FuelConsumerType type = FuelConsumerType.valueOf(consumerName);
	if(type == null)
	{
	    MineTweakerAPI.logError(consumerName + " is not a valid fuel consumer name.");
	    return 0;
	}
	return FuelRegistry.getBurnTime(type, MineTweakerMC.getItemStack(fuel));
    }

    private static class SetFuelValue implements IUndoableAction
    {
	private final FuelConsumerType type;
	private final List<ItemStack> stacks;
	private final int fuelValue;

	public SetFuelValue(FuelConsumerType type, IIngredient fuel, int fuelValue)
	{
	    this.type = type;
	    this.fuelValue = fuelValue;

	    RecipeElement elementEquiv = CraftTweakerUtil.convertToRecipeElement(fuel);
	    if(elementEquiv instanceof OreRecipeElement)
	    {
		this.stacks = ((OreRecipeElement)elementEquiv).getValidItems();
	    }
	    else this.stacks = Collections.singletonList(MineTweakerMC.getItemStack(fuel));
	}

	@Override
	public void apply()
	{
	    for(ItemStack stack : stacks)
	    {
		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) MineTweakerAPI.logError(stack + " uses the damage wilcard. mods.betterbeginnings.FuelTweaker does not support the damage wildcard.");
		FuelRegistry.registerFuel(type, stack, fuelValue);
	    }
	}

	@Override
	public boolean canUndo()
	{
	    return true;
	}

	@Override
	public void undo()
	{
	    for(ItemStack stack : stacks)
	    {
		FuelRegistry.unregisterFuel(type, stack);
	    }
	}

	@Override
	public String describe()
	{
	    return "Setting fuel value for " + stacks + " in " + type.name() + " to " + fuelValue;
	}

	@Override
	public String describeUndo()
	{
	    return "Resetting fuel value for " + stacks + " in " + type.name() + " to default";
	}

	@Override
	public Object getOverrideKey() {return null;}
    }
}