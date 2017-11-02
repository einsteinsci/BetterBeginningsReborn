package net.einsteinsci.betterbeginnings.client;

import net.einsteinsci.betterbeginnings.entity.projectile.EntityThrownKnife;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class RenderThrownKnife extends RenderSnowball<EntityThrownKnife> 
{	
	public RenderThrownKnife(RenderManager renderManager, RenderItem renderItem) 
	{
		super(renderManager, Items.STICK, renderItem);
	}
	
	@Override
	public ItemStack getStackToRender(EntityThrownKnife entityIn) 
	{
		return entityIn.getKnife();
	}
}
