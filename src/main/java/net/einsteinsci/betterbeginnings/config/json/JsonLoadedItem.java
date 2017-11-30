package net.einsteinsci.betterbeginnings.config.json;

import java.util.*;

import net.einsteinsci.betterbeginnings.util.RegistryUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

// allows for damage values, but not stack sizes
public class JsonLoadedItem
{
	private String itemName;
	private boolean isOreDictionary;
	private boolean isWildcard;
	private int damage;

	public JsonLoadedItem(String name, int dmg)
	{
		itemName = name;
		damage = dmg;
		isOreDictionary = false;
		isWildcard = (dmg == OreDictionary.WILDCARD_VALUE);
	}

	public JsonLoadedItem(String name, boolean wildcard)
	{
		itemName = name;
		damage = 0;
		isWildcard = wildcard;
		isOreDictionary = false;
	}

	public JsonLoadedItem(String name)
	{
		this(name, 0);
	}

	public JsonLoadedItem(ItemStack stack)
	{
		this(RegistryUtil.getForgeName(stack), stack.getMetadata());
	}

	public static JsonLoadedItem makeOreDictionary(String entry)
	{
		JsonLoadedItem res = new JsonLoadedItem(entry);
		res.isOreDictionary = true;
		return res;
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
		single.add(new ItemStack(item, 1, getDamage()));
		return single;
	}

	public ItemStack getFirstItemStackOrNull()
	{
		List<ItemStack> stacks = getItemStacks();
		if (stacks.isEmpty())
		{
			return ItemStack.EMPTY;
		}

		return stacks.get(0);
	}

	// not sure if this will be useful in the future...
	public String getMinetweakerName()
	{
		if (isOreDictionary)
		{
			return "<ore:" + itemName + ">";
		}

		String dmgStr = ":" + damage;
		if (isWildcard)
		{
			dmgStr = ":-1";
		}
		else if (damage == 0)
		{
			dmgStr = "";
		}

		return "<" + itemName + dmgStr + ">";
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
