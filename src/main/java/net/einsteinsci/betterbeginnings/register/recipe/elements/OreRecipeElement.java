package net.einsteinsci.betterbeginnings.register.recipe.elements;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class OreRecipeElement extends RecipeElement 
{
    	private static final String OREDICT = "oredict";
    	private static final String SIZE = "size";
    
	String oreDictionaryEntry;
	int stackSize;

	public OreRecipeElement(NBTTagCompound nbt)
	{
	    this(nbt.getString(OREDICT), nbt.getInteger(SIZE));
	}
	
	public OreRecipeElement(String dictionaryEntry, final int size)
	{
		cachedValidItems = Lists.newArrayList();
		for(ItemStack stack : OreDictionary.getOres(dictionaryEntry, false)) 
		{
		    	ItemStack newStack = stack.copy();
		    	newStack.stackSize = size;
			cachedValidItems.add(newStack);
		}
		oreDictionaryEntry = dictionaryEntry;
		stackSize = size;
	}

	public OreRecipeElement(String dictionaryEntry)
	{
		this(dictionaryEntry, 1);
	}
	
	@Override
	public ItemStack getFirst() 
	{
		return cachedValidItems.get(0);
	}

	@Override
	public boolean matches(ItemStack stackGiven) 
	{
		for(int id : OreDictionary.getOreIDs(stackGiven))
		{
			if(oreDictionaryEntry.equals(OreDictionary.getOreName(id))) return true;
		}
		return false;
	}

	@Override
	public boolean matchesCheckSize(ItemStack stackGiven) 
	{
		if(stackSize > stackGiven.stackSize) return false;
		return matches(stackGiven);
	}
	
	public int getStackSize() 
	{
		return stackSize;
	}
	
	@Override
	public void setStackSize(int size) 
	{
		stackSize = size;
	}

	public String getOreDictionaryEntry() 
	{
		return oreDictionaryEntry;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
	    nbt.setString(OREDICT, oreDictionaryEntry);
	    nbt.setInteger(SIZE, stackSize);
	}

	@Override
	public RecipeElement copy() 
	{
		return new OreRecipeElement(oreDictionaryEntry, stackSize);
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(this == obj) return true;
		if(obj instanceof OreRecipeElement)
		{
			OreRecipeElement other = (OreRecipeElement) obj;
			return other.oreDictionaryEntry.equals(this.oreDictionaryEntry) && other.stackSize == this.stackSize;
		}
		return false;
	}

	@Override
	public int hashCode() 
	{
		return Objects.hashCode(this.oreDictionaryEntry, this.stackSize);
	}
	
	@Override
	public String toString() 
	{
		return oreDictionaryEntry + " x " + stackSize;
	}
	
	@Override
	public String toFriendlyString()
	{
	    return oreDictionaryEntry + " x " + stackSize;
	}
}
