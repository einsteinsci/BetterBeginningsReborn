package net.einsteinsci.betterbeginnings.util;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootingEnchantBonus;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraft.world.storage.loot.functions.SetNBT;

public class LootUtil
{
    public static final LootCondition[] NO_CONDITIONS = new LootCondition[0]; 
	
    public static void addItemToTable(LootTable table, String poolName, Item item, int weight, int minQuantity, int maxQuantity)
    {
	addItemToTable(table, poolName, item, weight, 1, createCountFunction(minQuantity, maxQuantity));
    }	

    public static void addItemToTable(LootTable table, String poolName, Item item, int weight, int quality, LootFunction... functions)
    {
	addItemToTable(table, poolName, item, weight, quality, functions, NO_CONDITIONS);
    }

    public static void addItemToTable(LootTable table, String poolName, Item item, int weight, int quality, LootFunction[] functions, LootCondition[] conditions)
    {
	table.getPool("main").addEntry(new LootEntryItem(item, weight, quality, functions, conditions, item.getRegistryName().toString()));
    }

    public static SetCount createCountFunction(float min,float max)
    {
	return createCountFunction(min, max, NO_CONDITIONS);
    }

    public static SetCount createCountFunction(float min,float max, LootCondition... conditionsIn)
    {
	return new SetCount(conditionsIn, new RandomValueRange(min, max));
    }
    
    public static LootingEnchantBonus createLootingFunc(float min, float max)
    {
	return createLootingFunc(min, max, 0);
    }
    
    public static LootingEnchantBonus createLootingFunc(float min, float max, int limit)
    {
	return createLootingFunc(min, max, limit, NO_CONDITIONS);
    }
    
    public static LootingEnchantBonus createLootingFunc(float min, float max, int limit, LootCondition... conditionsIn)
    {
	return new LootingEnchantBonus(conditionsIn, new RandomValueRange(min, max), limit);
    }
    
    public static SetMetadata createSetMetadata(int meta)
    {
	return new SetMetadata(NO_CONDITIONS, new RandomValueRange(meta));
    }
    
    public static SetNBT createSetNBT(NBTTagCompound nbt)
    {
	return new SetNBT(NO_CONDITIONS, nbt);
    }
}
