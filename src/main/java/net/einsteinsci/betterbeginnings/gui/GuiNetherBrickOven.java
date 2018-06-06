package net.einsteinsci.betterbeginnings.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.inventory.containers.ContainerNetherBrickOven;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityNetherBrickOven;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNetherBrickOven extends GuiContainer
{
	private static final ResourceLocation ovenGuiTextures =
			new ResourceLocation(ModMain.MODID + ":textures/gui/container/nether_brick_oven.png");

	private TileEntityNetherBrickOven tileBrickOven;

	public GuiNetherBrickOven(EntityPlayer player, TileEntityNetherBrickOven tile)
	{
		super(new ContainerNetherBrickOven(player, tile));
		tileBrickOven = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String string = tileBrickOven.hasCustomName() ? tileBrickOven.getName() :
						I18n.format(tileBrickOven.getName());
		fontRenderer.drawString(string, 40, 6, 4210752);

		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;

		// In fuel zone
		if (mouseX >= k + 16 && mouseX <= k + 33 && mouseY >= l + 9 && mouseY <= l + 58)
		{
			drawFluidTooltip(mouseX - k, mouseY - l);
		}
	}

	private void drawFluidTooltip(int x, int y)
	{
		List<String> lines = new ArrayList<>();

		FluidStack stack = tileBrickOven.getFuelStack();
		lines.add("" + tileBrickOven.getFuelLevel() + " mB");

		if (stack != null)
		{
			lines.add(stack.getLocalizedName());
		}
		drawHoveringText(lines, x, y); //Draw tooltip
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(ovenGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;

		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		int i1;

		i1 = tileBrickOven.getCookProgressScaled(24);
		drawTexturedModalRect(k + 103, l + 34, 176, 14, i1 + 1, 16);

		int tankFillPx = tileBrickOven.getFuelLevelScaled(48);
		drawTexturedModalRect(k + 17, l + 58 - tankFillPx, 176, 31, 16, tankFillPx);
	}

	public static void setGLColorFromInt(int color)
	{
		float red = (color >> 16 & 255) / 255.0F;
		float green = (color >> 8 & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GlStateManager.color(red, green, blue, 1.0F);
	}
}
