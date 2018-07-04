package net.einsteinsci.betterbeginnings.event;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.items.ItemKnife;
import net.einsteinsci.betterbeginnings.util.ChatUtil;
import net.einsteinsci.betterbeginnings.util.LogUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.world.BlockEvent;
import org.apache.logging.log4j.Level;
import java.util.HashMap;
import java.util.Map;

public class BlockBreakHelper
{
	// Stores the last block the player failed to break.
	public static final Map<EntityPlayer, BlockPos> brokenOnce = new HashMap<>();

	// This is the magic function TODO Localise
	public static void handleBlockBreaking(BlockEvent.BreakEvent e)
	{
		if (!BBConfig.moduleBlockBreaking)
		{
			return;
		}

		// BC Quarries (and similar) get the okay
		if (e.getPlayer() instanceof FakePlayer)
		{
			return;
		}

		Block block = e.getState().getBlock();
		EntityPlayer player = e.getPlayer();
		ItemStack heldItemStack = player.getHeldItemMainhand();

		if (BBConfig.alwaysBreakable.contains(block))
		{
			LogUtil.logDebug("Skipped block-breaking for block '" + block.getUnlocalizedName() +
				"'. Block is marked as always breakable in config.");
			return;
		}

		if (player.capabilities.isCreativeMode)
		{
			return;
		}

		int neededHarvestLevel = block.getHarvestLevel(e.getState());
		String neededToolClass = block.getHarvestTool(e.getState());
		int usedHarvestLevel = 0;
		String usedToolClass = null;
		String stackName = !heldItemStack.isEmpty() ? heldItemStack.getDisplayName() : "NULL";

		boolean isConfigPickaxe = false, isConfigAxe = false;

		if (!heldItemStack.isEmpty())
		{
			for (String toolClass : heldItemStack.getItem().getToolClasses(heldItemStack))
			{
				int hl = heldItemStack.getItem().getHarvestLevel(heldItemStack, toolClass, player, e.getState());
				if (hl >= usedHarvestLevel)
				{
					usedHarvestLevel = hl;
					usedToolClass = toolClass;
				}
			}

			if (BBConfig.alsoPickaxes.containsKey(heldItemStack.getItem()))
			{
				usedToolClass = "pickaxe";
				usedHarvestLevel = BBConfig.alsoPickaxes.get(heldItemStack.getItem());
				isConfigPickaxe = true;
			}
			if (BBConfig.alsoAxes.containsKey(heldItemStack.getItem()))
			{
				usedToolClass = "axe";
				usedHarvestLevel = BBConfig.alsoAxes.get(heldItemStack.getItem());
				isConfigAxe = true;
			}
		}

		boolean cancel = false;

		if (!heldItemStack.isEmpty())
		{
			if (heldItemStack.getItem() instanceof ItemKnife && ItemKnife.getBreakable().contains(block))
			{
				return; // allows knife to do stuff.
			}
		}

		if (neededToolClass == null || neededToolClass.equalsIgnoreCase("shovel") ||
			neededToolClass.equalsIgnoreCase("null"))
		{
			return;
		}

		if (neededToolClass.equalsIgnoreCase(usedToolClass))
		{
			if (usedHarvestLevel < neededHarvestLevel)
			{
				cancel = true;
				System.out.println("NO");
			}
		}
		else if (neededToolClass.equalsIgnoreCase("pickaxe") && isConfigPickaxe)
		{
			LogUtil.logDebug("Item accepted as pickaxe from config: " + heldItemStack.toString());
		}
		else if (neededToolClass.equalsIgnoreCase("axe") && isConfigAxe)
		{
			LogUtil.logDebug("Item accepted as axe from config: " + heldItemStack.toString());
		}
		else
		{
			if (usedToolClass == null || usedToolClass.equalsIgnoreCase("null"))
			{
				if (e.getWorld().getDifficulty() != EnumDifficulty.PEACEFUL && !BBConfig.noDamageOnBadBreak)
				{
					player.attackEntityFrom(new DamageSourceFace(block), 6.0f);
				}

				if (!brokenOnce.containsKey(player) || brokenOnce.get(player) == null ||
						!brokenOnce.get(player).equals(e.getPos()))
				{
					ChatUtil.sendModChatToPlayer(player, "Almost. Once more should do it."
					/* I18n.format("blockbreak.fail") */);
					brokenOnce.put(player, e.getPos());

					// skip other notification
					e.setCanceled(true);
					return;
				}
				else
				{
					ChatUtil.sendModChatToPlayer(player, "Ouch! But at least it worked."
					/* I18n.format("blockbreak.success") */);
					brokenOnce.put(player, null);
				}

				LogUtil.log(Level.INFO, "Block break failed for " + stackName + " on " + block.getUnlocalizedName());
				LogUtil.log(Level.INFO, "  Required tool class: " + neededToolClass + ", supplied: " + usedToolClass);
				LogUtil.log(Level.INFO, "  Minimum harvest level: " + neededHarvestLevel + ", supplied: " +
					usedHarvestLevel);
			}
			else
			{
				cancel = true;
				System.out.println("NO");
			}
		}

		if (cancel)
		{
			LogUtil.log(Level.INFO, "Block break failed for " + stackName + " on " + block.getUnlocalizedName());
			LogUtil.log(Level.INFO, "  Required tool class: " + neededToolClass + ", supplied: " + usedToolClass);
			LogUtil.log(Level.INFO, "  Minimum harvest level: " + neededHarvestLevel + ", supplied: " +
				usedHarvestLevel);

			ChatUtil.sendModChatToPlayer(player, "Wrong tool!" /* I18n.format("blockbreak.wrongtool") */);
			ChatUtil.sendModChatToPlayer(player, "Requires " + getToolLevelName(neededHarvestLevel) + " " +
					neededToolClass + "."
			/* I18n.format("blockbreak.wrongtool.message",
				getToolLevelName(neededHarvestLevel), neededToolClass) */);

			e.setCanceled(true);
		}
	}

	public static String getToolLevelName(int level)
	{
		switch (level)
		{
		case 0:
			return "flint or bone";
		case 1:
			return "stone";
		case 2:
			return "iron";
		case 3:
			return "diamond";
		default:
			return "[LEVEL " + level + "]";
		}
	}
}
