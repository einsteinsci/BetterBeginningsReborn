package net.einsteinsci.betterbeginnings.tileentity;

import java.util.List;
import java.util.Random;
import java.util.Stack;

import javax.annotation.Nullable;

import net.einsteinsci.betterbeginnings.config.BBConfig;
import net.einsteinsci.betterbeginnings.inventory.ItemHandlerInfusionRepair;
import net.einsteinsci.betterbeginnings.register.RegisterItems;
import net.einsteinsci.betterbeginnings.register.recipe.InfusionRepairRecipeHandler;
import net.einsteinsci.betterbeginnings.register.recipe.elements.ElementRegistry;
import net.einsteinsci.betterbeginnings.register.recipe.elements.RecipeElement;
import net.einsteinsci.betterbeginnings.util.CapUtils;
import net.einsteinsci.betterbeginnings.util.Util;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;

public class TileEntityInfusionRepair extends TileEntityBB implements ITickable
{
    private static final String INV_TAG = "Items";
    private static final String PENDING_INGREDIENTS = "pending";
    private static final String MODE = "Mode";
    private static final String HEALTH_TAKEN = "HealthTaken";
    private static final String LEVELS_NEEDED = "LevelsNeeded";
    private static final String LEVELS_TAKEN = "LevelsTaken";
    public static final int SLOT_ENCH_ITEM = 0;
    private static final Random rand = new Random();

    private IItemHandlerModifiable mainHandler;
    private Stack<RecipeElement> pendingIngredients = new Stack<RecipeElement>();
    private Mode mode = Mode.NONE;
    private float healthTaken;
    private int xpLevelsTaken;
    private int xpLevelsNeeded;
    private AxisAlignedBB searchBox;

    public TileEntityInfusionRepair()
    {
	this.mainHandler = new ItemHandlerInfusionRepair(10);
    }

