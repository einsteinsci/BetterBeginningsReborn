package net.einsteinsci.betterbeginnings.client;

import java.util.Stack;

import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityInfusionRepair;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Prep1_11;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

@SideOnly(Side.CLIENT)
public class InfusionRender extends TileEntitySpecialRenderer<TileEntityInfusionRepair>
{
    public static final float HOVER_OPACITY = 0.8f;
    private static final float PIXEL_SIZE = 1.0F / 16.0F;

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final Vec2f[] ingredientPositions = new Vec2f[]{
    new Vec2f(-1.75F, -5.75F), new Vec2f(1.75F, -5.75F), new Vec2f(5.75F, -1.75F), new Vec2f(5.75F, 1.75F)
    , new Vec2f(1.75F, 5.75F), new Vec2f(-1.75F, 5.75F), new Vec2f(-5.75F, 1.75F), new Vec2f(-5.75F, -1.75F)};

    @Override
    public void renderTileEntityAt(TileEntityInfusionRepair te, double x, double y, double z, float partialTicks,
	    int destroyStage)
    {
	GlStateManager.pushMatrix();
	GlStateManager.translate(x + 0.5F, y + 1.2F, z + 0.5F);
	renderIngredients(te, x, y, z, partialTicks);
	renderTool(te, x, y, z, partialTicks);
	renderNextIngredient(te, x, y, z,  partialTicks);
	GlStateManager.popMatrix();
    }

    private void renderTool(TileEntityInfusionRepair te, double x, double y, double z, float partialTicks)
    {
	IItemHandler itemHandler = CapUtils.getItemHandler(te);
	ItemStack stack = itemHandler.getStackInSlot(TileEntityInfusionRepair.SLOT_ENCH_ITEM);
	if(Prep1_11.isEmpty(stack)) return;
	GlStateManager.pushMatrix();
	    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
	    GlStateManager.translate(-0.25F * PIXEL_SIZE, -1.5F * PIXEL_SIZE, 0.15F);
	    mc.getRenderItem().renderItem(stack, TransformType.GROUND);
	GlStateManager.popMatrix();
    }
    
    private void renderIngredients(TileEntityInfusionRepair te, double x, double y, double z, float partialTicks)
    {
	GlStateManager.pushMatrix();
	IItemHandler itemHandler = CapUtils.getItemHandler(te);
	for(int slot = 0; slot < 8; slot++)
	{
	    ItemStack stack = itemHandler.getStackInSlot(TileEntityInfusionRepair.SLOT_ENCH_ITEM + 1 + slot);
	    Vec2f ingredientPos = ingredientPositions[slot];
	    if(Prep1_11.isEmpty(stack)) continue;
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(ingredientPos.x * PIXEL_SIZE, -0.15F, ingredientPos.y * PIXEL_SIZE);
	    GlStateManager.scale(0.5F, 0.5F, 0.5F);
	    GlStateManager.rotate(((mc.player.ticksExisted + partialTicks) * 60.0f) / 10.0F, 0.0F, 1.0F, 0.0F);
	    mc.getRenderItem().renderItem(stack, TransformType.GROUND);
	    GlStateManager.scale(1.0F, 1.0F, 1.0F);
	    GlStateManager.popMatrix();
	}
	GlStateManager.popMatrix();
    }

    private void renderNextIngredient(TileEntityInfusionRepair te, double x, double y, double z, float partialTicks)
    {
	GlStateManager.pushMatrix();
	Stack<RecipeElement> ingredients = te.getPendingIngredients();
	if(!ingredients.isEmpty())
	{
	    GlStateManager.rotate(((mc.player.ticksExisted + partialTicks) * 60.0f) / 10.0F, 0.0F, 1.0F, 0.0F);
	    mc.getRenderItem().renderItem(ingredients.peek().getFirst(), TransformType.GROUND);
	}
	GlStateManager.popMatrix();
    }
}
