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

    // TODO: This seems like it should be a crafting recipe.
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        
        if (player.inventory.hasItemStack(new ItemStack(RegisterItems.marshmallow)))
        {
            stack.setCount(stack.getCount() - 1);
            ItemStack mallowStick = new ItemStack(RegisterItems.roastingStickRawMallow);
            if (!player.inventory.addItemStackToInventory(mallowStick))
            {
                EntityItem drop = new EntityItem(world, player.posX, player.posY, player.posZ, mallowStick);

                world.spawnEntity(drop);
            }

            stack.setCount(stack.getCount() - 1); // Why is this done twice?
            if (stack.getCount() <= 0)
            {
                player.inventory.setItemStack(ItemStack.EMPTY);
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
