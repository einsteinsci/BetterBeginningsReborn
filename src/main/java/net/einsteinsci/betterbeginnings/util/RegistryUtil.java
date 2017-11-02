package net.einsteinsci.betterbeginnings.util;

import java.util.*;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.oredict.OreDictionary;

public class RegistryUtil
{
	// fullID must be in the format "modid:unlocalizedName"
	public static Block getBlockFromRegistry(String fullID)
	{
		return Block.REGISTRY.getObject(new ResourceLocation(fullID));
	}

	public static Item getItemFromRegistry(String fullID)
	{
		return Item.REGISTRY.getObject(new ResourceLocation(fullID));
	}

	public static String getForgeName(Item item)
	{
		return item.getRegistryName().toString();
	}

	public static String getForgeName(Block block)
	{
		return block.getRegistryName().toString();
	}

	public static String getForgeName(ItemStack stack)
	{
		if (stack == null)
		{
			return null;
		}

		if (stack.getItem() instanceof ItemBlock)
		{
			Block b = Block.getBlockFromItem(stack.getItem());
			return getForgeName(b);
		}

		return getForgeName(stack.getItem());
	}

	public static String getModOwner(Item item)
	{
		String forgeName = getForgeName(item);

		String res = "";
		for (char c : forgeName.toCharArray())
		{
			if (c == ':')
			{
				break;
			}

			res += c;
		}

		return res;
	}

	public static String getCommonOreDictName(List<ItemStack> items)
	{
		List<String> commonNames = null;

		for (ItemStack is : items)
		{
			if (commonNames == null)
			{
				commonNames = getOreNames(is);
			}
			else
			{
				List<String> compared = getOreNames(is);

				Iterator<String> iter = commonNames.iterator();
				while (iter.hasNext())
				{
					String s = iter.next();

					if (!compared.contains(s))
					{
						iter.remove();
					}
				}

				if (commonNames.isEmpty())
				{
					return null;
				}
			}
		}

		if (commonNames == null || commonNames.isEmpty())
		{
			return null;
		}

		return commonNames.get(0);
	}

	public static List<String> getOreNames(ItemStack stack)
	{
		List<String> res = new ArrayList<>();
		for (int id : OreDictionary.getOreIDs(stack))
		{
			res.add(OreDictionary.getOreName(id));
		}
		return res;
	}

	// Kinda sketchy CLEANUP
	@SuppressWarnings("unchecked")
	public static Tuple<Boolean, Character> getRecipeCharacter(Map<Object, Character> map, Object obj, Character current)
	{
		boolean res = false;
		Character token = '0';

		if (obj instanceof ItemStack)
		{
			ItemStack stack = (ItemStack)obj;

			boolean found = false;
			for (Map.Entry<Object, Character> kvp : map.entrySet())
			{
				if (kvp.getKey() instanceof ItemStack)
				{
					ItemStack k = (ItemStack)kvp.getKey();
					if (Util.areItemStacksEqualIgnoreSize(k, stack))
					{
						token = kvp.getValue();
						found = true;
						break;
					}
				}
			}

			if (!found)
			{
				map.put(stack, token);
				res = true;
			}
		}
		else if (obj instanceof List)
		{
			String ore = null;
			try
			{
				ore = RegistryUtil.getCommonOreDictName((List<ItemStack>)obj);
			}
			catch (ClassCastException ex)
			{
				LogUtil.log(Level.ERROR, "Failed to cast list in ore dictionary conversion: " + ex.toString());
			}

			if (ore != null)
			{
				if (map.containsKey(ore))
				{
					token = map.get(ore);
				}
				else
				{
					map.put(ore, current);
					res = true;
				}
			}
		}

		return new Tuple<Boolean, Character>(res, token);
	}
}
