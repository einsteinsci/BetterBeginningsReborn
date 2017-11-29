/*package net.einsteinsci.betterbeginnings.jei.wrappers;

import java.util.List;

import com.google.common.collect.Lists;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.minecraft.item.ItemStack;

public class JEICampfireRecipeWrapper extends BlankRecipeWrapper 
{	
	static final List<ItemStack> UTENSIL_LIST = Lists.newArrayList(new ItemStack(RegisterItems.pan), new ItemStack(RegisterItems.rotisserie));
	
	List<List<ItemStack>> inputs = Lists.newArrayList(); 
	ItemStack output;
	boolean requiresPan;

	public JEICampfireRecipeWrapper(RecipeElement input, ItemStack output, boolean requiresPan) 
	{
		inputs.add(input.getValidItems());
		this.output = output;
		if(requiresPan) inputs.add(UTENSIL_LIST);
	}

	@Override
	public void getIngredients(IIngredients ingredients) 
	{
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutput(ItemStack.class, output);
	}
}*/
