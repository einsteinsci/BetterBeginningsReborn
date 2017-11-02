package net.einsteinsci.betterbeginnings.register.recipe.elements;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract class RecipeElement
{
    List<ItemStack> cachedValidItems;

    public abstract boolean matches(ItemStack stackGiven);

    public abstract boolean matchesCheckSize(ItemStack stackGiven);

    public List<ItemStack> getValidItems()
    {
	return cachedValidItems;
    }

    public abstract RecipeElement copy();
    
    public abstract ItemStack getFirst();

    public abstract int getStackSize();

    public abstract void setStackSize(int size);

    public abstract void writeToNBT(NBTTagCompound nbt);
    
    /**
     * @return A string suitable for presenting to the player
     */
    public abstract String toFriendlyString();
    
    @Override
    public abstract boolean equals(Object obj);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