    public void activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
	if(player.isSneaking())
	{
	    if(heldItem.isEmpty())
	    {
		if(hasEnchItem())
		{
		    if(!world.isRemote)
		    {
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), mainHandler.getStackInSlot(SLOT_ENCH_ITEM)));
		    }
		    mainHandler.setStackInSlot(SLOT_ENCH_ITEM, ItemStack.EMPTY);
		    mode = Mode.NONE;
		    pendingIngredients.clear();
		    markDirty();
		    world.notifyBlockUpdate(pos, state, state, 8);
		}
		else if(world.isRemote)
		{
		    ITextComponent link1 = new TextComponentString("Infusion Repair");
		    link1.setStyle(new Style().setBold(true).setUnderlined(true).setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/einsteinsci/betterbeginnings/wiki/Infusion-Repair")));
		    ITextComponent link2 = new TextComponentString("Enchantment Diffusion");
		    link2.setStyle(new Style().setBold(true).setUnderlined(true).setClickEvent(new ClickEvent(Action.OPEN_URL, "https://github.com/einsteinsci/betterbeginnings/wiki/Enchantment-Diffusion")));
		    ITextComponent message = new TextComponentString("See the BB wiki articles on ");
		    message.appendSibling(link1);
		    player.sendMessage(message);
		    player.sendMessage(new TextComponentString("and ").appendSibling(link2));
		}
	    }
	}
	else
	{
	    if(heldItem.isEmpty() || hasEnchItem() || mode != Mode.NONE) return;
	    if(heldItem.getItem() == RegisterItems.cloth)
	    {
		mode = Mode.DIFFUSION;
	    }
	    else if(heldItem.isItemEnchanted())
	    {
		if(heldItem.getEnchantmentTagList().tagCount() > 8)
		{
		    player.sendMessage(new TextComponentTranslation("diffusion.toomanyenchants"));
		    mode = Mode.NONE;
		    return;
		}
		mode = Mode.REPAIR;
		pendingIngredients.clear();
		pendingIngredients.addAll(InfusionRepairRecipeHandler.instance().getRequiredStacks(heldItem));
		xpLevelsNeeded = InfusionRepairRecipeHandler.instance().getTakenLevels(heldItem);
		markDirty();
	    }
	    else
		return;
	    ItemStack newStack = heldItem.copy();
	    newStack.setCount(1);
	    mainHandler.setStackInSlot(heldItem.isItemEnchanted() ? SLOT_ENCH_ITEM : 1, newStack);
	    heldItem.shrink(1);
	}
    }

    @Override
    public void update()
    {
	switch (mode)
	{
	case NONE:
	    break;

	case DIFFUSION:
	    absorbItems();
	    diffuse();
	    break;

	case REPAIR:
	    absorbItems();
	    repair();
	    break;
	default:
	    return;
	}
    }

    private void absorbItems()
    {
	List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, searchBox);
	IBlockState state = world.getBlockState(pos);
	for (EntityItem entityItem : items)
	{
	    if(tryAbsorbDroppedItem(entityItem, state))
	    {
		markDirty();
		world.notifyBlockUpdate(pos, state, state, 8);
	    }
	    if(entityItem.getItem().getCount() == 0)
		entityItem.setDead();
	}
    }

    private boolean tryAbsorbDroppedItem(EntityItem item, IBlockState state)
    {
	ItemStack newStack = ItemStack.EMPTY;
	ItemStack stack = item.getItem();
	switch(mode)
	{
	case DIFFUSION:
	    if(isIngredientPresent(stack)) return false;
	    if(stack.getItem() == Items.BOOK || stack.isItemEnchanted())
	    {
		newStack = stack.copy();
		newStack.setCount(1);
		if(!world.isRemote) stack.shrink(1);
	    }
	    break;
	case REPAIR:
	    if(pendingIngredients.isEmpty()) return false;
	    RecipeElement currentIngredient = pendingIngredients.peek();
	    if(currentIngredient.matches(stack))
	    {
		newStack = stack.copy();
		newStack.setCount(Math.min(currentIngredient.getStackSize(), stack.getCount()));
		currentIngredient.setStackSize(currentIngredient.getStackSize() - Math.min(currentIngredient.getStackSize(), stack.getCount()));

		if(currentIngredient.getStackSize() <= 0)
		{
		    pendingIngredients.pop();
		}
		if(!world.isRemote) stack.shrink(newStack.getCount());
		markDirty();
	    }
	    break;
	default:
	    break;
	}	
	if(newStack.isEmpty()) return false;
	boolean slotFound = false;
	if(newStack.isItemEnchanted())
	{
	    mainHandler.setStackInSlot(SLOT_ENCH_ITEM, newStack);
	    slotFound = true;
	}
	else
	{
	    //attempt to insert stacks into inv
	    for(int s = SLOT_ENCH_ITEM + 1; s < mainHandler.getSlots(); s++)
	    {
		ItemStack slotStack = mainHandler.getStackInSlot(s);
		if (ItemHandlerHelper.canItemStacksStack(newStack, slotStack))
		{
		    CapUtils.incrementStack(mainHandler, s, newStack.getCount());
		    slotFound = true;
		    break;
		}
		if(slotStack.isEmpty())
		{
		    mainHandler.setStackInSlot(s, newStack);
		    slotFound = true;
		    break;
		}
	    }
	}
	return slotFound;
    }

    private boolean isIngredientPresent(ItemStack stack)
    {
	for(int s = 0; s < mainHandler.getSlots(); s++)
	{
	    ItemStack slotStack = mainHandler.getStackInSlot(s);
	    if(ItemStack.areItemsEqual(stack, slotStack) && ItemStack.areItemStackTagsEqual(stack, slotStack))
		return true;
	}
	return false;
    }

    private void diffuse()
    {
	//Check all ingredients are present
	if(canDiffuse())
	{
	    IBlockState state = world.getBlockState(pos);

	    //Find the enchanted item
	    ItemStack enchItem = mainHandler.getStackInSlot(SLOT_ENCH_ITEM);

	    //Take health 
	    if(healthTaken != BBConfig.diffusionHealthTaken)
	    {
		for (int i = 0; i < 2; i++)
		{
		    double x = rand.nextDouble() * 0.4 + 0.3;
		    double y = rand.nextDouble() * 0.5 + 0.5;
		    double z = rand.nextDouble() * 0.4 + 0.3;

		    double vx = rand.nextDouble() * 0.02 - 0.01;
		    double vy = rand.nextDouble() * 0.035 + 0.02;
		    double vz = rand.nextDouble() * 0.02 - 0.01;
		    world.spawnParticle(EnumParticleTypes.HEART, pos.getX() + x, pos.getY() + y + 1,
			    pos.getZ() + z, vx, vy, vz);
		}

		//Get player on top and damage them
		List<EntityPlayer> playersOnTop = world.getEntitiesWithinAABB(EntityPlayer.class, searchBox);
		if(!playersOnTop.isEmpty())
		{
		    EntityPlayer player = playersOnTop.get(0);
		    if (player.attackEntityFrom(Util.DIFFUSION_DAMAGE, 0.5F))
			healthTaken += 0.5F;
		}
		world.notifyBlockUpdate(pos, state, state, 8);
		markDirty();
	    }
	    else
	    {
		NBTTagList enchList = enchItem.getEnchantmentTagList();
		if(enchList != null)
		{
		    //Choose random enchant
		    int chosenEnchIndex = rand.nextInt(enchList.tagCount());
		    NBTTagCompound chosenEnchNBT = enchList.getCompoundTagAt(chosenEnchIndex);
		    EnchantmentData chosenEnchData = new EnchantmentData(Enchantment.getEnchantmentByID(chosenEnchNBT.getShort("id")), chosenEnchNBT.getShort("lvl"));

		    //Remove the enchant
		    enchList.removeTag(chosenEnchIndex);
		    if(enchList.hasNoTags()) enchItem.getTagCompound().removeTag("ench");

		    //Damage the ench item
		    if(enchItem.isItemStackDamageable())
			enchItem.damageItem(enchItem.getMaxDamage() / 5, null);
		    if(enchItem.getItemDamage() > enchItem.getMaxDamage())
			mainHandler.setStackInSlot(SLOT_ENCH_ITEM, ItemStack.EMPTY);

		    //Create an enchanted book for the enchantment
		    ItemStack enchBook = new ItemStack(Items.ENCHANTED_BOOK);
		    ItemEnchantedBook.addEnchantment(enchBook, chosenEnchData);

		    //Spawn the enchanted book & the disenchanted item
		    if(!world.isRemote)
		    {
			world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), enchBook));
			if(!mainHandler.getStackInSlot(SLOT_ENCH_ITEM).isEmpty())
			    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), mainHandler.getStackInSlot(SLOT_ENCH_ITEM)));
			world.notifyBlockUpdate(pos, state, state, 8);
			markDirty();
		    }


		    //Consume everything else in the inventory
		    for(int s = 0; s < mainHandler.getSlots(); s++)
		    {
			mainHandler.setStackInSlot(s, ItemStack.EMPTY);
		    }
		    world.notifyBlockUpdate(pos, state, state, 8);
		    markDirty();
		}
		mode = Mode.NONE;
	    }
	}
    }

    private void repair()
    {
	if(canRepair())
	{
	    IBlockState state = world.getBlockState(pos);

	    //Find the enchanted item
	    ItemStack enchItem = mainHandler.getStackInSlot(SLOT_ENCH_ITEM);
	    if(enchItem.getItemDamage() == 0)
	    {
		if(!world.isRemote)
		{
		    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), mainHandler.getStackInSlot(SLOT_ENCH_ITEM)));
		}
		mainHandler.setStackInSlot(SLOT_ENCH_ITEM, ItemStack.EMPTY);
		mode = Mode.NONE;
		markDirty();
		world.notifyBlockUpdate(pos, state, state, 8);
	    }
	    if(xpLevelsTaken < xpLevelsNeeded)
	    {
		for (int i = 0; i < 2; i++)
		{
		    double x = rand.nextDouble() * 0.8 + 0.3;
		    double y = rand.nextDouble() * 0.1 + 0.2;
		    double z = rand.nextDouble() * 0.8 + 0.3;

		    double vx = rand.nextDouble() * 0.02 - 0.01;
		    double vy = rand.nextDouble() * 0.035 + 0.02;
		    double vz = rand.nextDouble() * 0.02 - 0.01;
		    world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + x, pos.getY() + y + 1,
			    pos.getZ() + z, vx, vy, vz);
		}

		//Get player on top and damage them
		List<EntityPlayer> playersOnTop = world.getEntitiesWithinAABB(EntityPlayer.class, searchBox);
		if(!playersOnTop.isEmpty())
		{
		    EntityPlayer player = playersOnTop.get(0);
		    player.onEnchant(enchItem, 1);
		    xpLevelsTaken++;
		    world.notifyBlockUpdate(pos, state, state, 8);
		    markDirty();
		}
	    }
	    else
	    {
		enchItem.setItemDamage(0);
		//Spawn the repair item
		if(!world.isRemote)
		{
		    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY() + 1, pos.getZ(), mainHandler.getStackInSlot(SLOT_ENCH_ITEM)));
		}

		//Consume everything else in the inventory
		for(int s = 0; s < mainHandler.getSlots(); s++)
		{
		    mainHandler.setStackInSlot(s, ItemStack.EMPTY);
		}
		mode = Mode.NONE;

		this.xpLevelsTaken = 0;
		markDirty();
		world.notifyBlockUpdate(pos, state, state, 8);
	    }
	}
    }

    private boolean canDiffuse()
    {
	boolean foundBook = false;
	boolean foundEnchItem = false;
	for(int s = 0; s < mainHandler.getSlots(); s++)
	{
	    ItemStack stack = mainHandler.getStackInSlot(s);
	    if(!stack.isEmpty())
	    {
		if(stack.getItem() == Items.BOOK)
		    foundBook = true;
		else if(stack.isItemEnchanted())
		    foundEnchItem = true;
	    }
	}
	return foundBook && foundEnchItem;
    }

    private boolean canRepair()
    {
	return pendingIngredients.isEmpty();
    }

    private boolean hasEnchItem()
    {
	return !mainHandler.getStackInSlot(SLOT_ENCH_ITEM).isEmpty();//STACKNULL
    }

    public Stack<RecipeElement> getPendingIngredients()
    {
	return pendingIngredients;
    }

    @Override
    public void setPos(BlockPos pos)
    {
	super.setPos(pos);
	searchBox = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
	NBTTagCompound tag = new NBTTagCompound();
	writeToNBT(tag);
	return new SPacketUpdateTileEntity(pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet)
    {
	readFromNBT(packet.getNbtCompound());
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
	    return true;
	return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
	if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
	    return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(mainHandler);
	return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
	super.readFromNBT(compound);
	CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(mainHandler, null, compound.getTag(INV_TAG));
	mode = Mode.valueOf(compound.getString(MODE));

	NBTTagList ingredientList = compound.getTagList(PENDING_INGREDIENTS, NBT.TAG_COMPOUND);
	pendingIngredients = new Stack<RecipeElement>();
	for(int tag = 0; tag < ingredientList.tagCount(); tag++)
	{
	    pendingIngredients.add(ElementRegistry.createElementFromNBT(ingredientList.getCompoundTagAt(tag)));
	}

	xpLevelsNeeded = compound.getInteger(LEVELS_NEEDED);
	xpLevelsTaken = compound.getInteger(LEVELS_TAKEN);
	healthTaken = compound.getFloat(HEALTH_TAKEN);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
	compound.setTag(INV_TAG, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(mainHandler, null));
	compound.setString(MODE, mode.name());

	NBTTagList ingredientList = new NBTTagList();
	for (RecipeElement element : pendingIngredients)
	{
	    ingredientList.appendTag(ElementRegistry.elementToNBT(element));
	}
	compound.setTag(PENDING_INGREDIENTS, ingredientList);

	compound.setInteger(LEVELS_NEEDED, xpLevelsNeeded);
	compound.setInteger(LEVELS_TAKEN, xpLevelsTaken);
	compound.setFloat(HEALTH_TAKEN, healthTaken);
	return super.writeToNBT(compound);
    }

    enum Mode
    {
	NONE,
	DIFFUSION,
	REPAIR;
    }

    @Override
    public String getName()
    {
	return hasCustomName() ? customName : "container.infusionRepair";
    }
}
