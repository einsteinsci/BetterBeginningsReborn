package net.einsteinsci.betterbeginnings.client;

import java.util.Random;

import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.IItemHandler;

public class WickerBasketRender extends TileEntitySpecialRenderer<TileEntityWickerBasket>
{  
    private final Random rand = new Random();
    
    @Override
    public void renderTileEntityAt(TileEntityWickerBasket te, double x, double y, double z, float partialTicks,
            int destroyStage)
    {
	rand.setSeed(te.getWorld().getSeed());
	IItemHandler itemHandler = CapUtils.getItemHandler(te);
	for(int slot = 0; slot < itemHandler.getSlots(); slot++)
	{
	    GlStateManager.pushMatrix();
	    //Translate into the centre of the basket, above the bottom
	    GlStateManager.translate(x + 0.5F, y + 0.2F + 0.075F * slot, z + 0.5F);
	    GlStateManager.translate(getRandDisplacement(rand), 0.0F, getRandDisplacement(rand));
	    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	    GlStateManager.rotate(360.0F * rand.nextFloat(), 0.0F, 0.0F, 1.0F);
	    GlStateManager.scale(0.8F, 0.8F, 0.8F);
	    Minecraft.getMinecraft().getRenderItem().renderItem(itemHandler.getStackInSlot(slot), TransformType.FIXED);
	    GlStateManager.popMatrix();
	}
    }
    
    private float getRandDisplacement(Random rand)
    {
	float ret = rand.nextFloat();
	//Restrict the amount of displacement possible
	ret = MathHelper.clamp(ret, -0.1F, 0.1F);
	return rand.nextBoolean() ? ret : -ret;
    }
}
