package net.einsteinsci.betterbeginnings.items;


import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemRoastingStickMallow extends Item implements IBBName
{
	boolean isCooked;

	public ItemRoastingStickMallow(boolean cooked)
	{
		isCooked = cooked;
		setCreativeTab(ModMain.tabBetterBeginnings);
		setMaxStackSize(10);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) 
	{	
		if (isCooked)
		{
			player.inventory.addItemStackToInventory(new ItemStack(RegisterItems.marshmallowCooked));
			player.inventory.addItemStackToInventory(new ItemStack(RegisterItems.roastingStick));
			stack.stackSize -= 1;
		}
		else
		{
			player.inventory.addItemStackToInventory(new ItemStack(RegisterItems.marshmallow));
			player.inventory.addItemStackToInventory(new ItemStack(RegisterItems.roastingStick));
			stack.stackSize -= 1;
		}

		if (stack.stackSize <= 0)
		{
			player.inventory.setItemStack(null);
			ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
		}
		player.inventoryContainer.detectAndSendChanges();
		return ActionResult.newResult(EnumActionResult.PASS, stack);
	}
	@Override
	public String getName()
	{
		if (isCooked)
		{
			return "roasting_stick_cooked_mallow";
		}
		else
		{
		    return "roasting_stick_raw_mallow";
		}
	}
}
