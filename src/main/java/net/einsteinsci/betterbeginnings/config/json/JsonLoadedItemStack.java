package net.einsteinsci.betterbeginnings.config.json;

import net.einsteinsci.betterbeginnings.util.RegistryUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

import javax.annotation.Nullable;

// allows for damage values and stack sizes
public class JsonLoadedItemStack
{
	private String itemName;
	private boolean isOreDictionary;
	private boolean isWildcard;
	private int damage;
	private int stackSize;
	private NBTTagCompound nbt;
	
	public JsonLoadedItemStack(String name, int amount, int dmg)
	{
		this(name, amount, dmg, null);
	}

	public JsonLoadedItemStack(String name, int amount, int dmg, NBTTagCompound nbt)
	{
		itemName = name;
		damage = dmg;
		stackSize = amount;
		isOreDictionary = false;
		isWildcard = (dmg == OreDictionary.WILDCARD_VALUE);
		this.nbt = nbt;
	}

	public JsonLoadedItemStack(String name, int amount)
	{
		this(name, amount, null);
	}

	public JsonLoadedItemStack(String name, int amount, NBTTagCompound nbt)
	{
		this(name, amount, 0);
	}
	
	public JsonLoadedItemStack(String name, int amount, boolean wildcard)
	{
		this(name, amount, wildcard, null);
	}

	public JsonLoadedItemStack(String name, int amount, boolean wildcard, NBTTagCompound nbt)
	{
		itemName = name;
		damage = 0;
		stackSize = amount;
		isWildcard = wildcard;
		isOreDictionary = false;
		this.nbt = nbt;
	}
	
	public JsonLoadedItemStack(String name, boolean wildcard)
	{
		this(name, 1, wildcard, null);
	}

	public JsonLoadedItemStack(String name, boolean wildcard, NBTTagCompound nbt)
	{
		this(name, 1, wildcard);
	}

	public JsonLoadedItemStack(String name) 
	{
	    	this(name, null);
	}
	
	public JsonLoadedItemStack(String name, NBTTagCompound nbt)
	{
		this(name, 1, 0);
	}

	public static JsonLoadedItemStack makeOreDictionary(String entry, int amount)
	{
		JsonLoadedItemStack res = new JsonLoadedItemStack(entry, amount);
		res.isOreDictionary = true;
		return res;
	}

	public static JsonLoadedItemStack makeOreDictionary(String entry)
	{
		return makeOreDictionary(entry, 1);
	}

	public JsonLoadedItemStack(ItemStack stack)
	{
		this(RegistryUtil.getForgeName(stack), stack.getCount(), stack.getMetadata(), stack.getTagCompound());
	}

	public List<ItemStack> getItemStacks()
	{
		if (isOreDictionary)
		{
		    	
			return OreDictionary.getOres(itemName, false);
		}

		Item item = RegistryUtil.getItemFromRegistry(itemName);
		if (item == null) return Collections.emptyList();

		List<ItemStack> single = new ArrayList<>();
		ItemStack stack = new ItemStack(item, stackSize, getDamage());
		stack.setTagCompound(getNbt());
		single.add(stack);
		return single;
	}

	public ItemStack getFirstItemStackOrNull()
	{
		List<ItemStack> stacks = getItemStacks();
		if (stacks.isEmpty())
		{
			return null;
		}

		return stacks.get(0);
	}
	
	@Nullable
	public NBTTagCompound getNbt()
	{
	    return nbt;
	}

	public String getItemName()
	{
		return itemName;
	}

	public boolean isOreDictionary()
	{
		return isOreDictionary;
	}

	public boolean isWildcard()
	{
		return isWildcard;
	}
	
	public boolean isValid()
	{
	    return isOreDictionary ? OreDictionary.doesOreNameExist(itemName) : RegistryUtil.getItemFromRegistry(itemName) != null; 
	}

	public int getStackSize()
	{
		return stackSize;
	}

	public int getDamage()
	{
		if (isWildcard)
		{
			return OreDictionary.WILDCARD_VALUE;
		}

		return damage;
	}

	public int getDamageNoWildcard()
	{
		return damage;
	}
}
