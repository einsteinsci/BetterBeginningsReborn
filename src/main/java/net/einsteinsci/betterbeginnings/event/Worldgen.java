package net.einsteinsci.betterbeginnings.event;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.util.LootUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;

public class Worldgen
{
    public static void addLoot(LootTable table, ResourceLocation tableName)
    {
	if(BBConfig.spawnMarshmallows)
	{
	    if(tableName.equals(LootTableList.CHESTS_SIMPLE_DUNGEON))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 100, 1, 5);
	    }
	    else if(tableName.equals(LootTableList.CHESTS_DESERT_PYRAMID))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 200, 1, 10);
	    }
	    else if(tableName.equals(LootTableList.CHESTS_JUNGLE_TEMPLE))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 200, 1, 10);
	    }
	    else if(tableName.equals(LootTableList.CHESTS_STRONGHOLD_CROSSING))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 150, 1, 12);
	    }
	    else if(tableName.equals(LootTableList.CHESTS_STRONGHOLD_CORRIDOR))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 150, 1, 12);
	    }
	    else if(tableName.equals(LootTableList.CHESTS_ABANDONED_MINESHAFT))
	    {
		LootUtil.addItemToTable(table, "main", RegisterItems.marshmallow, 150, 1, 12);
	    }		
	}
    }
}
