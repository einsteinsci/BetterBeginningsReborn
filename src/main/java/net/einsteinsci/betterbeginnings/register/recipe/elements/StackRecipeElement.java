package net.einsteinsci.betterbeginnings.register.recipe.elements;

import java.util.Collections;

import com.google.common.base.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class StackRecipeElement extends RecipeElement 
{
	private ItemStack stack;
	
	public StackRecipeElement(NBTTagCompound nbt)
	{
	    this(ItemStack.loadItemStackFromNBT(nbt));
	}

	public StackRecipeElement(ItemStack stack)
	{
		this.stack = stack;
		this.cachedValidItems = Collections.singletonList(stack);
	}

	public StackRecipeElement(Item item)
	{
		this(new ItemStack(item));
	}
	public StackRecipeElement(Item item, int count)
	{
		this(new ItemStack(item, count));
	}
	public StackRecipeElement(Item item, int count, int meta)
	{
		this(new ItemStack(item, count, meta));
	}

	public StackRecipeElement(Block block)
	{
		this(new ItemStack(block));
	}
	public StackRecipeElement(Block block, int count)
	{
		this(new ItemStack(block, count));
	}
	public StackRecipeElement(Block block, int count, int meta)
	{
		this(new ItemStack(block, count, meta));
	}
	
	@Override
	public ItemStack getFirst() 
	{
		return stack;
	}
	
	@Override
	public int getStackSize() 
	{
		return stack.stackSize;
	}

	@Override
	public void setStackSize(int size) 
	{
		stack.stackSize = size;
	}

	@Override
	public boolean matches(ItemStack stackGiven) 
	{
		return stack.getItem() == stackGiven.getItem() && (stack.getItemDamage() == stackGiven.getItemDamage() || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) && ItemStack.areItemStackTagsEqual(stack, stackGiven);
	}

	@Override
	public boolean matchesCheckSize(ItemStack stackGiven) 
	{
	    return stack.getItem() == stackGiven.getItem() && (stack.getItemDamage() == stackGiven.getItemDamage() || stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) && ItemStack.areItemStackTagsEqual(stack, stackGiven) && stack.stackSize <= stackGiven.stackSize;
	}
	
	public ItemStack getStack() 
	{
		return stack;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
	    stack.writeToNBT(nbt);
	}

	@Override
	public RecipeElement copy() 
	{
	    return new StackRecipeElement(stack.copy());
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(this == obj) return true;
		if(obj instanceof StackRecipeElement)
		{
			return ItemStack.areItemStacksEqual(((StackRecipeElement) obj).stack, this.stack);
		}
		return false;
	}

	@Override
	public int hashCode() 
	{
		return Objects.hashCode(stack.getItem(), stack.stackSize, stack.getItemDamage(), stack.getTagCompound());
	}
	
	@Override
	public String toString() 
	{
		return stack.toString();
	}
	
	@Override
	public String toFriendlyString()
	{
	    return stack.getDisplayName() + " x " + stack.stackSize;
	}
}
