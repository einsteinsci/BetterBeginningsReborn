package net.einsteinsci.betterbeginnings.entity;

import net.einsteinsci.betterbeginnings.register.RegisterBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartFurnace;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartKiln extends EntityMinecartFurnace
{
    public EntityMinecartKiln(World worldIn)
    {
	super(worldIn);
    }

    public EntityMinecartKiln(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }

    @Override
    public IBlockState getDefaultDisplayTile()
    {
	return this.isMinecartPowered() ? RegisterBlocks.kilnLit.getDefaultState() : RegisterBlocks.kiln.getDefaultState();
    }

    @Override
    public void killMinecart(DamageSource source)
    {
	this.setDead();

	if (this.world.getGameRules().getBoolean("doEntityDrops"))
	{
	    ItemStack itemstack = new ItemStack(Items.MINECART, 1);

	    if (this.hasCustomName())
	    {
		itemstack.setStackDisplayName(this.getName());
	    }

	    this.entityDropItem(itemstack, 0.0F);
	    if (!source.isExplosion())
	    {
		this.entityDropItem(new ItemStack(RegisterBlocks.kiln), 0.0F);
	    }
	}
    }
}
