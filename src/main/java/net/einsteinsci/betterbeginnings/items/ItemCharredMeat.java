package net.einsteinsci.betterbeginnings.items;

import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCharredMeat extends ItemFood implements IBBName
{
    public static final int META_MEAT = 0;
    public static final int META_CHICKEN = 1;
    public static final int META_MUTTON = 2;
    public static final int META_RABBIT = 3;
    public static final int META_FISH = 4;
    public static final int META_UNKNOWN = 5;

    public ItemCharredMeat()
    {
        super(5, 2.0f, true);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(ModMain.tabBetterBeginnings);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return null;
        }

        return "item." + ModMain.MODID + "." + getSimpleUnlocalizedName(stack.getMetadata());
    }

    public static String getSimpleUnlocalizedName(int meta)
    {
        switch (meta)
        {
        case META_MEAT:
            return "charred_meat";
        case META_CHICKEN:
            return "charred_chicken";
        case META_MUTTON:
            return "charred_mutton";
        case META_RABBIT:
            return "charred_rabbit";
        case META_FISH:
            return "charred_fish";
        default:
            return "charred_unknown";
        }
    }

    public static int getDamageBasedOnMeat(ItemStack meatStack)
    {
        Item meat = meatStack.getItem();
        if (meat == Item.getItemFromBlock(Blocks.AIR)) // TODO: make the Item form of Blocks.AIR a constant.
        {
            return 5; // ???
        }

        if (meat == Items.BEEF || meat == Items.PORKCHOP ||
            meat == Items.COOKED_BEEF || meat == Items.COOKED_PORKCHOP)
        {
            return 0;
        }

        if (meat == Items.CHICKEN || meat == Items.COOKED_CHICKEN)
        {
            return 1;
        }

        if (meat == Items.MUTTON || meat == Items.COOKED_MUTTON)
        {
            return 2;
        }

        if (meat == Items.RABBIT || meat == Items.COOKED_RABBIT)
        {
            return 3;
        }

        if (meat == Items.FISH || meat == Items.COOKED_FISH)
        {
            return 4;
        }

        return 0;
    }

    @Override
    public void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        super.onFoodEaten(stack, world, player);

        //TODO Advancement 
        //RegisterAchievements.achievementGet(player, "charredMeat");
    }

    @Override
    public String getName()
    {
        return "charred_meat";
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
        subItems.add(new ItemStack(itemIn, 1, 2));
        subItems.add(new ItemStack(itemIn, 1, 3));
        subItems.add(new ItemStack(itemIn, 1, 4));

        subItems.add(new ItemStack(itemIn, 1, 5));
    }
}
