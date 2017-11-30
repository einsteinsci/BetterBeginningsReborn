package net.einsteinsci.betterbeginnings.register;

import java.util.Map;

import com.google.common.collect.Maps;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.items.ItemBonePickaxe;
import net.einsteinsci.betterbeginnings.items.ItemFlintHatchet;
import net.einsteinsci.betterbeginnings.items.ItemKnifeFlint;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityBB;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityEnderSmelter;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityKiln;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityObsidianKiln;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelter;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FuelRegistry
{
    private static Map<FuelConsumerType, FuelMap> consumerTypeToFuelMap = Maps.newEnumMap(FuelConsumerType.class);
    
    public enum FuelConsumerType
    {
	KILN,
	OBSIDIAN_KILN,
	OVEN,
	//NETHER_OVEN,
	SMELTER,
	ENDER_SMELTER,
	CAMPFIRE;

	public static FuelConsumerType getFromInstance(TileEntityBB consumerInstance)
	{
	    if(consumerInstance instanceof TileEntityKiln)
		return KILN;
	    else if (consumerInstance instanceof TileEntityObsidianKiln)
		return OBSIDIAN_KILN;
	    else if (consumerInstance instanceof TileEntityBrickOven)
		return OVEN;
	    /*else if (consumerInstance instanceof TileEntityNetherBrickOven)
		return NETHER_OVEN;*/
	    else if (consumerInstance instanceof TileEntitySmelter)
		return SMELTER;
	    else if (consumerInstance instanceof TileEntityEnderSmelter)
		return ENDER_SMELTER;
	    else if (consumerInstance instanceof TileEntityCampfire)
		return CAMPFIRE;
	    else
		return null;
	}
    }
    
    public static void registerFuel(FuelConsumerType type, ItemStack stack, int fuelValue)
    {
	consumerTypeToFuelMap.get(type).put(stack, fuelValue);
    }
    
    public static void unregisterFuel(FuelConsumerType type, ItemStack stack)
    {
	consumerTypeToFuelMap.get(type).remove(stack);
    }

    public static int getBurnTime(FuelConsumerType type, ItemStack stack)
    {
	if(stack.isEmpty()) return 0;
	return consumerTypeToFuelMap.get(type).getBurnTime(stack);
    }

    public static void addDefaultFuels()
    {
	consumerTypeToFuelMap.put(FuelConsumerType.KILN, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);

		if (item instanceof ItemBlock && block != Blocks.AIR)
		{
		    if (block.getDefaultState().getMaterial() == Material.WOOD)
		    {
			return 300;
		    }
		    if(block == Blocks.SAPLING) return 100;
		    if(block == Blocks.COAL_BLOCK) return 16000;
		    if(block == Blocks.WOODEN_SLAB) return 150;
		}

		// It may be "wood", but it is not fuel.
		if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe ||
			item instanceof ItemFlintHatchet)
		{
		    return 0;
		}

		if (item instanceof ItemTool)
		{
		    if (((ItemTool)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemTool)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemSword)
		{
		    if (((ItemSword)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemSword)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemHoe)
		{
		    if (((ItemHoe)item).getMaterialName().equals("WOOD") ||
			    ((ItemHoe)item).getMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if(item == Items.COAL) return 1600;
		if(item == Items.STICK) return 100;
		if(item == Items.BLAZE_ROD || item == Items.LAVA_BUCKET) return 0;

		return GameRegistry.getFuelValue(fuel);
	    }
	}));
	
	consumerTypeToFuelMap.put(FuelConsumerType.OBSIDIAN_KILN, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);

		if (item instanceof ItemBlock && block != Blocks.AIR)
		{
		    if (block.getDefaultState().getMaterial() == Material.WOOD)
		    {
			return 300;
		    }
		    if(block == Blocks.SAPLING) return 100;
		    if(block == Blocks.COAL_BLOCK) return 16000;
		    if(block == Blocks.WOODEN_SLAB) return 150;
		}

		// It may be "wood", but it is not fuel.
		if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe ||
			item instanceof ItemFlintHatchet)
		{
		    return 0;
		}

		if (item instanceof ItemTool)
		{
		    if (((ItemTool)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemTool)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemSword)
		{
		    if (((ItemSword)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemSword)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemHoe)
		{
		    if (((ItemHoe)item).getMaterialName().equals("WOOD") ||
			    ((ItemHoe)item).getMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if(item == Items.COAL) return 1600;
		if(item == Items.STICK) return 100;
		if(item == Items.BLAZE_ROD) return 1600;
		if(item == Items.LAVA_BUCKET) return 80000;

		return GameRegistry.getFuelValue(fuel);
	    }
	}));

	consumerTypeToFuelMap.put(FuelConsumerType.OVEN, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);

		if (item instanceof ItemBlock && block != Blocks.AIR)
		{
		    if (block.getDefaultState().getMaterial() == Material.WOOD)
		    {
			return 300;
		    }
		    if(block == Blocks.SAPLING) return 100;
		    if(block == Blocks.COAL_BLOCK) return 16000;
		    if(block == Blocks.WOODEN_SLAB) return 150;
		}

		// It may be "wood", but it is not fuel.
		if (item instanceof ItemKnifeFlint || item instanceof ItemBonePickaxe ||
			item instanceof ItemFlintHatchet)
		{
		    return 0;
		}

		if (item instanceof ItemTool)
		{
		    if (((ItemTool)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemTool)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemSword)
		{
		    if (((ItemSword)item).getToolMaterialName().equals("WOOD") ||
			    ((ItemSword)item).getToolMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if (item instanceof ItemHoe)
		{
		    if (((ItemHoe)item).getMaterialName().equals("WOOD") ||
			    ((ItemHoe)item).getMaterialName().equals("noobwood"))
		    {
			return 200;
		    }
		}
		if(item == Items.COAL) return 1600;
		if(item == Items.STICK) return 100;
		if(item == Items.BLAZE_ROD || item == Items.LAVA_BUCKET) return 0;

		return GameRegistry.getFuelValue(fuel);
	    }
	}));
	
	consumerTypeToFuelMap.put(FuelConsumerType.SMELTER, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);
		//Coal
		if (item == Items.COAL) return (BBConfig.smeltersCanUseCoal || fuel.getItemDamage() == 1) ? 1600 : 0;
		if(item == Items.BLAZE_ROD) return 600;
		if(item == Items.LAVA_BUCKET) return 7200;
		
		if(block == Blocks.SAPLING) return 100;
		if(block == Blocks.COAL_BLOCK) return 16000;
		if(block == Blocks.WOODEN_SLAB) return 150;
		
		return GameRegistry.getFuelValue(fuel);
	    }
	}));
	
	consumerTypeToFuelMap.put(FuelConsumerType.ENDER_SMELTER, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Item item = fuel.getItem();
		Block block = Block.getBlockFromItem(item);
		//Coal
		if (item == Items.COAL) return (BBConfig.smeltersCanUseCoal || fuel.getItemDamage() == 1) ? 1600 : 0;
		if(item == Items.BLAZE_ROD) return 600;
		if(item == Items.LAVA_BUCKET) return 7200;
		if(item == Items.ENDER_PEARL) return 1200;
		if(item == Items.ENDER_EYE) return 2400;
		
		if(block == Blocks.SAPLING) return 100;
		if(block == Blocks.COAL_BLOCK) return 16000;
		if(block == Blocks.WOODEN_SLAB) return 150;
		
		return GameRegistry.getFuelValue(fuel);
	    }
	}));
	
	consumerTypeToFuelMap.put(FuelConsumerType.CAMPFIRE, new FuelMap(new IFuelHandler()
	{
	    @Override
	    public int getBurnTime(ItemStack fuel)
	    {
		Block block = Block.getBlockFromItem(fuel.getItem());
		Item item = fuel.getItem();

		if (block != null)
		{
		    if (block.getDefaultState().getMaterial() == Material.WOOD)
		    {
			return 600;
		    }
		    if(block == Blocks.WOODEN_SLAB) return 300;
		    if(block == Blocks.SAPLING) return 200;
		}

		if (item != null)
		{
		    if (item instanceof ItemBonePickaxe || item instanceof ItemFlintHatchet ||
			    item instanceof ItemKnifeFlint)
		    {
			return 0;
		    }

		    if (item instanceof ItemTool)
		    {
			if (((ItemTool)item).getToolMaterialName().equals("WOOD") ||
				((ItemTool)item).getToolMaterialName().equals("noobwood"))
			{
			    return 400;
			}
		    }
		    if (item instanceof ItemSword)
		    {
			if (((ItemSword)item).getToolMaterialName().equals("WOOD") ||
				((ItemSword)item).getToolMaterialName().equals("noobwood"))
			{
			    return 400;
			}
		    }
		    if (item instanceof ItemHoe)
		    {
			if (((ItemHoe)item).getMaterialName().equals("WOOD") ||
				((ItemHoe)item).getMaterialName().equals("noobwood"))
			{
			    return 400;
			}
		    }
		    if(item == Items.STICK) return 200;
		}

		return 0;
	    }
	}));
    }
    
    public static void reset()
    {
	consumerTypeToFuelMap.clear();
	addDefaultFuels();
    }
}
