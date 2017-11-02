package net.einsteinsci.betterbeginnings.items;


import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class ItemRoastingStick extends Item implements IBBName
{

	public ItemRoastingStick()
	{
		setCreativeTab(ModMain.tabBetterBeginnings);
		setMaxStackSize(10);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (player.inventory.hasItemStack(new ItemStack(RegisterItems.marshmallow)))
		{
			stack.stackSize -= 1;
			ItemStack mallowStick = new ItemStack(RegisterItems.roastingStickRawMallow);
			if (!player.inventory.addItemStackToInventory(mallowStick))
			{
				EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, mallowStick);

				world.spawnEntity(drop);
			}

			stack.stackSize--;
			if (stack.stackSize <= 0)
			{
				player.inventory.setItemStack(null);
				ForgeEventFactory.onPlayerDestroyItem(player, stack, hand);
			}
		}
		player.inventoryContainer.detectAndSendChanges();
		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	@Override
	public String getName()
	{
		return "roasting_stick";
	}
}
