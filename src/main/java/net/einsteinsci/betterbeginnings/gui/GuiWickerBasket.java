package net.einsteinsci.betterbeginnings.gui;

import org.lwjgl.opengl.GL11;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.inventory.containers.ContainerWickerBasket;
import net.einsteinsci.betterbeginnings.tileentity.TileEntityWickerBasket;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiWickerBasket extends GuiContainer
{
    private static final ResourceLocation BASKET_GUI_TEXTURES = new ResourceLocation(ModMain.MODID, "textures/gui/container/wicker_basket.png");
    private TileEntityWickerBasket tileBasket;

    public GuiWickerBasket(EntityPlayer player, TileEntityWickerBasket tile)
    {
	super(new ContainerWickerBasket(player, tile));
	this.tileBasket = tile;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
	String string = tileBasket.hasCustomName() ? tileBasket.getName() : I18n.format(tileBasket.getName());
	fontRendererObj.drawString(string, 8, 6, 4210752);
	fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 128, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
	GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	mc.getTextureManager().bindTexture(BASKET_GUI_TEXTURES);
	drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
    }

}
