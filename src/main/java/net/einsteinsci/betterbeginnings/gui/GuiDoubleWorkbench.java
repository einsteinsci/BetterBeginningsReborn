package net.einsteinsci.betterbeginnings.gui;

import java.util.ArrayList;
import java.util.List;

import net.einsteinsci.betterbeginnings.ModMain;
import net.einsteinsci.betterbeginnings.inventory.containers.ContainerDoubleWorkbench;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedCraftingHandler;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.util.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

@SideOnly(Side.CLIENT)
public class GuiDoubleWorkbench extends GuiContainer
{
	private static final ResourceLocation workbenchGuiTextures = new ResourceLocation(ModMain.MODID +
		":textures/gui/container/double_workbench_mc18.png");

	private final ContainerDoubleWorkbench container;
	private boolean needsCatalystItems = false;

	//protected final int trueXSize = xSize + 20;

	public GuiDoubleWorkbench(InventoryPlayer invPlayer, World world, BlockPos pos)
	{
		super(new ContainerDoubleWorkbench(invPlayer, world, pos));
		xSize += 20;

		container = (ContainerDoubleWorkbench)inventorySlots;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		//The really big number at the end is the colour
		fontRenderer.drawString(I18n.format("container.craftingdouble"), 33 + 20, 6, 4210752);

		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;

		final int CATALYST_X_OFFSET = -20;

		if (AdvancedCraftingHandler.crafting().hasRecipe(container.craftMatrix, container.worldObj))
		{
			for (AdvancedRecipe recipe : AdvancedCraftingHandler.crafting().recipes)
			{
				if (recipe.matchesMostly(container.craftMatrix, container.worldObj))
				{
					for (int i = 0; i < recipe.getNeededMaterials().length; ++i)
					{
						RecipeElement neededElement = recipe.getNeededMaterials()[i];
						ItemStack needed = neededElement.getFirst().copy();

						if (needed.getItemDamage() == OreDictionary.WILDCARD_VALUE)
						{
							needed.setItemDamage(0);
						}

						Slot slot = container.matSlots[i];

						int _x = slot.xPos + CATALYST_X_OFFSET;
						int _y = slot.yPos;

						if (container.addedMats.getStackInSlot(i).isEmpty() &&
							mouseX >= k + _x && mouseX < k + _x + 16 &&
							mouseY >= l + _y && mouseY < l + _y + 16)
						{
							drawItemTooltip(mouseX - k, mouseY - l, needed, false);
						}
					}

					ItemStack result = recipe.getRecipeOutput();
					Slot slot = container.resultSlot;
					if (container.craftResult.getStackInSlot(0).isEmpty())
					{
						if (!result.isEmpty())
						{
							// draw result tooltip

							int _x = slot.xPos;
							int _y = slot.yPos;

							if (mouseX >= k + _x && mouseX < k + _x + 16 &&
								mouseY >= l + _y && mouseY < l + _y + 16)
							{
								drawItemTooltip(mouseX - k, mouseY - l, result, true);
							}
						}
					}
					break;
				}
			}
		}
	}

	private void drawItemTooltip(int x, int y, ItemStack stack, boolean warn)
	{
		if (stack.isEmpty())
		{
			return;
		}

		List<String> lines = new ArrayList<>();

		boolean adv = Minecraft.getMinecraft().gameSettings.advancedItemTooltips;
		int id = Item.getIdFromItem(stack.getItem());
		lines.addAll(stack.getTooltip(container.getOpeningPlayer(), adv ? TooltipFlags.ADVANCED : TooltipFlags.NORMAL));
		lines.set(0, lines.get(0) + " " + id);

		if (warn)
		{
			lines.add(ChatUtil.RED + I18n.format("container.craftingdouble.warning"));
		}

		drawHoveringText(lines, x, y); //Draw tooltip
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(workbenchGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		if (needsCatalystItems)
		{
			drawTexturedModalRect(k + 144, l + 30, xSize, 0, 26, 26);
		}

		renderTransparentItems(k, l);
	}

	public void renderTransparentItems(int k, int l)
	{
		final int CATALYST_X_OFFSET = -20;

		needsCatalystItems = false;
		if (AdvancedCraftingHandler.crafting().hasRecipe(container.craftMatrix, container.worldObj))
		{
			for (AdvancedRecipe recipe : AdvancedCraftingHandler.crafting().recipes)
			{
				if (recipe.matchesMostly(container.craftMatrix, container.worldObj))
				{
					for (int i = 0; i < recipe.getNeededMaterials().length; ++i)
					{
						RecipeElement neededElement = recipe.getNeededMaterials()[i];
						ItemStack needed = neededElement.getFirst().copy();

						if (needed.getItemDamage() == OreDictionary.WILDCARD_VALUE)
						{
							needed.setItemDamage(0);
						}

						Slot slot = container.matSlots[i];
						
						ItemStack matStack = ItemStack.EMPTY;
						
						for(int s = 0; s < container.addedMats.getSizeInventory(); ++s)
						{
							ItemStack stack = container.addedMats.getStackInSlot(s);
							
							if(stack.isEmpty()){ continue; }
							if(neededElement.matches(stack))
							{
								if(!matStack.isEmpty())
								{
									if(stack.getCount() > matStack.getCount())
									{
										matStack = stack;
									}
								}
								else
								{
									matStack = stack;
								}
							}
						}
						boolean hasMatStack = (!matStack.isEmpty());
						if (!hasMatStack ||
							(matStack.getCount() < needed.getCount() || !neededElement.matches(matStack)))
						{
					        RenderHelper.enableGUIStandardItemLighting();
							drawItemStack(needed, k + slot.xPos + CATALYST_X_OFFSET,
								l + slot.yPos,
								"" + ((matStack == null || neededElement.matchesCheckSize(matStack)) ?
										needed.getCount() : needed.getCount() - matStack.getCount()));
						}
					}

					ItemStack result = recipe.getRecipeOutput();
					Slot slot = container.resultSlot;
					if (container.craftResult.getStackInSlot(0).isEmpty())
					{
						if (!result.isEmpty())
						{
							// Draw red output box
							mc.getTextureManager().bindTexture(workbenchGuiTextures);
							needsCatalystItems = true;

							drawItemStack(result, k + slot.xPos, l + slot.yPos, "");
						}
					}
					break;
				}
			}
		}
	}

	private void drawItemStack(ItemStack stack, int xPos, int yPos, String note)
	{
		this.itemRender.renderItemAndEffectIntoGUI(stack, xPos, yPos);

		FontRenderer font = null;
		if (!stack.isEmpty())
		{
			font = stack.getItem().getFontRenderer(stack);
		}
		if (font == null)
		{
			font = fontRenderer;
		}

		this.itemRender.renderItemOverlayIntoGUI(font, stack, xPos, yPos, note);
	}
}