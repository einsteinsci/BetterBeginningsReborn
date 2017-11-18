package net.einsteinsci.betterbeginnings.items;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.register.IBBName;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.util.Prep1_11;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("deprecation") // TODO: Rework so that this annotation is not necessary.
public class ItemWickerShield extends ItemShield implements IBBName
{
    public ItemWickerShield()
    {
        super();
        setMaxDamage(168);
    }

    @Override
    public String getName()
    {
        return "wicker_shield";
    }
    
    public String getItemStackDisplayName(ItemStack stack)
    {
        return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }
    
    @Override
    public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[] {ModMain.tabBetterBeginnings, CreativeTabs.COMBAT};
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent e)
    {
        EntityLivingBase attacked = e.getEntityLiving();
        EntityLivingBase attacker = attacked.getAttackingEntity();
        if(attacker == null) return;
        damageShield(attacked, attacker, e.getAmount());
        doAxeStuff(attacked, attacker);
    }

    private void doAxeStuff(EntityLivingBase attacked, EntityLivingBase attacker)
    {
        //Copied from EntityMob#attackEntityAsMob() L134-150 START
        if (attacked instanceof EntityPlayer)
        {
            EntityPlayer entityplayer = (EntityPlayer)attacked;
            ItemStack itemstack = attacker.getHeldItemMainhand();
            ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;
    
            if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem() instanceof ItemAxe && itemstack1.getItem() == RegisterItems.wickerShield)
            {
                float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(attacker) * 0.05F;
    
                if (attacker.world.rand.nextFloat() < f1)
                {
                entityplayer.getCooldownTracker().setCooldown(RegisterItems.wickerShield, 100);
                attacker.world.setEntityState(entityplayer, (byte) 30);
                }
            }
        }
        //Copied from EntityMob#attackEntityAsMob() L134-150 END
    }

    void damageShield(EntityLivingBase attacked, EntityLivingBase attacker, float f)
    {
        ItemStack shield = null;
        if(Prep1_11.isValid(attacked.getHeldItemOffhand()) && attacked.getHeldItemOffhand().getItem() == RegisterItems.wickerShield)
        shield = attacked.getHeldItemOffhand();
        else if(Prep1_11.isValid(attacked.getHeldItemMainhand()) && attacked.getHeldItemMainhand().getItem() == RegisterItems.wickerShield)
        shield = attacked.getHeldItemMainhand();

        //Copied from EntityPlayer#damageShield() START
        if(Prep1_11.isEmpty(shield)) return;
        shield.damageItem(1 + MathHelper.floor(f), attacker);
        if (shield.getCount() <= 0)
        {
            EnumHand enumhand = attacked.getActiveHand();
            if(attacked instanceof EntityPlayer) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((EntityPlayer) attacked, attacked.getActiveItemStack(), enumhand);
    
            if (enumhand == EnumHand.MAIN_HAND)
            {
                attacked.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
            }
            else
            {
                attacked.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.EMPTY);
            }
    
            attacked.resetActiveHand();
            attacked.playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + attacked.world.rand.nextFloat() * 0.4F);
        }
        //Copied from EntityPlayer#damageShield() END
    }
}

