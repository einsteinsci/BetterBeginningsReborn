package net.einsteinsci.betterbeginnings.event;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.entity.EntityMinecartKiln;
import net.einsteinsci.betterbeginnings.items.ItemCharredMeat;
import net.einsteinsci.betterbeginnings.items.ItemFireBow;
import net.einsteinsci.betterbeginnings.items.ItemHammer;
import net.einsteinsci.betterbeginnings.items.ItemKnife;
import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.achievement.RegisterAchievements;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityCampfire;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelterBase;
import net.einsteinsci.betterbeginnings.util.ChatUtil;
import net.einsteinsci.betterbeginnings.util.LootUtil;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.stats.AchievementList;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.conditions.EntityHasProperty;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.properties.EntityOnFire;
import net.minecraft.world.storage.loot.properties.EntityProperty;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class BBEventHandler
{
    // add WIP items here
    static List<Item> wip = Lists.<Item>newArrayList(RegisterItems.wickerShield, Item.getItemFromBlock(RegisterBlocks.wickerBasket));

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e)
    {
	if (BBConfig.greetUser)
	{
	    ChatUtil.sendModChatToPlayer(e.player, ChatUtil.LIME + "BetterBeginnings " + ModMain.VERSION +
		    " loaded " + ChatUtil.LIME + "successfully.");
	}
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e)
    {
	if (e.getModID().equals(ModMain.MODID))
	{
	    BBConfig.syncConfig(ModMain.configFile);
	}
    }

    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent e)
    {
	Item item = e.getItemStack().getItem();

	if (item == RegisterItems.charredMeat)
	{
	    if (e.getItemStack().getMetadata() != ItemCharredMeat.META_UNKNOWN)
	    {
		e.getToolTip().add("Not to be confused with charcoal");
	    }
	    else
	    {
		e.getToolTip().add("You don't want to know...");
	    }
	}

	if (item == RegisterItems.ironNugget)
	{
	    e.getToolTip().add("Good for hinges and rivets");
	}

	if (item == RegisterItems.flintKnife)
	{
	    e.getToolTip().add("Don't bring it to a gunfight");
	}

	if (item == RegisterItems.infusionScroll)
	{
	    e.getToolTip().add("Allows you to decipher tool infusion.");
	}

	if (item == RegisterItems.noobWoodSword && EnchantmentHelper.getEnchantments(e.getItemStack()).isEmpty())
	{
	    e.getToolTip().add(ChatUtil.BLUE + "+0 Attack Damage");
	}

	if (item == RegisterItems.testItem)
	{
	    e.getToolTip().add(ChatUtil.PINK + "For dev testing only. What it does");
	    e.getToolTip().add(ChatUtil.PINK + "changes from one version to the next.");
	}

	if (item == RegisterItems.pan)
	{
	    e.getToolTip().add(ChatUtil.WHITE + "Doubles campfire cooking speed");
	}

	if (item == RegisterItems.rotisserie)
	{
	    e.getToolTip().add(ChatUtil.WHITE + "Cooks food like a pan at the cost of speed");
	}

	if (item == RegisterItems.roastingStick)
	{
	    e.getToolTip().add(ChatUtil.WHITE + "Right-click to impale a marshmallow.");
	}

	if (item == Items.SUGAR)
	{
	    e.getToolTip().add(ChatUtil.ITALIC + "Yes please!");
	}

	if (item == Item.getItemFromBlock(RegisterBlocks.campfire) && !BBConfig.moduleCampfire)
	{
	    e.getToolTip().add(ChatUtil.RED + "Module disabled in config.");
	}

	if (item == Item.getItemFromBlock(RegisterBlocks.infusionRepairStation) &&
		!BBConfig.moduleInfusionRepair)
	{
	    e.getToolTip().add(ChatUtil.RED + "Module disabled in config.");
	}

	if (TileEntitySmelterBase.isBooster(e.getItemStack()))
	{
	    int percent = (int)(TileEntitySmelterBase.getBoostFromBooster(e.getItemStack()) * 100.0f);
	    String line = ChatUtil.LIME + "Smelter Booster";
	    if (percent > 0)
	    {
		line += ": +" + percent + "% chance of bonus";
	    }
	    e.getToolTip().add(line);
	}

	Block block = Block.getBlockFromItem(item);
	if (block != null)
	{
	    if (block == RegisterBlocks.kiln || block == RegisterBlocks.obsidianKiln ||
		    block == RegisterBlocks.brickOven || block == RegisterBlocks.netherBrickOven ||
		    block == RegisterBlocks.smelter || block == RegisterBlocks.enderSmelter)
	    {
		if (!BBConfig.moduleFurnaces)
		{
		    e.getToolTip().add(ChatUtil.RED + "Module disabled in config.");
		}
	    }
	}

	if (isWIP(e.getItemStack()))
	{
	    e.getToolTip().add(ChatUtil.RED + "WIP. May not be fully functional.");
	}
    }

    public boolean isWIP(ItemStack stack)
    {
	return wip.contains(stack.getItem());
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent e)
    {
	// Let's leave this to the professionals
	BlockBreakHelper.handleBlockBreaking(e);
    }

    @SubscribeEvent
    public void onRightClick(PlayerInteractEvent.RightClickBlock e)
    {
	if (e.getItemStack() != null)
	{
	    Item item = e.getItemStack().getItem();

	    if (item instanceof ItemFlintAndSteel || item instanceof ItemFireBow)
	    {
		Block b = e.getWorld().getBlockState(e.getPos()).getBlock();

		if (b == RegisterBlocks.campfire || b == RegisterBlocks.campfireLit)
		{
		    TileEntityCampfire campfire = (TileEntityCampfire)e.getWorld().getTileEntity(e.getPos());

		    campfire.lightFuel(); // Light it.
		}
	    }
	}
    }

    @SubscribeEvent
    public void onBlockDrops(BlockEvent.HarvestDropsEvent e)
    {
	Block block = e.getState().getBlock();
	EntityPlayer player = e.getHarvester();

	// All onBlockDrops activity that does not have to do with players must
	// occur before here.
	if (player == null)
	{
	    return;
	}

	ItemStack held = player.getHeldItemMainhand();
	Random rand = player.world.rand;

	// Knife silk-touching for vines
	if (block == Blocks.VINE && !e.isSilkTouching())
	{
	    if (held != null)
	    {
		if (held.getItem() instanceof ItemKnife)
		{
		    if (rand.nextInt(4) == 0)
		    {
			e.getDrops().add(new ItemStack(Blocks.VINE));
		    }
		}
	    }
	}

	// Knife silk-touching for grass/bushes
	if ((block == Blocks.TALLGRASS || block == Blocks.DEADBUSH) && !e.isSilkTouching())
	{
	    if (held != null)
	    {
		if (held.getItem() instanceof ItemKnife)
		{
		    if (rand.nextInt(4) == 0)
		    {
			e.getDrops().add(new ItemStack(block, 1, block.getMetaFromState(e.getState())));
		    }
		}
	    }
	}

	// Hammer
	if (held != null)
	{
	    if (held.getItem() instanceof ItemHammer)
	    {
		ItemStack crushResult = ItemHammer.getCrushResult(block, e.getState());
		if (crushResult != null)
		{
		    e.getDrops().clear();
		    e.getDrops().add(crushResult);
		}
	    }
	}

	// Makes sure emergency escape mechanic does not let blocks fall out (like logs)
	// Also aborts if the block breaking module is disabled
	if (BBConfig.alwaysBreakable.contains(block)|| !BBConfig.moduleBlockBreaking)
	{
	    return;
	}

	int neededHarvestLevel = block.getHarvestLevel(e.getState());
	String neededToolClass = block.getHarvestTool(e.getState());
	int usedHarvestLevel = -1;
	String usedToolClass = null;
	if (held != null)
	{
	    usedHarvestLevel = held.getItem().getHarvestLevel(held, neededToolClass, player, e.getState());
	    if(usedHarvestLevel != -1) usedToolClass = neededToolClass;

	    if (BBConfig.alsoPickaxes.containsKey(held.getItem()))
	    {
		usedToolClass = "pickaxe";
		usedHarvestLevel = BBConfig.alsoPickaxes.get(held.getItem());
	    }

	    if (BBConfig.alsoAxes.containsKey(held.getItem()))
	    {
		usedToolClass = "axe";
		usedHarvestLevel = BBConfig.alsoAxes.get(held.getItem());
	    }
	}
	if (neededToolClass == null || neededToolClass.equalsIgnoreCase("shovel") ||
		neededToolClass.equalsIgnoreCase("null"))
	{
	    return;
	}

	if (usedToolClass == null || !usedToolClass.equalsIgnoreCase(neededToolClass) ||
		usedHarvestLevel < neededHarvestLevel)
	{
	    e.getDrops().clear();
	}
    }

    @SubscribeEvent
    public void onItemSmelted(PlayerEvent.ItemSmeltedEvent e)
    {
	if (e.smelting.getItem() == Items.GOLDEN_APPLE && e.smelting.getItemDamage() == 1)
	{
	    RegisterAchievements.achievementGet(e.player, "notchApple");
	}
    }

    @SubscribeEvent
    public void onItemCrafted(PlayerEvent.ItemCraftedEvent e)
    {
	// To fix duplication glitch (#27)
	if (e.crafting.getItem() instanceof ItemKnife)
	{
	    for (int i = 0; i < e.craftMatrix.getSizeInventory(); i++)
	    {
		ItemStack stack = e.craftMatrix.getStackInSlot(i);
		if (stack != null)
		{
		    if (stack.getItem() instanceof ItemKnife)
		    {
			--stack.stackSize;

			if (stack.stackSize <= 0)
			{
			    e.craftMatrix.setInventorySlotContents(i, null);
			}
			else
			{
			    e.craftMatrix.setInventorySlotContents(i, stack);
			}
		    }
		}
	    }
	}

	if (e.crafting.getItem() == RegisterItems.flintKnife)
	{
	    RegisterAchievements.achievementGet(e.player, "flintKnife");
	}
	else if (e.crafting.getItem() instanceof ItemKnife)
	{
	    RegisterAchievements.achievementGet(e.player, "upgradeKnife");
	}

	if (e.crafting.getItem() == Items.STRING)
	{
	    RegisterAchievements.achievementGet(e.player, "makeString");
	}

	if (e.crafting.getItem() == RegisterItems.twine)
	{
	    RegisterAchievements.achievementGet(e.player, "makeTwine");
	}

	for (int i = 0; i < e.craftMatrix.getSizeInventory(); ++i)
	{
	    if (e.craftMatrix.getStackInSlot(i) != null)
	    {
		ItemStack current = e.craftMatrix.getStackInSlot(i);
		if (current.getItem() instanceof ItemKnife && e.crafting.getItem() == Items.STICK)
		{
		    RegisterAchievements.achievementGet(e.player, "makeSticks");
		}
	    }
	}

	if (e.crafting.getItem() == RegisterItems.flintHatchet)
	{
	    RegisterAchievements.achievementGet(e.player, "makeHatchet");
	}

	if (e.crafting.getItem() instanceof ItemSword)
	{
	    RegisterAchievements.achievementGet(e.player, "makeSword");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.infusionRepairStation))
	{
	    RegisterAchievements.achievementGet(e.player, "infusionRepair");
	}

	if (e.crafting.getItem() == RegisterItems.boneShard)
	{
	    RegisterAchievements.achievementGet(e.player, "boneShards");
	}

	if (e.crafting.getItem() == RegisterItems.bonePickaxe)
	{
	    RegisterAchievements.achievementGet(e.player, "bonePick");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.kiln))
	{
	    RegisterAchievements.achievementGet(e.player, "makeKiln");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.obsidianKiln))
	{
	    RegisterAchievements.achievementGet(e.player, "obsidianKiln");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.smelter))
	{
	    RegisterAchievements.achievementGet(e.player, "makeSmelter");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.enderSmelter))
	{
	    RegisterAchievements.achievementGet(e.player, "enderSmelter");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.brickOven))
	{
	    RegisterAchievements.achievementGet(e.player, "makeBrickOven");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.doubleWorkbench))
	{
	    e.player.addStat(AchievementList.BUILD_WORK_BENCH, 1);
	}

	if (e.crafting.getItem() == Items.CAKE)
	{
	    RegisterAchievements.achievementGet(e.player, "cake");
	}

	if (e.crafting.getItem() == Item.getItemFromBlock(RegisterBlocks.netherBrickOven))
	{
	    RegisterAchievements.achievementGet(e.player, "netherBrickOven");
	}
    }

    @SubscribeEvent
    public void onEntityLivingDrops(LivingDropsEvent e)
    {
	Random rand = e.getEntity().world.rand;
	if (BBConfig.moreBones)
	{
	    if (!BBConfig.moreBonesPeacefulOnly || e.getEntity().world.getDifficulty() == EnumDifficulty.PEACEFUL)
	    {
		int maxBones = 0;
		int maxShards = 0;

		if (e.getEntityLiving() instanceof EntityCow)
		{
		    maxBones = 4;
		}
		if (e.getEntityLiving() instanceof EntitySheep || e.getEntityLiving() instanceof EntityPig)
		{
		    maxBones = 3;
		}
		if (e.getEntityLiving() instanceof EntityChicken || e.getEntityLiving() instanceof EntityOcelot)
		{
		    maxShards = 3;
		}
		if (e.getEntityLiving() instanceof EntityZombie)
		{
		    maxBones = 2;
		    maxShards = 3;
		}

		if (maxBones > 0 && e.isRecentlyHit() && !e.getEntityLiving().isChild())
		{
		    int dropCount = rand.nextInt(maxBones + e.getLootingLevel());
		    for (int i = 0; i < dropCount; ++i)
		    {
			e.getEntityLiving().dropItem(Items.BONE, 1);
		    }
		}
		if (maxShards > 0 && e.isRecentlyHit() && !e.getEntityLiving().isChild())
		{
		    int dropCount = rand.nextInt(maxShards + e.getLootingLevel());
		    for (int i = 0; i < dropCount; ++i)
		    {
			e.getEntityLiving().dropItem(RegisterItems.boneShard, 1);
		    }
		}
	    }
	}
    }

    @SubscribeEvent
    public void onLootTableLoad(LootTableLoadEvent e)
    {
	Worldgen.addLoot(e.getTable(), e.getName());
	if (BBConfig.moreBones)
	{
	    if(e.getName().equals(LootTableList.ENTITIES_SKELETON))
	    {
		LootPool pool1 = e.getTable().getPool("pool1");
		pool1.removeEntry("minecraft:bone");
		pool1.addEntry(new LootEntryItem(Items.BONE, 1, 1, new LootFunction[] {LootUtil.createCountFunction(1, 3), LootUtil.createLootingFunc(0, 1)}, LootUtil.NO_CONDITIONS, Items.BONE.getRegistryName().toString()));
	    }
	}
	if (BBConfig.flamingAnimalsDropCharredMeat)
	{
	    if(e.getName().equals(LootTableList.ENTITIES_COW))
	    {
		replaceCookedWithCharred(e.getTable().getPool("pool1"), Items.BEEF, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_MEAT), 1, 3);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_PIG))
	    {
		replaceCookedWithCharred(e.getTable().getPool("main"), Items.PORKCHOP, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_MEAT), 1, 3);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_CHICKEN))
	    {
		replaceCookedWithCharred(e.getTable().getPool("pool1"), Items.CHICKEN, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_CHICKEN), 1, 1);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_SHEEP))
	    {
		replaceCookedWithCharred(e.getTable().getPool("main"), Items.MUTTON, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_MUTTON), 1, 2);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_RABBIT))
	    {
		replaceCookedWithCharred(e.getTable().getPool("pool1"), Items.RABBIT, new ItemStack(RegisterItems.charredMeat, 1, ItemCharredMeat.META_RABBIT), 0, 1);
	    }
	}
	else
	{
	    if(e.getName().equals(LootTableList.ENTITIES_COW))
	    {
		removeSmeltFunction(e.getTable().getPool("pool1"), Items.BEEF, Items.COOKED_BEEF, 1, 3);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_PIG))
	    {
		removeSmeltFunction(e.getTable().getPool("main"), Items.PORKCHOP, Items.COOKED_PORKCHOP, 1, 3);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_CHICKEN))
	    {
		removeSmeltFunction(e.getTable().getPool("pool1"), Items.CHICKEN, Items.COOKED_CHICKEN, 1, 1);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_SHEEP))
	    {
		removeSmeltFunction(e.getTable().getPool("main"), Items.MUTTON, Items.COOKED_MUTTON, 1, 2);
	    }
	    else if(e.getName().equals(LootTableList.ENTITIES_RABBIT))
	    {
		removeSmeltFunction(e.getTable().getPool("pool1"), Items.RABBIT, Items.COOKED_RABBIT, 0, 1);
	    }
	}
	if(e.getName().equals(LootTableList.ENTITIES_SPIDER) || e.getName().equals(LootTableList.ENTITIES_CAVE_SPIDER))
	{
	    if (!BBConfig.spidersDropString)
	    {
		e.getTable().getPool("main").removeEntry("minecraft:string");
	    }
	    e.getTable().getPool("main").addEntry(new LootEntryItem(RegisterItems.silk, 1, 1, new LootFunction[] {LootUtil.createCountFunction(2, 4), LootUtil.createLootingFunc(0, 1)}, LootUtil.NO_CONDITIONS, RegisterItems.silk.getRegistryName().toString()));
	}
    }

    private static void replaceCookedWithCharred(LootPool targetPool, Item targetItem, ItemStack replacementStack, int minCount, int maxCount)
    {
	List<LootFunction> charredFunctions = Lists.newArrayList();
	if(replacementStack.getItemDamage() != 0) charredFunctions.add(LootUtil.createSetMetadata(replacementStack.getItemDamage()));
	if(replacementStack.getTagCompound() != null) charredFunctions.add(LootUtil.createSetNBT(replacementStack.getTagCompound()));
	charredFunctions.add(LootUtil.createCountFunction(minCount, maxCount));
	charredFunctions.add(LootUtil.createLootingFunc(0, 1));
	
	LootCondition notOnFire = new EntityHasProperty(new EntityProperty[] {new EntityOnFire(false)}, EntityTarget.THIS);
	LootCondition onFire = new EntityHasProperty(new EntityProperty[] {new EntityOnFire(true)}, EntityTarget.THIS);
	
	targetPool.removeEntry(targetItem.getRegistryName().toString());
	targetPool.addEntry(new LootEntryItem(targetItem, 1, 1, new LootFunction[] {LootUtil.createCountFunction(1, 3), LootUtil.createLootingFunc(0, 1)}, new LootCondition[] {notOnFire}, targetItem.getRegistryName().toString()));
	targetPool.addEntry(new LootEntryItem(replacementStack.getItem(), 1, 1, charredFunctions.toArray(new LootFunction[charredFunctions.size()]), new LootCondition[] {onFire}, ModMain.MODID + ":charred_+" + targetItem.getRegistryName().getResourcePath().toString()));
    }
    
    private void removeSmeltFunction(LootPool pool, Item targetItem, Item replacement, int minCount, int maxCount)
    {
	LootCondition notOnFire = new EntityHasProperty(new EntityProperty[] {new EntityOnFire(false)}, EntityTarget.THIS);
	LootCondition onFire = new EntityHasProperty(new EntityProperty[] {new EntityOnFire(true)}, EntityTarget.THIS);
	
	pool.removeEntry(targetItem.getRegistryName().toString());
	pool.addEntry(new LootEntryItem(targetItem, 1, 1, new LootFunction[] {LootUtil.createCountFunction(1, 3), LootUtil.createLootingFunc(0, 1)}, new LootCondition[] {notOnFire}, targetItem.getRegistryName().toString()));
	pool.addEntry(new LootEntryItem(replacement, 1, 1, new LootFunction[] {LootUtil.createCountFunction(1, 3), LootUtil.createLootingFunc(0, 1)}, new LootCondition[] {onFire}, replacement.getRegistryName().toString()));
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent e)
    {
	//Replace Minecart with Furnace with Minecart with Kiln
	if(BBConfig.moduleFurnaces && e.getEntity().getClass() == EntityMinecartFurnace.class && ((EntityMinecartFurnace) e.getEntity()).getDisplayTile() == Blocks.FURNACE.getDefaultState())
	{
	    if(!e.getWorld().isRemote)
	    {
		e.getEntity().setDead();
		EntityMinecartKiln kilnCart = new EntityMinecartKiln(e.getWorld(), e.getEntity().posX, e.getEntity().posY, e.getEntity().posZ);
		e.getWorld().spawnEntity(kilnCart);
	    }
	}
    }
}
