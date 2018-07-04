package net.einsteinsci.betterbeginnings.items;

import java.util.HashSet;
import java.util.Set;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
//Disable throwing knives due to desync issues
//import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

public abstract class ItemKnife extends ItemTool implements IBBName
{
    public static final float BASE_DAMAGE = 1.0f;
    public static final float SPEED = -2.2f;
    public static final int DRAW_TIME = 32;
    private static final String THROWN_TAG = "isThrown";
    private static final ResourceLocation THROWN_GETTER_ID = new ResourceLocation(ModMain.MODID, "thrown");
    private static final IItemPropertyGetter thrownGetter = (stack, worldIn, entityIn) -> entityIn != null && isThrown(stack) ? 1.0F : 0.0F;

    public ItemKnife(ToolMaterial material)
    {
        super(BASE_DAMAGE, SPEED, material, getBreakable());
        addPropertyOverride(THROWN_GETTER_ID, thrownGetter);
    }

    public static Set<Block> getBreakable()
    {
        Set<Block> s = new HashSet<Block>();
        
        s.add(Blocks.PUMPKIN);
        s.add(Blocks.LIT_PUMPKIN);
        s.add(Blocks.MELON_BLOCK);
        s.add(Blocks.CLAY);
        s.add(Blocks.GRASS);
        s.add(Blocks.MYCELIUM);
        s.add(Blocks.LEAVES);
        s.add(Blocks.LEAVES2);
        s.add(Blocks.BROWN_MUSHROOM_BLOCK);
        s.add(Blocks.RED_MUSHROOM_BLOCK);
        s.add(Blocks.GLASS);
        s.add(Blocks.GLASS_PANE);
        s.add(Blocks.SOUL_SAND);
        s.add(Blocks.STAINED_GLASS);
        s.add(Blocks.STAINED_GLASS_PANE);
        s.add(Blocks.CACTUS);

        return s;
    }
    
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        
        if(isThrown(stack))
        {
            return super.onItemRightClick(world, player, hand);
        }
        player.setActiveHand(hand);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
    }
    
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
    {
        if(entityLiving instanceof EntityPlayer)
        {
            setThrown(stack, true);
        }
        if(!worldIn.isRemote)
        {
            EntityThrownKnife knife = new EntityThrownKnife(worldIn, entityLiving, stack);
            knife.setForce((float) Math.min((this.getMaxItemUseDuration(stack) - timeLeft), ItemKnife.DRAW_TIME) / ItemKnife.DRAW_TIME);
            knife.shoot(entityLiving, entityLiving.rotationPitch, entityLiving.rotationYaw, 0.0F, 1.5F, 1.0F);
            worldIn.spawnEntity(knife);
        }
    }
    
    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return isThrown(stack) ? EnumAction.NONE : EnumAction.BOW;
    }
    
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        // Why is this not overridden by each knife?
        return 72000;
    }

    @Override
    public boolean shouldRotateAroundWhenRendering()
    {
        return true;
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockstate)
    {
        return toolMaterial.getHarvestLevel();
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack)
    {
        Set<String> res = new HashSet<>();

        res.add("knife");

        return res;
    }


    // Allows durability-based crafting.
    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return true;
    }
    // ...which also requires this...
    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        ItemStack result = itemStack.copy();
        result.setItemDamage(itemStack.getItemDamage() + 1);

        return result;
    }
    
    public float getDamageVsEntity()
    {
    	//TODO
    	return 5;
    }

    @Override
    public abstract String getName();
    
    public static void setThrown(ItemStack stack, boolean thrown)
    {
        if(!stack.hasTagCompound())
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setBoolean(THROWN_TAG, thrown);
    }
    
    public static boolean isThrown(ItemStack stack)
    {
        if(stack.hasTagCompound())
        return stack.getTagCompound().getBoolean(THROWN_TAG);
        return false;
    }
}
