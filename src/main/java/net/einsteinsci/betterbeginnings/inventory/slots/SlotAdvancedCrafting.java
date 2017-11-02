package net.einsteinsci.betterbeginnings.inventory.slots;

import net.einsteinsci.betterbeginnings.inventory.containers.ContainerDoubleWorkbench;
import net.einsteinsci.betterbeginnings.register.recipe.AdvancedRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.FMLCommonHandler;

// Advanced crafting result slot
public class SlotAdvancedCrafting extends Slot
{
    /**
     * The craft matrix inventory linked to this result slot.
     */
    private final InventoryCrafting craftMatrix;
    /**
     * The added materials necessary for advanced crafting.
     */
    private final IInventory additionalMaterials;
    /**
     * The player that is using the GUI where this slot resides.
     */
    private EntityPlayer thePlayer;
    /**
     * The number of items that have been crafted so far. Gets passed to
     * ItemStack.onCrafting before being reset.
     */
    private int amountCrafted;

    private ContainerDoubleWorkbench container;

    public SlotAdvancedCrafting(EntityPlayer player, ContainerDoubleWorkbench container_, InventoryCrafting matrix,
	    IInventory resultInv, IInventory addedMats, int id, int x, int y)
    {
	super(resultInv, id, x, y);
	container = container_;
	thePlayer = player;
	craftMatrix = matrix;
	additionalMaterials = addedMats;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
     * not ore and wood. Typically increases an internal count then calls
     * onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack stack, int resultCount)
    {
	amountCrafted += resultCount;
	onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes,
     * not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack stack)
    {
	stack.onCrafting(thePlayer.world, thePlayer, amountCrafted);
	amountCrafted = 0;

	if (stack.getItem() == Item.getItemFromBlock(Blocks.CRAFTING_TABLE))
	{
	    thePlayer.addStat(AchievementList.BUILD_WORK_BENCH, 1);
	}

	if (stack.getItem() instanceof ItemPickaxe)
	{
	    thePlayer.addStat(AchievementList.BUILD_PICKAXE, 1);
	}

	if (stack.getItem() == Item.getItemFromBlock(Blocks.FURNACE))
	{
	    thePlayer.addStat(AchievementList.BUILD_FURNACE, 1);
	}

	if (stack.getItem() instanceof ItemHoe)
	{
	    thePlayer.addStat(AchievementList.BUILD_HOE, 1);
	}

	if (stack.getItem() == Items.BREAD)
	{
	    thePlayer.addStat(AchievementList.MAKE_BREAD, 1);
	}

	if (stack.getItem() == Items.CAKE)
	{
	    thePlayer.addStat(AchievementList.BAKE_CAKE, 1);
	}

	if (stack.getItem() instanceof ItemPickaxe
		&& ((ItemPickaxe)stack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD)
	{
	    thePlayer.addStat(AchievementList.BUILD_BETTER_PICKAXE, 1);
	}

	if (stack.getItem() instanceof ItemSword
		&& !((ItemSword)stack.getItem()).getToolMaterialName().equals("noobwood"))
	{
	    thePlayer.addStat(AchievementList.BUILD_SWORD, 1);
	}

	if (stack.getItem() == Item.getItemFromBlock(Blocks.ENCHANTING_TABLE))
	{
	    thePlayer.addStat(AchievementList.ENCHANTMENTS, 1);
	}

	if (stack.getItem() == Item.getItemFromBlock(Blocks.BOOKSHELF))
	{
	    thePlayer.addStat(AchievementList.BOOKCASE, 1);
	}
    }

    // onCraftingEvent?
    @Override
    public void onPickupFromSlot(EntityPlayer player, ItemStack resultStack)
    {
	FMLCommonHandler.instance().firePlayerCraftingEvent(player, resultStack, craftMatrix);
	onCrafting(resultStack);

	AdvancedRecipe advRecipe = container.getLastAdvancedRecipe();
	if(advRecipe != null)
	{
	    // Decrease crafted materials
	    for (int i = 0; i < craftMatrix.getSizeInventory(); ++i)
	    {
		ItemStack ingredientStack = craftMatrix.getStackInSlot(i);

		if (ingredientStack != null)
		{
		    if (ingredientStack.getItem().hasContainerItem(ingredientStack))
		    {
			ItemStack containerStack = ingredientStack.getItem().getContainerItem(ingredientStack);

			craftMatrix.setInventorySlotContents(i, containerStack);

			if (containerStack != null)
			{
			    boolean isDamageable = containerStack.isItemStackDamageable();

			    if (isDamageable && containerStack.getItemDamage() > containerStack.getMaxDamage())
			    {
				ForgeEventFactory.onPlayerDestroyItem(thePlayer, containerStack, thePlayer.getActiveHand());
				continue;
			    }
			}
		    }
		    else
		    {
			craftMatrix.decrStackSize(i, 1);
		    }
		}
	    }

	    // Decrease added materials
	    for (int i = 0; i < additionalMaterials.getSizeInventory(); ++i)
	    {
		ItemStack matStack = additionalMaterials.getStackInSlot(i);

		if (matStack != null)
		{
		    int amount = 0;

		    if (advRecipe != null)
		    {
			amount = advRecipe.getNeededMaterialCount(matStack);
		    }

		    additionalMaterials.decrStackSize(i, amount);

		    // Containers
		    if (matStack.getItem().hasContainerItem(matStack))
		    {
			ItemStack containerStack = matStack.getItem().getContainerItem(matStack);

			if (containerStack != null && containerStack.isItemStackDamageable()
				&& containerStack.getItemDamage() > containerStack.getMaxDamage())
			{
			    ForgeEventFactory.onPlayerDestroyItem(thePlayer, containerStack, thePlayer.getActiveHand());
			    continue;
			}
		    }
		}
	    }
	}
	else
	{
	    //From SlotCrafting
	    ItemStack[] remainingItems = CraftingManager.getInstance().getRemainingItems(craftMatrix, thePlayer.world);
	    for(int s = 0; s < remainingItems.length; s++)
	    {
		ItemStack currentStack = craftMatrix.getStackInSlot(s);
		ItemStack remnantStack = remainingItems[s];

		if (currentStack != null)
		{
		    craftMatrix.decrStackSize(s, 1);
		    currentStack = craftMatrix.getStackInSlot(s);
		}

		if(remnantStack != null)
		{
		    if(currentStack == null)
		    {
			craftMatrix.setInventorySlotContents(s, remnantStack);
		    }
		    else if (ItemStack.areItemsEqual(currentStack, remnantStack) && ItemStack.areItemStackTagsEqual(currentStack, remnantStack))
		    {
			remnantStack.stackSize += currentStack.stackSize;
			this.craftMatrix.setInventorySlotContents(s, remnantStack);
		    }
		    else if (!this.thePlayer.inventory.addItemStackToInventory(remnantStack))
		    {
			this.thePlayer.dropItem(remnantStack, false);
		    }
		}
	    }
	}
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for
     * the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack stack)
    {
	// One way only.
	return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of
     * the second int arg. Returns the new stack.
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
	if (getHasStack())
	{
	    amountCrafted += Math.min(amount, getStack().stackSize);
	}

	return super.decrStackSize(amount);
    }
}


// BUFFER
