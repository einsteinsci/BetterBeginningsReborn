package net.einsteinsci.betterbeginnings.register.recipe.elements;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.nbt.NBTTagCompound;

public class ElementRegistry
{
    private static final String ELEMENT_TYPE = "type";
    private static final String ELEMENT_TAG = "element_tag";
    
    
    private static BiMap<String, Class<? extends RecipeElement>> NAME_TO_CLASS = HashBiMap.<String, Class<? extends RecipeElement>>create(2);
    private static BiMap<Class<? extends RecipeElement>, String> CLASS_TO_NAME = NAME_TO_CLASS.inverse();
    
    public static void init()
    {
	registerElement("stack", StackRecipeElement.class);
	registerElement("ore", OreRecipeElement.class);
    }
    
    public static void registerElement(String id, Class<? extends RecipeElement> clazz)
    {
	NAME_TO_CLASS.put(id, clazz);
    }
    
    public static RecipeElement createElementFromNBT(NBTTagCompound nbt)
    {
	try
	{
	    Class<? extends RecipeElement> clazz = NAME_TO_CLASS.get(nbt.getString(ElementRegistry.ELEMENT_TYPE));
	    Constructor<? extends RecipeElement> ctor = clazz.getConstructor(NBTTagCompound.class);
	    return ctor.newInstance(nbt.getCompoundTag(ElementRegistry.ELEMENT_TAG));
	}
	catch (NoSuchMethodException e)
	{
	    System.out.println("Recipe elements must have a constructor that takes an NBTTagCompound!");
	    e.printStackTrace(); 
	}
	catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
	{
	    e.printStackTrace();
	}
	return null;
    }
    
    public static NBTTagCompound elementToNBT(RecipeElement element)
    {
	try
	{
	    NBTTagCompound tag = new NBTTagCompound();
	    tag.setString(ElementRegistry.ELEMENT_TYPE, CLASS_TO_NAME.get(element.getClass()));
	    NBTTagCompound nbt = new NBTTagCompound();
	    element.writeToNBT(nbt);
	    tag.setTag(ELEMENT_TAG, nbt);
	    return tag;
	}
	catch (IllegalArgumentException e)
	{
	    e.printStackTrace();
	}
	return null;
    }
}
