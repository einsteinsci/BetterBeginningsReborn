package net.einsteinsci.betterbeginnings.gui;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.inventory.containers.ContainerSmelter;
import net.einsteinsci.betterbeginnings.register.recipe.SmelterRecipeHandler;
import net.einsteinsci.betterbeginnings.tileentity.TileEntitySmelter;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.ChatUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSmelter extends GuiContainer
{
	private static final ResourceLocation SMELTER_GUI_TEXTURES =
		new ResourceLocation(ModMain.MODID + ":textures/gui/container/smelter.png");
	private TileEntitySmelter tileSmelter;

	public GuiSmelter(EntityPlayer player, TileEntitySmelter tile)
	{
		super(new ContainerSmelter(player, tile));
		tileSmelter = tile;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String string = tileSmelter.hasCustomName() ? tileSmelter.getName() : I18n.format(tileSmelter.getName());
		fontRendererObj.drawString(string, xSize / 2 - fontRendererObj.getStringWidth(string), 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 94, 4210752);

		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		
		ItemStack input = CapUtils.getItemHandler(tileSmelter).getStackInSlot(TileEntitySmelter.INPUT); 
		if (input != null && SmelterRecipeHandler.instance().getSmeltingResult(input) != null)
		{
			if (mouseX >= 46 + k && mouseX < 62 + k && mouseY >= 17 + l && mouseY < 33 + l)
			{
				List<String> lines = new ArrayList<>();
				int min = tileSmelter.getMinOutputCount(), max = tileSmelter.getMaxOutputCount();

				if (min == max)
				{
					lines.add(ChatUtil.CYAN + "Output: " + min);
				}
				else
				{
					lines.add(ChatUtil.CYAN + "Output: " + min + " - " + max);
					int boostPct = (int)(tileSmelter.getTotalBoost() * 100.0f);
					lines.add(ChatUtil.BLUE + "Total Boost: +" + boostPct + "%");
				}

				drawHoveringText(lines, mouseX - k, mouseY - l - (min == max ? 18 : 30));
			}
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(SMELTER_GUI_TEXTURES);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;

		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		int i1;

		if (tileSmelter.isBurning())
		{
			i1 = tileSmelter.getBurnTimeRemainingScaled(12);
			drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = tileSmelter.getCookProgressScaled(24);
		drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
