package net.einsteinsci.betterbeginnings.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class DamageSourceDiffusion extends DamageSource
{
	public DamageSourceDiffusion()
	{
		super("diffusion");
		setDamageBypassesArmor();
		setDifficultyScaled();
	}

	@Override
	public ITextComponent getDeathMessage(EntityLivingBase mob)
	{
		return new TextComponentTranslation("death.diffusion", mob.getName());
	}
}
