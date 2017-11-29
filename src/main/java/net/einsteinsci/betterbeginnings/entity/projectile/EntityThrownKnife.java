package net.einsteinsci.betterbeginnings.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.einsteinsci.betterbeginnings.items.ItemKnife;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class EntityThrownKnife extends EntityThrowable implements IEntityAdditionalSpawnData
{
	private static final String TAG_THROWN_KNIFE = "ThrownKnife";

	private ItemStack knife;
	private float baseDamage;
	private float force;
	private boolean inTerrain;
	private BlockPos stuckPos = new BlockPos(-1, -1, -1);

	public EntityThrownKnife(World worldIn) 
	{
		super(worldIn);
	}

	public EntityThrownKnife(World world, EntityLivingBase thrower, ItemStack knife) 
	{
		super(world, thrower);
		this.knife = knife;
		this.knife.setCount(1);
		this.baseDamage = ((ItemKnife) knife.getItem()).getDamageVsEntity();
	}

	@Override
	protected void onImpact(RayTraceResult mop) 
	{
		switch(mop.typeOfHit)
		{
		case BLOCK:
			if (!inTerrain)
			{
				doBlockHitEffects(world, mop);
			}
			BlockPos pos = mop.getBlockPos();
			IBlockState state = world.getBlockState(pos);
			if(state.getBlock().canCollideCheck(state, false))
			{
				this.inTerrain = true;
				this.stuckPos = pos;
				//Why setVelocity is @SideOnly(Side.CLIENT) I do not know
				this.motionX = this.motionY = this.motionZ = 0.0F;
			}	
			break;
		case ENTITY:
			if(mop.entityHit instanceof EntityLivingBase && mop.entityHit != this.getThrower())
			{
				EntityLivingBase entityLiving = (EntityLivingBase) mop.entityHit;
				int dmg = 2;
				if(this.getThrower() instanceof EntityPlayer && ((EntityPlayer)this.getThrower()).capabilities.isCreativeMode) dmg = 0;
				if(!world.isRemote && !knife.attemptDamageItem(dmg, rand, (EntityPlayerMP) this.getThrower()))
				{
					entityLiving.attackEntityFrom(DamageSource.causeThrownDamage(mop.entityHit, this.getThrower()), baseDamage * force);
					entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, (int) (100 * force), 2, false, false));
				}
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer playerIn) 
	{
		if(inTerrain && !world.isRemote)
		{
			this.setDead();
		}
	}
	
	@Override
	public void setDead()
	{
	    ItemKnife.setThrown(this.knife, false);
	    super.setDead();
	}

	@Override
	public void onUpdate() 
	{
		IBlockState stuck = world.getBlockState(this.stuckPos);
		if(stuck.getCollisionBoundingBox(world, stuckPos) == Block.NULL_AABB)
		{
			this.inTerrain = false;
		}
		if(!inTerrain)
		{
			super.onUpdate();
		}
	}

	private void doBlockHitEffects(World world, RayTraceResult mop) 
	{
		IBlockState state = world.getBlockState(mop.getBlockPos());
		for(int p = 0; p < 8; p ++)
		{
			world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX, this.posY, this.posZ, 0.0F, 0.0F, 0.0F, Block.getStateId(state));
		}
		world.playSound(this.posX, this.posY, this.posZ, state.getBlock().getSoundType(state, world, mop.getBlockPos(), this).getStepSound(), SoundCategory.NEUTRAL, 0.8F, 0.9F, false);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound) 
	{
		super.readEntityFromNBT(tagCompound);
		knife = new ItemStack(tagCompound.getCompoundTag(TAG_THROWN_KNIFE));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound) 
	{
		super.writeEntityToNBT(tagCompound);
		if(knife != null)
		{
			NBTTagCompound thrownKnife = knife.writeToNBT(new NBTTagCompound());
			tagCompound.setTag(TAG_THROWN_KNIFE, thrownKnife);
		}
	}

	public ItemStack getKnife() 
	{
		return knife;
	}

	public EntityThrownKnife setForce(float force)
	{
		this.force = force;
		return this;
	}

	@Override
	public void writeSpawnData(ByteBuf additionalData) 
	{
		ByteBufUtils.writeItemStack(additionalData, knife);
		additionalData.writeFloat(baseDamage);
		additionalData.writeFloat(force);
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) 
	{
		knife = ByteBufUtils.readItemStack(additionalData);
		baseDamage = additionalData.readFloat();
		force = additionalData.readFloat();
	}
}