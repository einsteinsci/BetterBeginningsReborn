package net.einsteinsci.betterbeginnings.event;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
 ;

public class DamageSourceFace extends DamageSource
{
	public Block faceHurt;

	public DamageSourceFace(Block block)
	{
		super("face");
		faceHurt = block;
		setDamageBypassesArmor();
		setDifficultyScaled();
	}

	@Override
	public ITextComponent getDeathMessage(EntityLivingBase mob)
	{
		return new TextComponentTranslation("death.face", mob.getName(), faceHurt.getLocalizedName());
	}
}
